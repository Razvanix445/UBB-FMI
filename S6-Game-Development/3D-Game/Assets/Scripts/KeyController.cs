using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class KeyController : MonoBehaviour
{
    public int keyID = 0;
    public float rotationSpeed = 50f;
    public AudioClip pickupSound;
    
    private bool isCollected = false;
    
    void Update()
    {
        // Rotate the key to make it more visible
        transform.Rotate(Vector3.up, rotationSpeed * Time.deltaTime);
    }
    
    void OnTriggerEnter(Collider other)
    {
        if (isCollected) return;
        
        if (other.CompareTag("Player"))
        {
            // Add the key to player's inventory
            KeyInventory inventory = other.GetComponent<KeyInventory>();
            if (inventory != null)
            {
                inventory.AddKey(keyID);
                isCollected = true;
                
                // Play pickup sound
                if (pickupSound != null)
                {
                    AudioSource.PlayClipAtPoint(pickupSound, transform.position);
                }
                
                // Hide the key
                gameObject.SetActive(false);
            }
        }
    }
}