using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

public class MonsterController : MonoBehaviour
{
    private Animator animator;
    private NavMeshAgent navAgent;
    
    // Animation state parameters
    private bool isWalking = false;
    private bool isRunning = false;
    
    // State machine variables
    public enum MonsterState
    {
        Idle,
        Wander,
        Chase,
        Flee
    }
    
    private MonsterState currentState = MonsterState.Idle;
    
    // Player detection settings
    public float detectionRange = 20.0f;
    public float losePlayerRange = 25.0f;
    public Transform player;
    
    // Movement settings
    public float walkSpeed = 3.0f;
    public float runSpeed = 7.0f;
    public float fleeSpeed = 6.0f;
    
    // Wander settings
    public float wanderRadius = 10.0f;
    public float minWanderInterval = 4.0f;
    public float maxWanderInterval = 8.0f;
    private float wanderTimer;
    private Vector3 wanderTarget;
    
    // Return home after being hit
    private bool returnToStartArea = false;
    
    // Flee settings
    public float fleeTime = 7.0f;
    private float fleeTimer = 0f;
    public float fleeDistance = 15.0f;
    
    // Audio settings
    public AudioClip walkSound;
    public AudioClip runSound;
    public AudioClip hurtSound;
    public AudioClip attackSound;
    
    private AudioSource audioSource;
    
    // Movement and position tracking
    private Vector3 startPosition;
    private Vector3 lastPosition;
    private float stuckCheckTimer = 0.0f;
    private int stuckCounter = 0;
    public float stuckCheckDistance = 0.1f;
    
    // Attack settings
    public float attackRange = 1.5f;
    public float attackCooldown = 2.0f;
    private float attackTimer = 0f;
    private bool canAttack = true;
    
    // Debug options
    public bool showDebug = true;
    
    void Start()
    {
        // Get the components
        animator = GetComponent<Animator>();
        navAgent = GetComponent<NavMeshAgent>();
        
        // Verify we have required components
        if (animator == null)
        {
            Debug.LogError("No Animator component found on the monster!");
        }
        
        if (navAgent == null)
        {
            Debug.LogError("No NavMeshAgent component found on the monster! Please add one.");
            this.enabled = false;
            return;
        }
        
        // Find the player if not set
        if (player == null)
        {
            GameObject playerObj = GameObject.FindGameObjectWithTag("Player");
            if (playerObj != null)
            {
                player = playerObj.transform;
                if (showDebug) Debug.Log("Found player: " + player.name);
            }
            else
            {
                Debug.LogError("No GameObject with 'Player' tag found! Please tag your player.");
            }
        }
        
        // Set up audio source
        audioSource = GetComponent<AudioSource>();
        if (audioSource == null)
        {
            audioSource = gameObject.AddComponent<AudioSource>();
        }
        
        // Save starting position
        startPosition = transform.position;
        lastPosition = transform.position;
        
        // Configure NavMeshAgent initial settings
        navAgent.speed = walkSpeed;
        navAgent.stoppingDistance = attackRange * 0.8f; // Stop slightly before attackRange
        
        // Start in idle state
        ChangeState(MonsterState.Idle);
    }
    
    void Update()
    {
        // Update attack cooldown timer
        if (!canAttack)
        {
            attackTimer -= Time.deltaTime;
            if (attackTimer <= 0)
            {
                canAttack = true;
            }
        }
        
        // Handle state transitions
        if (currentState == MonsterState.Flee)
        {
            // If fleeing, check if flee time is over
            fleeTimer -= Time.deltaTime;
            if (fleeTimer <= 0)
            {
                // After fleeing, just wander from current position
                ChangeState(MonsterState.Wander);
            }
        }
        else if (CanSeePlayer())
        {
            // If we see player, always start chase (highest priority)
            ChangeState(MonsterState.Chase);
            
            // Check if we're close enough to attack
            float distanceToPlayer = Vector3.Distance(transform.position, player.position);
            if (distanceToPlayer <= attackRange && canAttack)
            {
                AttackPlayer();
            }
        }
        else if (currentState == MonsterState.Chase && !CanSeePlayer(losePlayerRange))
        {
            // If chasing but lost sight of player, start wandering
            ChangeState(MonsterState.Wander);
        }
    
        // Update current state behavior
        switch (currentState)
        {
            case MonsterState.Idle:
                // Transition to wander after a short delay
                wanderTimer -= Time.deltaTime;
                if (wanderTimer <= 0)
                {
                    ChangeState(MonsterState.Wander);
                }
                break;
            
            case MonsterState.Wander:
                if (returnToStartArea)
                {
                    ReturnToStartArea();
                }
                else
                {
                    UpdateWandering();
                }
                break;
            
            case MonsterState.Chase:
                ChasePlayer();
                break;
            
            case MonsterState.Flee:
                FleeFromPlayer();
                break;
        }
    
        // Update animation parameters
        UpdateAnimator();
    
        // Check if we're stuck
        CheckIfStuck();
    }
    
