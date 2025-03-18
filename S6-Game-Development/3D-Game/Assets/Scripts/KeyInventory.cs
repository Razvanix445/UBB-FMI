using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class KeyInventory : MonoBehaviour
{
    // Dictionary to track the keys the player has collected
    // Key = KeyID, Value = true if collected
    private Dictionary<int, bool> collectedKeys = new Dictionary<int, bool>();
    
    public AudioClip keyPickupSound;
    
    private AudioSource audioSource;
    
    void Start()
    {
        audioSource = GetComponent<AudioSource>();
        if (audioSource == null)
        {
            audioSource = gameObject.AddComponent<AudioSource>();
        }
    }
    
    // Check if the player has a specific key
    public bool HasKey(int keyID)
    {
        return collectedKeys.ContainsKey(keyID) && collectedKeys[keyID];
    }
    
    // Add a key to the inventory
    public void AddKey(int keyID)
    {
        collectedKeys[keyID] = true;
        
        // Play pickup sound
        if (keyPickupSound != null && audioSource != null)
        {
            audioSource.PlayOneShot(keyPickupSound);
        }
        
        Debug.Log("Key " + keyID + " collected!");
    }
    
    // Remove a key from the inventory (e.g., when used to open a door)
    public void UseKey(int keyID)
    {
        if (HasKey(keyID))
        {
            collectedKeys[keyID] = false;
            Debug.Log("Key " + keyID + " used!");
        }
        else
        {
            Debug.LogWarning("Tried to use key " + keyID + " but it's not in the inventory!");
        }
    }
}