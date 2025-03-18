using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class PlayerHealth : MonoBehaviour
{
    [Header("Player Identity")]
    [Tooltip("Should match PlayerController.playerID")]
    public int playerID = 1;

    [Header("Health Settings")]
    public int maxHealth = 5;
    private int currentHealth;
    public float respawnTime = 3f;
    public Transform respawnPoint;

    [Header("UI References")]
    public Image[] healthIcons;
    public Text healthText;

    [Header("Invincibility")]
    public float invincibilityTime = 1f;
    private bool isInvincible = false;
    private bool isDead = false;

    // References
    private SpriteRenderer spriteRenderer;
    private PlayerController playerController;
    private PlayerShooting playerShooting;
    private Collider2D playerCollider;

    void Start()
    {
        spriteRenderer = GetComponent<SpriteRenderer>();
        playerController = GetComponent<PlayerController>();
        playerShooting = GetComponent<PlayerShooting>();
        playerCollider = GetComponent<Collider2D>();
        
        currentHealth = maxHealth;
        
        // If respawn point is not set, use current position
        if (respawnPoint == null)
        {
            respawnPoint = transform;
        }
        
        UpdateHealthUI();
    }

    public void TakeDamage(int amount)
    {
        // Check if player is currently invincible or dead
        if (isInvincible || isDead)
            return;

        currentHealth -= amount;

        // Clamp health to prevent negative values
        currentHealth = Mathf.Max(currentHealth, 0);

        // Flash sprite and start invincibility
        StartCoroutine(FlashSprite());
        StartCoroutine(InvincibilityFrames());

        // Update the UI
        UpdateHealthUI();

        // Check if player is dead
        if (currentHealth <= 0)
        {
            Die();
        }
    }

    public void AddHealth(int amount)
    {
        currentHealth += amount;

        // Clamp health to prevent exceeding max
        currentHealth = Mathf.Min(currentHealth, maxHealth);

        // Update the UI
        UpdateHealthUI();
    }

    void UpdateHealthUI()
    {
        // Update text if available
        if (healthText != null)
        {
            healthText.text = "Health: " + currentHealth + "/" + maxHealth;
        }

        // Update health icons if available
        if (healthIcons != null && healthIcons.Length > 0)
        {
            for (int i = 0; i < healthIcons.Length; i++)
            {
                if (i < currentHealth)
                {
                    healthIcons[i].enabled = true;
                }
                else
                {
                    healthIcons[i].enabled = false;
                }
            }
        }
    }

    IEnumerator FlashSprite()
    {
        // Flash the sprite red a few times
        for (int i = 0; i < 3; i++)
        {
            spriteRenderer.color = Color.red;
            yield return new WaitForSeconds(0.1f);
            spriteRenderer.color = Color.white;
            yield return new WaitForSeconds(0.1f);
        }
    }

    IEnumerator InvincibilityFrames()
    {
        isInvincible = true;
        yield return new WaitForSeconds(invincibilityTime);
        isInvincible = false;
    }

    void Die()
    {
        if (isDead)
            return;
            
        isDead = true;
        Debug.Log("Player " + playerID + " died!");
        
        // Disable player components
        if (playerController != null)
            playerController.enabled = false;
            
        if (playerShooting != null)
            playerShooting.enabled = false;
            
        if (playerCollider != null)
            playerCollider.enabled = false;
        
        // Make player transparent
        if (spriteRenderer != null)
        {
            Color fadeColor = spriteRenderer.color;
            fadeColor.a = 0.5f;
            spriteRenderer.color = fadeColor;
        }
        
        // If in two-player mode, add score to the other player
        int otherPlayerID = (playerID == 1) ? 2 : 1;
        if (TwoPlayerManager.Instance != null)
        {
            TwoPlayerManager.Instance.AddScore(otherPlayerID, 200);
        }
        
        // Respawn after delay
        StartCoroutine(RespawnAfterDelay());
    }
    
    IEnumerator RespawnAfterDelay()
    {
        yield return new WaitForSeconds(respawnTime);
        
        // Reset player state
        isDead = false;
        currentHealth = maxHealth;
        
        // Move to respawn point
        transform.position = respawnPoint.position;
        
        // Re-enable components
        if (playerController != null)
            playerController.enabled = true;
            
        if (playerShooting != null)
            playerShooting.enabled = true;
            
        if (playerCollider != null)
            playerCollider.enabled = true;
        
        // Restore visibility
        if (spriteRenderer != null)
        {
            Color normalColor = spriteRenderer.color;
            normalColor.a = 1f;
            spriteRenderer.color = normalColor;
        }
        
        // Update UI
        UpdateHealthUI();
        
        // Give temporary invincibility
        StartCoroutine(InvincibilityFrames());
    }
}