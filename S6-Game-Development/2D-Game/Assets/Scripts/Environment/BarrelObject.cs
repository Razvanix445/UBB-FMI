using UnityEngine;
using System.Collections;

public class BarrelObject : MonoBehaviour
{
    [Header("General Settings")]
    public int pointValue = 10;
    
    [Header("Destruction Settings")]
    public float health = 3f;
    public int numberOfDebris = 5;
    public float explosionForce = 300f;
    public float explosionRadius = 2f;
    public GameObject debrisPrefab;
    
    [Header("Physics Settings")]
    public float mass = 3f;
    public float drag = 2f;
    public float pushResistance = 0.8f; // Higher values make object harder to push
    public bool rotateDuringMovement = true;
    public float maxRotationSpeed = 20f;
    
    // Components
    private SpriteRenderer spriteRenderer;
    private Rigidbody2D rb;
    private float currentVelocity = 0f;
    private bool isDying = false;
    
    void Start()
    {
        // Get components
        spriteRenderer = GetComponent<SpriteRenderer>();
        
        // Set up rigidbody
        rb = GetComponent<Rigidbody2D>();
        if (rb == null)
        {
            rb = gameObject.AddComponent<Rigidbody2D>();
        }
        
        // Configure the rigidbody for physics
        rb.mass = mass;
        rb.drag = drag;
        rb.angularDrag = 0.5f;
        rb.gravityScale = 0f; // No gravity for top-down game
        rb.collisionDetectionMode = CollisionDetectionMode2D.Continuous;
        rb.interpolation = RigidbodyInterpolation2D.Interpolate;
        rb.freezeRotation = !rotateDuringMovement;
        
        // Make sure we have a collider
        if (GetComponent<Collider2D>() == null)
        {
            BoxCollider2D collider = gameObject.AddComponent<BoxCollider2D>();
            
            // If we have a sprite, match the collider to it
            if (spriteRenderer != null)
            {
                collider.size = spriteRenderer.sprite.bounds.size;
            }
        }
    }
    
    void Update()
    {
        if (isDying) return;
        
        if (rotateDuringMovement)
        {
            // Rotate the object based on movement speed
            currentVelocity = rb.velocity.magnitude;
            if (currentVelocity > 0.1f)
            {
                float rotationAmount = Mathf.Clamp(currentVelocity * 10f, 0, maxRotationSpeed);
                transform.Rotate(0, 0, rotationAmount * Time.deltaTime);
            }
        }
    }
    
    public void TakeDamage(float amount)
    {
        if (isDying) return;
        
        health -= amount;
        
        // Visual feedback
        StartCoroutine(FlashSprite());
        
        if (health <= 0)
        {
            Explode();
        }
    }
    
    IEnumerator FlashSprite()
    {
        if (spriteRenderer != null)
        {
            spriteRenderer.color = Color.red;
            yield return new WaitForSeconds(0.1f);
            spriteRenderer.color = Color.white;
        }
    }
    
    void Explode()
    {
        isDying = true;
        
        // Award points to the player who destroyed this
        if (TwoPlayerManager.Instance != null)
        {
            // Default to player 1 for now, or you could track which player hit it last
            TwoPlayerManager.Instance.AddScore(1, pointValue);
        }
        
        // Disable physics
        if (rb != null)
        {
            rb.velocity = Vector2.zero;
            rb.angularVelocity = 0;
            rb.isKinematic = true;
        }
        
        // Disable collider
        Collider2D col = GetComponent<Collider2D>();
        if (col != null)
        {
            col.enabled = false;
        }
        
        // Create debris
        for (int i = 0; i < numberOfDebris; i++)
        {
            if (debrisPrefab != null)
            {
                GameObject debris = Instantiate(debrisPrefab, transform.position, Quaternion.identity);
                
                // Add random rotation
                debris.transform.rotation = Quaternion.Euler(0, 0, Random.Range(0, 360));
                
                // Add physics
                Rigidbody2D debrisRb = debris.GetComponent<Rigidbody2D>();
                if (debrisRb != null)
                {
                    // Add explosion force
                    Vector2 randomDir = Random.insideUnitCircle.normalized;
                    debrisRb.AddForce(randomDir * explosionForce);
                    
                    // Add random torque (rotational force)
                    debrisRb.AddTorque(Random.Range(-10f, 10f), ForceMode2D.Impulse);
                    
                    // Destroy after delay
                    Destroy(debris, 2f);
                }
            }
        }
        
        // Make the crate fade away
        StartCoroutine(FadeAndDestroy());
    }
    
    IEnumerator FadeAndDestroy()
    {
        float duration = 0.5f;
        float elapsed = 0;
        
        Color startColor = spriteRenderer.color;
        Color endColor = new Color(startColor.r, startColor.g, startColor.b, 0);
        
        while (elapsed < duration)
        {
            spriteRenderer.color = Color.Lerp(startColor, endColor, elapsed / duration);
            elapsed += Time.deltaTime;
            yield return null;
        }
        
        // Destroy the original object
        Destroy(gameObject);
    }
    
    void OnCollisionEnter2D(Collision2D collision)
    {
        // Add impact sound or effect here if needed
        if (collision.relativeVelocity.magnitude > 2)
        {
            // Play impact sound
            // AudioSource.PlayClipAtPoint(impactSound, transform.position);
        }
    }
    
    // This method is called when something tries to push this object
    public void ApplyForce(Vector2 direction, float strength)
    {
        if (isDying) return;
        
        // Apply the push force reduced by resistance
        rb.AddForce(direction * strength * (1f - pushResistance), ForceMode2D.Impulse);
    }
    
    void OnCollisionStay2D(Collision2D collision)
    {
        if (isDying) return;
        
        // If a player is pushing this object
        if (collision.gameObject.CompareTag("Player"))
        {
            Rigidbody2D playerRb = collision.gameObject.GetComponent<Rigidbody2D>();
            if (playerRb != null && playerRb.velocity.magnitude > 0.1f)
            {
                // Calculate push direction
                Vector2 pushDirection = (transform.position - collision.gameObject.transform.position).normalized;
                
                // Calculate push strength based on player's movement
                float pushStrength = playerRb.velocity.magnitude * 0.5f;
                
                // Apply force
                ApplyForce(pushDirection, pushStrength);
            }
        }
    }
    
    void OnTriggerEnter2D(Collider2D other)
    {
        if (isDying) return;
        
        // Handle projectile hits
        if (other.CompareTag("Projectile"))
        {
            ProjectileController projectile = other.GetComponent<ProjectileController>();
            if (projectile != null)
            {
                TakeDamage(1);
            }
        }
    }
}