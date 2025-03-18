using UnityEngine;
using System.Collections;

public class MonsterController : MonoBehaviour
{
    [Header("Movement Settings")]
    public float moveSpeed = 2f;
    public float detectionRange = 7f;
    public float minDistanceToPlayer = 3f;

    [Header("Attack Settings")]
    public GameObject projectilePrefab;
    public float shootingCooldown = 2f;
    public float projectileSpeed = 5f;

    [Header("Health Settings")]
    public int health = 3;

    [Header("Score Settings")]
    public int pointValue = 100;

    // Internal references
    private Transform player;
    private SpriteRenderer spriteRenderer;
    private Animator animator;
    private float lastShotTime;
    private Vector3 randomMovementTarget;
    private float nextRandomMovementTime;

    // Animation states
    private const string IDLE = "MonsterIdle";
    private const string WALK = "MonsterWalk";
    private const string ATTACK = "MonsterAttack";

    void Start()
    {
        spriteRenderer = GetComponent<SpriteRenderer>();
        animator = GetComponent<Animator>();

        // Find the player by tag
        if (GameObject.FindGameObjectWithTag("Player") != null)
        {
            player = GameObject.FindGameObjectWithTag("Player").transform;
        }
        else
        {
            Debug.LogError("Player not found! Make sure your player has the 'Player' tag.");
        }

        // Set initial random movement target
        SetNewRandomMovementTarget();

        // Make sure this GameObject has a SpriteSorter component for proper depth sorting
        if (GetComponent<SpriteSorter>() == null)
        {
            gameObject.AddComponent<SpriteSorter>();
        }
    }

    void Update()
    {
        if (player == null) return;

        // Calculate distance to player
        float distanceToPlayer = Vector2.Distance(transform.position, player.position);

        // If player is within detection range
        if (distanceToPlayer < detectionRange)
        {
            // Move towards player but maintain minimum distance
            if (distanceToPlayer > minDistanceToPlayer)
            {
                MoveTowardsPlayer();
                PlayAnimation(WALK);
            }
            else
            {
                // At optimal distance, try to shoot
                AttemptToShoot();
                PlayAnimation(IDLE);
            }
        }
        else
        {
            // Random wandering behavior when player is out of range
            RandomMovement();
        }

        // Face the right direction based on movement
        UpdateSpriteDirection();
    }

    void MoveTowardsPlayer()
    {
        Vector2 direction = (player.position - transform.position).normalized;
        transform.position += (Vector3)direction * moveSpeed * Time.deltaTime;
    }

    void AttemptToShoot()
    {
        if (Time.time > lastShotTime + shootingCooldown)
        {
            lastShotTime = Time.time;

            // Play attack animation
            PlayAnimation(ATTACK);

            // Launch projectile
            ShootProjectile();
        }
    }

    void ShootProjectile()
    {
        if (projectilePrefab != null)
        {
            // Instantiate the projectile
            GameObject projectile = Instantiate(
                projectilePrefab,
                transform.position,
                Quaternion.identity
            );

            // Calculate direction towards player
            Vector2 direction = (player.position - transform.position).normalized;

            // Add the ProjectileController component and set its direction
            ProjectileController projectileController = projectile.GetComponent<ProjectileController>();
            if (projectileController != null)
            {
                projectileController.SetDirection(direction);
                projectileController.speed = projectileSpeed;
                projectileController.fromPlayer = false;
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

            // Destroy the projectile after 5 seconds to prevent cluttering the scene
            Destroy(projectile, 5f);
        }
    }

    void RandomMovement()
    {
        if (Time.time > nextRandomMovementTime)
        {
            SetNewRandomMovementTarget();
        }

        // Move towards the random target
        Vector3 direction = (randomMovementTarget - transform.position).normalized;
        transform.position += direction * (moveSpeed * 0.5f) * Time.deltaTime;

        // If we're close enough to the target, set a new one
        if (Vector3.Distance(transform.position, randomMovementTarget) < 0.1f)
        {
            SetNewRandomMovementTarget();
        }

        PlayAnimation(WALK);
    }

    void SetNewRandomMovementTarget()
    {
        // Set a new random position within a reasonable range of current position
        float randomX = Random.Range(-5f, 5f);
        float randomY = Random.Range(-5f, 5f);
        randomMovementTarget = transform.position + new Vector3(randomX, randomY, 0);

        // Set time for next random movement
        nextRandomMovementTime = Time.time + Random.Range(2f, 5f);
    }

    void UpdateSpriteDirection()
    {
        // Flip sprite based on movement direction relative to player
        if (player != null)
        {
            // Flip sprite horizontally based on direction to player
            spriteRenderer.flipX = transform.position.x > player.position.x;
        }
    }

    void PlayAnimation(string animationName)
    {
        if (animator != null)
        {
            if (!animator.GetCurrentAnimatorStateInfo(0).IsName(animationName))
            {
                animator.Play(animationName);
            }
        }
    }

    public void TakeDamage(int amount)
    {
        health -= amount;

        // Flash the sprite when taking damage
        StartCoroutine(FlashSprite());

        if (health <= 0)
        {
            Die();
        }
    }

    IEnumerator FlashSprite()
    {
        spriteRenderer.color = Color.red;
        yield return new WaitForSeconds(0.1f);
        spriteRenderer.color = Color.white;
    }

    void Die()
    {
        if (ScoreManager.Instance != null)
        {
            ScoreManager.Instance.AddScore(pointValue);
        }

        // Add death animation here if you have one

        // Disable components
        GetComponent<Collider2D>().enabled = false;
        enabled = false;

        // Destroy after delay
        Destroy(gameObject, 1f);
    }

    void OnDrawGizmosSelected()
    {
        // Draw detection range
        Gizmos.color = Color.yellow;
        Gizmos.DrawWireSphere(transform.position, detectionRange);

        // Draw minimum distance
        Gizmos.color = Color.red;
        Gizmos.DrawWireSphere(transform.position, minDistanceToPlayer);
    }
}