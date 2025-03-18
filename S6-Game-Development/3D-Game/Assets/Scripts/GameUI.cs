using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class GameUI : MonoBehaviour
{
    public static GameUI Instance;
    
    public TextMeshProUGUI livesText;
    public TextMeshProUGUI keysText;
    public TextMeshProUGUI doorInteractionPrompt;
    
    private PlayerController playerController;
    private KeyInventory keyInventory;
    
    // List of all keyIDs in the game (for display)
    public List<int> allKeyIDs = new List<int>();
    
    void Awake()
    {
        // Singleton pattern
        if (Instance == null)
        {
            Instance = this;
        }
        else
        {
            Destroy(gameObject);
        }
    }
    
    void Start()
    {
        // Find player references
        GameObject player = GameObject.FindGameObjectWithTag("Player");
        if (player != null)
        {
            playerController = player.GetComponent<PlayerController>();
            keyInventory = player.GetComponent<KeyInventory>();
        }
    }
    
    void Update()
    {
        // Update lives display
        if (livesText != null && playerController != null)
        {
            livesText.text = "Lives: " + playerController.currentLives;
        }
        
        // Update keys display
        if (keysText != null && keyInventory != null)
        {
            string keyDisplay = "Keys: ";
            foreach (int keyID in allKeyIDs)
            {
                if (keyInventory.HasKey(keyID))
                {
                    keyDisplay += keyID + " ";
                }
                else
                {
                    keyDisplay += "? ";
                }
            }
            keysText.text = keyDisplay;
        }
    }
    
    public void ShowInteractionPrompt(string message)
    {
        if (doorInteractionPrompt != null)
        {
            doorInteractionPrompt.text = message;
            doorInteractionPrompt.gameObject.SetActive(true);
        }
    }
    
    public void HideInteractionPrompt()
    {
        if (doorInteractionPrompt != null)
        {
            doorInteractionPrompt.gameObject.SetActive(false);
        }
    }
}