    void AttackPlayer()
    {
        if (showDebug) Debug.Log("Monster attacking player!");
        
        // Stop the agent from moving during attack
        navAgent.isStopped = true;
        
        // Face the player first
        if (player != null)
        {
            Vector3 dirToPlayer = player.position - transform.position;
            dirToPlayer.y = 0;
            if (dirToPlayer.magnitude > 0.1f)
            {
                transform.rotation = Quaternion.LookRotation(dirToPlayer.normalized);
            }
        }
        
        // Play attack animation if available
        if (animator != null)
        {
            // Make sure other triggers are reset first to avoid conflicts
            animator.ResetTrigger("isHit");
            
            // Set attack trigger
            animator.SetTrigger("Attack");
            
            // Start coroutine to deal damage after a short delay
            // This ensures the animation has time to play before resuming movement
            StartCoroutine(AttackCoroutine());
        }
        else
        {
            // If no animator, deal damage immediately
            DealDamageToPlayer();
            
            // Resume movement after a short delay
            StartCoroutine(ResumeMovementAfterAttack(0.5f));
        }
        
        // Set attack cooldown
        canAttack = false;
        attackTimer = attackCooldown;
    }
    
    IEnumerator AttackCoroutine()
    {
        // Wait for animation to reach the damage point (adjust time as needed)
        yield return new WaitForSeconds(0.3f);
        
        // Deal damage at the appropriate moment in the animation
        DealDamageToPlayer();
        
        // Wait for animation to finish
        yield return new WaitForSeconds(0.7f);
        
        // Resume movement after attack animation
        StartCoroutine(ResumeMovementAfterAttack(0.1f));
    }
    
    void DealDamageToPlayer()
    {
        // Play attack sound
        if (attackSound != null && audioSource != null)
        {
            audioSource.PlayOneShot(attackSound);
        }
        
        // Deal damage to player
        if (player != null)
        {
            PlayerController playerController = player.GetComponent<PlayerController>();
            if (playerController != null)
            {
                playerController.TakeDamage();
            }
        }
    }
    
    IEnumerator ResumeMovementAfterAttack(float delay)
    {
        yield return new WaitForSeconds(delay);
        
        // Resume chasing if we're still in chase state
        if (currentState == MonsterState.Chase)
        {
            navAgent.isStopped = false;
            navAgent.SetDestination(player.position);
        }
    }
    
    void ReturnToStartArea()
    {
        navAgent.SetDestination(startPosition);
        
        // Check if we've arrived at start position
        if (Vector3.Distance(transform.position, startPosition) < 1.0f)
        {
            returnToStartArea = false;
            SetNewWanderTarget(); // Start wandering again
        }
        
        if (showDebug)
        {
            Debug.DrawLine(transform.position, startPosition, Color.cyan);
        }
    }
    
    void UpdateWandering()
    {
        // Decrease timer for next position change
        wanderTimer -= Time.deltaTime;
        
        // Check if we've reached the target or timer ran out
        if (wanderTimer <= 0 || 
            (navAgent.remainingDistance <= navAgent.stoppingDistance && !navAgent.pathPending))
        {
            SetNewWanderTarget();
        }
    }
    
