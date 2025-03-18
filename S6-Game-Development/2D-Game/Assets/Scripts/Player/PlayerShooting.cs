using UnityEngine;

public class PlayerShooting : MonoBehaviour
{
    [Header("Player Identity")]
    [Tooltip("Should match PlayerController.playerID")]
    public int playerID = 1;

    [Header("Shooting Settings")]
    public GameObject projectilePrefab;
    public float projectileSpeed = 8f;
    public float shootingCooldown = 0.5f;
    public Transform shootPoint; // Optional: specific point where bullets spawn

    [Header("Player 1 Settings")]
    [Tooltip("Allow mouse aiming and shooting for Player 1")]
    public bool useMouseForPlayer1 = true;

    // Internal variables
    private float lastShotTime;
    private Animator animator;
    
    // Shoot key (set by PlayerController)
    [HideInInspector]
    public KeyCode shootKey = KeyCode.Space;

    // Animation state name
    private const string ATTACK = "PlayerAttack";
    
    // Mouse position in world coordinates
    private Vector3 mouseWorldPosition;

    void Start()
    {
        animator = GetComponent<Animator>();

        // If no shoot point assigned, use the player's position
        if (shootPoint == null)
        {
            shootPoint = transform;
        }
        
        // Check if projectile prefab is assigned
        if (projectilePrefab == null)
        {
            Debug.LogError("No projectile prefab assigned to Player " + playerID);
        }
        
        Debug.Log("PlayerShooting initialized for Player " + playerID + 
                 " with key: " + shootKey + 
                 (playerID == 1 && useMouseForPlayer1 ? " and mouse support" : ""));
    }

    void Update()
    {
        // Only Player 1 can use space bar and mouse
        if (playerID == 1)
        {
            // Check for space bar
            if (Input.GetKeyDown(KeyCode.Space))
            {
                AttemptToShoot();
            }
            
            // Check for mouse click if enabled
            if (useMouseForPlayer1 && Input.GetMouseButtonDown(0))
            {
                // Update mouse position
                UpdateMouseWorldPosition();
                AttemptToShoot();
            }
        }
        // Player 2 uses their assigned key
        else if (Input.GetKeyDown(shootKey))
        {
            AttemptToShoot();
        }
        
        // Always update mouse position for aiming
        if (playerID == 1 && useMouseForPlayer1)
        {
            UpdateMouseWorldPosition();
        }
    }

    void UpdateMouseWorldPosition()
    {
        if (Camera.main != null)
        {
            Vector3 mousePos = Input.mousePosition;
            mousePos.z = -Camera.main.transform.position.z; // Distance from camera to 2D plane
            mouseWorldPosition = Camera.main.ScreenToWorldPoint(mousePos);
        }
    }

    public void AttemptToShoot()
    {
        // Check cooldown
        if (Time.time > lastShotTime + shootingCooldown)
        {
            Debug.Log("Player " + playerID + " shooting!");
            lastShotTime = Time.time;

            // Play attack animation if available
            if (animator != null)
            {
                animator.Play(ATTACK);
            }

            // Get the direction to shoot
            Vector3 shootDirection = GetShootDirection();

            // Shoot
            Shoot(shootDirection);
        }
    }

    void Shoot(Vector2 direction)
    {
        if (projectilePrefab == null)
        {
            Debug.LogError("Projectile prefab not assigned to PlayerShooting script!");
            return;
        }

        // Create the projectile
        GameObject projectile = Instantiate(
            projectilePrefab,
            shootPoint.position,
            Quaternion.identity
        );

        // Set up the projectile
        ProjectileController projectileController = projectile.GetComponent<ProjectileController>();
        if (projectileController != null)
        {
            projectileController.SetDirection(direction);
            projectileController.speed = projectileSpeed;
            projectileController.fromPlayer = true;
            projectileController.ownerID = playerID; // Set the owner ID to track which player shot it
        }
        else
        {
            // If no projectile controller, just add force to the rigidbody
            Rigidbody2D rb = projectile.GetComponent<Rigidbody2D>();
            if (rb != null)
            {
                rb.velocity = direction * projectileSpeed;
            }
        }

        // Destroy after 5 seconds if it doesn't hit anything
        Destroy(projectile, 5f);
    }

    Vector3 GetShootDirection()
    {
        // For Player 1 with mouse support, aim at mouse position if recently moved
        if (playerID == 1 && useMouseForPlayer1 && 
            (Input.GetMouseButton(0) || Input.GetMouseButtonDown(0) || Input.mousePresent))
        {
            Vector3 aimDirection = (mouseWorldPosition - transform.position).normalized;
            // Keep it 2D
            aimDirection.z = 0;
            
            return aimDirection;
        }
        
        // Default directional shooting based on player movement/orientation
        Rigidbody2D rb = GetComponent<Rigidbody2D>();
        
        if (rb != null && rb.velocity.magnitude > 0.1f)
        {
            // Shoot in the direction the player is moving
            return rb.velocity.normalized;
        }
        
        // Default - shoot based on player orientation
        SpriteRenderer spriteRenderer = GetComponent<SpriteRenderer>();
        if (spriteRenderer != null && spriteRenderer.flipX)
        {
            return Vector3.left;
        }
        
        return Vector3.right; // Default direction
    }
    
    // Draw aiming line in editor for debugging
    private void OnDrawGizmos()
    {
        if (playerID == 1 && useMouseForPlayer1 && Application.isPlaying)
        {
            // Draw a line showing the aim direction
            Gizmos.color = Color.red;
            Gizmos.DrawLine(transform.position, transform.position + (Vector3)GetShootDirection() * 2f);
        }
    }
}