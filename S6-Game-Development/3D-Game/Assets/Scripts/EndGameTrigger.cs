using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EndGameTrigger : MonoBehaviour
{
    public GameObject victoryScreen; // Assign your UI canvas in the inspector
    
    private void OnTriggerEnter(Collider other)
    {
        Debug.Log("Something entered the trigger: " + other.gameObject.name);
        if (other.CompareTag("Player"))
        {
            Debug.Log("Player detected! Attempting to end game...");
            EndGame();
        }
    }
    
    private void EndGame()
    {
        // Show victory screen
        if (victoryScreen != null)
        {
            victoryScreen.SetActive(true);
            
            // Unlock cursor for button interaction
            Cursor.lockState = CursorLockMode.None;
            Cursor.visible = true;
            
            // Disable player movement (optional)
            GameObject player = GameObject.FindGameObjectWithTag("Player");
            if (player != null)
            {
                PlayerController playerController = player.GetComponent<PlayerController>();
                if (playerController != null)
                {
                    playerController.canMove = false;
                }
            }
        }
        
        Debug.Log("Game completed! Player reached the end.");
    }
}