    void SetNewWanderTarget()
    {
        // Try to find a valid wander target
        for (int attempts = 0; attempts < 10; attempts++)
        {
            // Find a random point within the wander radius
            Vector3 randomDirection = Random.insideUnitSphere * wanderRadius;
            randomDirection.y = 0;
            Vector3 potentialTarget = startPosition + randomDirection;
            
            // Try to get a valid point on the NavMesh
            NavMeshHit hit;
            if (NavMesh.SamplePosition(potentialTarget, out hit, wanderRadius, NavMesh.AllAreas))
            {
                // Valid target found
                wanderTarget = hit.position;
                wanderTimer = Random.Range(minWanderInterval, maxWanderInterval);
                
                // Set the NavMeshAgent destination
                navAgent.SetDestination(wanderTarget);
                
                if (showDebug)
                {
                    Debug.Log("New wander target set: " + wanderTarget);
                    Debug.DrawLine(transform.position, wanderTarget, Color.magenta, wanderTimer);
                }
                
                return;
            }
        }
        
        // If we couldn't find a valid target after 10 attempts, just wander near current position
        NavMeshHit fallbackHit;
        Vector3 fallbackPos = transform.position + Random.insideUnitSphere * 3.0f;
        fallbackPos.y = transform.position.y; // Keep same height
        
        if (NavMesh.SamplePosition(fallbackPos, out fallbackHit, 5.0f, NavMesh.AllAreas))
        {
            wanderTarget = fallbackHit.position;
            wanderTimer = 2.0f; // Short time
            navAgent.SetDestination(wanderTarget);
            
            if (showDebug) Debug.Log("Using fallback wander target: " + wanderTarget);
        }
        else
        {
            // Extreme fallback - just move a short distance forward
            if (showDebug) Debug.Log("No valid NavMesh position found, using direct forward movement");
            wanderTarget = transform.position + transform.forward * 2.0f;
            navAgent.SetDestination(wanderTarget);
            wanderTimer = 1.0f;
        }
    }
    
    void ChasePlayer()
    {
        if (player != null)
        {
            // Set destination to player position
            navAgent.SetDestination(player.position);
            
            // Debug visualization for chase
            if (showDebug)
            {
                Debug.DrawLine(transform.position, player.position, Color.red);
            }
        }
    }
    
    void FleeFromPlayer()
    {
        if (player == null) return;
        
        // Find a direction away from the player
        Vector3 fleeDirection = transform.position - player.position;
        fleeDirection.y = 0; // Keep on ground plane
        
        if (fleeDirection.magnitude > 0.1f)
        {
            // Normalize and extend by fleeDistance
            fleeDirection = fleeDirection.normalized * fleeDistance;
            
            // Find a valid NavMesh position in that direction
            NavMeshHit hit;
            if (NavMesh.SamplePosition(transform.position + fleeDirection, out hit, fleeDistance, NavMesh.AllAreas))
            {
                // Set destination to flee point
                navAgent.SetDestination(hit.position);
                
                if (showDebug)
                {
                    Debug.DrawRay(transform.position, fleeDirection, Color.yellow);
                }
            }
            else
            {
                // If we can't find a valid point in the direct away direction,
                // try some other directions
                for (int i = 45; i <= 315; i += 45)
                {
                    Vector3 testDirection = Quaternion.Euler(0, i, 0) * fleeDirection;
                    if (NavMesh.SamplePosition(transform.position + testDirection, 
                        out hit, fleeDistance, NavMesh.AllAreas))
                    {
                        navAgent.SetDestination(hit.position);
                        
                        if (showDebug)
                        {
                            Debug.DrawRay(transform.position, testDirection, Color.yellow);
                        }
                        
                        break;
                    }
                }
            }
        }
    }
    
    void CheckIfStuck()
    {
        // Update the timer
        stuckCheckTimer += Time.deltaTime;
        
        // Only check every 0.5 seconds
        if (stuckCheckTimer > 0.5f)
        {
            // Check if we've moved significantly
            float distanceMoved = Vector3.Distance(transform.position, lastPosition);
            
            // If we haven't moved but should be moving (and have a path)
            if (distanceMoved < stuckCheckDistance && 
                navAgent.hasPath && navAgent.remainingDistance > navAgent.stoppingDistance)
            {
                stuckCounter++;
                
                if (stuckCounter >= 3)  // If stuck for several checks in a row
                {
                    if (showDebug) Debug.Log("Monster is stuck. Taking evasive action.");
                    
                    // Force-enable the NavMeshAgent if it was disabled
                    if (!navAgent.enabled)
                    {
                        navAgent.enabled = true;
                    }
                    
                    // We're really stuck - take more drastic action
                    navAgent.ResetPath();
                    
                    // Try to unstick by teleporting slightly
                    if (NavMesh.SamplePosition(transform.position + Vector3.up * 0.5f, 
                        out NavMeshHit hit, 2.0f, NavMesh.AllAreas))
                    {
                        transform.position = hit.position;
                        if (showDebug) Debug.Log("Teleported monster to valid NavMesh position");
                    }
                    
                    // Set new path based on state
                    if (currentState == MonsterState.Wander)
                    {
                        SetNewWanderTarget();
                    }
                    else if (currentState == MonsterState.Chase)
                    {
                        if (player != null)
                        {
                            navAgent.SetDestination(player.position);
                        }
                    }
                    else if (currentState == MonsterState.Flee)
                    {
                        FleeFromPlayer();
                    }
                    
                    stuckCounter = 0;
                }
            }
            else
            {
                // We moved, so reset stuck counter
                stuckCounter = 0;
            }
            
            // Update last position
            lastPosition = transform.position;
            stuckCheckTimer = 0f;
        }
    }
    
