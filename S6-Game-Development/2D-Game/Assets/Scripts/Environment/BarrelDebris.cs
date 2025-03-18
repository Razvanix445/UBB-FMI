using UnityEngine;

public class BarrelDebris : MonoBehaviour
{
    [Header("Settings")]
    public float fadeDelay = 1f;     // Time before starting to fade
    public float fadeDuration = 1f;  // How long the fade takes
    public float rotationSpeed = 5f; // How fast the debris rotates
    
    // Components
    private SpriteRenderer spriteRenderer;
    private float fadeTimer;
    private bool isFading = false;
    
    void Start()
    {
        spriteRenderer = GetComponent<SpriteRenderer>();
        
        // Start the fade timer
        fadeTimer = fadeDelay;
        
        // Make sure there's a collider and rigidbody
        if (GetComponent<Collider2D>() == null)
        {
            CircleCollider2D collider = gameObject.AddComponent<CircleCollider2D>();
            collider.radius = 0.1f;
        }
        
        if (GetComponent<Rigidbody2D>() == null)
        {
            Rigidbody2D rb = gameObject.AddComponent<Rigidbody2D>();
            rb.gravityScale = 0f;
            rb.drag = 1f;
            rb.angularDrag = 0.5f;
        }
    }
    
    void Update()
    {
        // Rotate the debris
        transform.Rotate(0, 0, rotationSpeed * Time.deltaTime);
        
        // Handle fading
        if (!isFading)
        {
            fadeTimer -= Time.deltaTime;
            if (fadeTimer <= 0)
            {
                isFading = true;
                fadeTimer = fadeDuration;
            }
        }
        else
        {
            // Fade out
            fadeTimer -= Time.deltaTime;
            float alpha = fadeTimer / fadeDuration;
            
            if (spriteRenderer != null)
            {
                Color color = spriteRenderer.color;
                color.a = alpha;
                spriteRenderer.color = color;
            }
            
            // Destroy when fully faded
            if (fadeTimer <= 0)
            {
                Destroy(gameObject);
            }
        }
    }
    
    void OnCollisionEnter2D(Collision2D collision)
    {
        // Reduce velocity slightly on collision
        Rigidbody2D rb = GetComponent<Rigidbody2D>();
        if (rb != null)
        {
            rb.velocity *= 0.8f;
        }
    }
}