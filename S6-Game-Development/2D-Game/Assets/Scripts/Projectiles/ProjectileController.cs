using UnityEngine;

public class ProjectileController : MonoBehaviour
{
    public float speed = 5f;
    public int damage = 1;
    public bool fromPlayer = false;
    public int ownerID = 0; // 1 = Player 1, 2 = Player 2, 0 = Monster/Other

    private Vector2 direction;
    private SpriteRenderer spriteRenderer;

    void Start()
    {
        spriteRenderer = GetComponent<SpriteRenderer>();
    }

    void Update()
    {
        // Move in the set direction
        transform.position += (Vector3)(direction * speed * Time.deltaTime);

        // Rotate to face movement direction
        float angle = Mathf.Atan2(direction.y, direction.x) * Mathf.Rad2Deg;
        transform.rotation = Quaternion.AngleAxis(angle, Vector3.forward);
    }

    public void SetDirection(Vector2 newDirection)
    {
        direction = newDirection.normalized;
    }

    void OnTriggerEnter2D(Collider2D other)
    {
        // If projectile is from player, check what it hit
        if (fromPlayer)
        {
            // Hit an enemy
            if (other.CompareTag("Enemy"))
            {
                MonsterController monster = other.GetComponent<MonsterController>();
                if (monster != null)
                {
                    monster.TakeDamage(damage);
                    
                    // If in two-player mode, add score
                    if (TwoPlayerManager.Instance != null)
                    {
                        TwoPlayerManager.Instance.AddScore(ownerID, monster.pointValue);
                    }
                }
                DestroyProjectile();
            }
            // Hit another player
            else if (other.CompareTag("Player"))
            {
                // Get the hit player's controller to check their ID
                PlayerController playerController = other.GetComponent<PlayerController>();
                if (playerController != null && playerController.playerID != ownerID)
                {
                    // Only damage the other player, not the owner
                    PlayerHealth playerHealth = other.GetComponent<PlayerHealth>();
                    if (playerHealth != null)
                    {
                        playerHealth.TakeDamage(damage);
                        
                        // Add score to the player who hit the other player
                        if (TwoPlayerManager.Instance != null)
                        {
                            TwoPlayerManager.Instance.AddScore(ownerID, 100);
                        }
                    }
                    DestroyProjectile();
                }
            }
        }
        // If projectile is from monster, damage any player
        else if (!fromPlayer && other.CompareTag("Player"))
        {
            PlayerHealth playerHealth = other.GetComponent<PlayerHealth>();
            if (playerHealth != null)
            {
                playerHealth.TakeDamage(damage);
            }
            DestroyProjectile();
        }
        // Collide with obstacles
        else if (other.CompareTag("Obstacle"))
        {
            DestroyProjectile();
        }
    }

    void DestroyProjectile()
    {
        // Create a simple hit effect
        // You can replace this with a particle effect if you have one
        GameObject effect = new GameObject("HitEffect");
        effect.transform.position = transform.position;
        SpriteRenderer effectSprite = effect.AddComponent<SpriteRenderer>();
        effectSprite.sprite = spriteRenderer.sprite;
        effectSprite.color = Color.white;

        // Scale down and fade out effect
        Destroy(effect, 0.2f);

        // Destroy projectile
        Destroy(gameObject);
    }
}