    void UpdateAnimator()
    {
        // Update animation parameters based on current state
        if (animator != null)
        {
            // Force the animation states based on current state
            // This is more reliable than checking velocity
            switch (currentState)
            {
                case MonsterState.Idle:
                    animator.SetBool("isWalking", false);
                    animator.SetBool("isRunning", false);
                    break;
                    
                case MonsterState.Wander:
                    animator.SetBool("isWalking", true);
                    animator.SetBool("isRunning", false);
                    break;
                    
                case MonsterState.Chase:
                case MonsterState.Flee:
                    animator.SetBool("isWalking", true);
                    animator.SetBool("isRunning", true);
                    break;
            }
            
            // Also update the local tracking variables
            isWalking = animator.GetBool("isWalking");
            isRunning = animator.GetBool("isRunning");
        }
    }
    
    bool CanSeePlayer(float range = 0)
    {
        if (player == null)
            return false;
            
        if (range == 0)
            range = detectionRange;
            
        float distance = Vector3.Distance(transform.position, player.position);
        
        // Draw debug line to player
        if (showDebug)
        {
            Debug.DrawLine(transform.position, player.position, 
                (distance <= range) ? Color.green : Color.red, 0.1f);
        }
        
        if (distance <= range)
        {
            // Check if there's a clear line of sight
            Vector3 direction = (player.position - transform.position).normalized;
            RaycastHit hit;
            
            // Use a slightly higher position to avoid ground detection issues
            Vector3 monsterEyePosition = transform.position + Vector3.up * 1.0f;
            Vector3 playerPosition = player.position + Vector3.up * 1.0f;
            direction = (playerPosition - monsterEyePosition).normalized;
            
            if (Physics.Raycast(monsterEyePosition, direction, out hit, range))
            {
                if (hit.transform == player || hit.transform.IsChildOf(player))
                {
                    return true;
                }
                else if (showDebug)
                {
                    // We hit something that's not the player
                    Debug.DrawRay(monsterEyePosition, direction * hit.distance, Color.yellow, 0.1f);
                }
            }
        }
        
        return false;
    }
    
    void ChangeState(MonsterState newState)
    {
        // Don't change if it's the same state
        if (currentState == newState) return;
        
        // Exit previous state
        ExitState(currentState);
        
        // Enter new state
        currentState = newState;
        EnterState(newState);
        
        if (showDebug) Debug.Log("Monster changed state to: " + newState);
        
        // Make sure NavMeshAgent is active and has a destination
        if (navAgent != null && !navAgent.isActiveAndEnabled)
        {
            navAgent.enabled = true;
            if (showDebug) Debug.Log("Re-enabled NavMeshAgent");
        }
    }
    
    void EnterState(MonsterState state)
    {
        // Make sure NavMeshAgent is enabled
        if (!navAgent.enabled)
        {
            navAgent.enabled = true;
            if (showDebug) Debug.Log("Enabling NavMeshAgent on state change");
        }
        
        // Reset any path issues
        if (navAgent.pathStatus == NavMeshPathStatus.PathInvalid || 
            navAgent.pathStatus == NavMeshPathStatus.PathPartial)
        {
            navAgent.ResetPath();
            if (showDebug) Debug.Log("Resetting invalid path on state change");
        }
        
        switch (state)
        {
            case MonsterState.Idle:
                // Stop the agent from moving
                navAgent.isStopped = true;
                
                wanderTimer = Random.Range(1f, 3f); // Short idle before wandering
                break;
                
            case MonsterState.Wander:
                // Configure NavMeshAgent for walking
                navAgent.speed = walkSpeed;
                navAgent.acceleration = 8.0f;
                navAgent.angularSpeed = 120.0f;
                navAgent.isStopped = false;
                
                // Set initial wander target if not returning to start area
                if (!returnToStartArea)
                {
                    SetNewWanderTarget();
                }
                
                if (walkSound != null && audioSource != null)
                {
                    audioSource.clip = walkSound;
                    audioSource.loop = true;
                    audioSource.Play();
                }
                break;
                
            case MonsterState.Chase:
                // Configure NavMeshAgent for running
                navAgent.speed = runSpeed;
                navAgent.acceleration = 12.0f; // Faster acceleration for chase
                navAgent.angularSpeed = 180.0f; // Faster turning for chase
                navAgent.isStopped = false;
                
                // Immediately set destination to player
                if (player != null)
                {
                    navAgent.SetDestination(player.position);
                }
                
                if (runSound != null && audioSource != null)
                {
                    audioSource.clip = runSound;
                    audioSource.loop = true;
                    audioSource.Play();
                }
                break;
                
            case MonsterState.Flee:
                // Configure NavMeshAgent for fleeing
                navAgent.speed = fleeSpeed;
                navAgent.acceleration = 12.0f;
                navAgent.angularSpeed = 180.0f;
                navAgent.isStopped = false;
                
                fleeTimer = fleeTime;
                
                // Immediately calculate a flee direction
                FleeFromPlayer();
                
                if (runSound != null && audioSource != null)
                {
                    audioSource.clip = runSound;
                    audioSource.loop = true;
                    audioSource.Play();
                }
                break;
        }
        
        // Ensure agent is properly updated
        navAgent.updatePosition = true;
        navAgent.updateRotation = true;
        
        // Update animator immediately when entering state
        UpdateAnimator();
    }
    
    void ExitState(MonsterState state)
    {
        // Handle any clean-up when exiting states
        switch (state)
        {
            case MonsterState.Wander:
            case MonsterState.Chase:
            case MonsterState.Flee:
                if (audioSource != null && audioSource.isPlaying)
                {
                    audioSource.Stop();
                }
                break;
        }
    }
    
    public void TakeDamage()
    {
        // Play hurt sound
        if (hurtSound != null && audioSource != null)
        {
            audioSource.PlayOneShot(hurtSound);
        }
        
        // Trigger hit animation
        if (animator != null)
        {
            animator.SetTrigger("isHit");
            
            // Make sure the hit animation doesn't cause other animation issues
            StartCoroutine(ResetTriggers());
        }
        
        // Start fleeing
        ChangeState(MonsterState.Flee);
        
        if (showDebug) Debug.Log("Monster took damage and is now fleeing");
    }
    
    // Call this when player is hit or when player respawns
    public void PlayerReset()
    {
        // Return to wander area
        returnToStartArea = true;
        ChangeState(MonsterState.Wander);
        
        if (showDebug) Debug.Log("Player reset, monster returning to wander area");
    }
    
    IEnumerator ResetTriggers()
    {
        // Wait a frame to let the trigger be consumed
        yield return new WaitForEndOfFrame();
        
        // Reset all triggers to avoid conflicts
        animator.ResetTrigger("isHit");
        animator.ResetTrigger("Attack");
    }
    
    // This method is called when a collider enters the trigger collider of the monster
    void OnTriggerEnter(Collider other)
    {
        // Check if the collider belongs to the player
        if (other.CompareTag("Player") && currentState == MonsterState.Chase && canAttack)
        {
            AttackPlayer();
        }
    }
    
    // Draw the detection range in the editor
    void OnDrawGizmosSelected()
    {
        // Detection range
        Gizmos.color = Color.red;
        Gizmos.DrawWireSphere(transform.position, detectionRange);
        
        // Lose player range
        Gizmos.color = Color.yellow;
        Gizmos.DrawWireSphere(transform.position, losePlayerRange);
        
        // Wander radius
        Gizmos.color = Color.white;
        if (Application.isPlaying)
        {
            Gizmos.DrawWireSphere(startPosition, wanderRadius);
        }
        else
        {
            Gizmos.DrawWireSphere(transform.position, wanderRadius);
        }
        
        // Attack range
        Gizmos.color = Color.magenta;
        Gizmos.DrawWireSphere(transform.position, attackRange);
    }
}