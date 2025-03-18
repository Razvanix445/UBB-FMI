using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DoorController : MonoBehaviour
{
    public enum DoorType
    {
        Key,       // Requires a key to open
        Target,    // Opens when targets are hit
        Checkpoint // Saves checkpoint when opened
    }
    
    public DoorType doorType = DoorType.Key;
    public int keyID = 0;  // The ID of the key needed to open this door
    public float openSpeed = 3.0f; // Speed at which the door opens
    public float openAngle = 90.0f; // How far the door opens (in degrees)
    public bool consumeKey = false; // Whether the key is consumed when used
    public AudioClip openSound;
    public AudioClip lockedSound;
    
    private bool isOpen = false;
    private bool isOpening = false;
    private Vector3 initialRotation;
    private AudioSource audioSource;

    private bool playerInRange = false;
    private KeyInventory playerKeyInventory;
    
    // For checkpoint functionality
    private PlayerController playerController;
    
    // For target door
    public List<Target> targets = new List<Target>();
    private int targetsHit = 0;
    
    void Start()
    {
        initialRotation = transform.eulerAngles;
        audioSource = GetComponent<AudioSource>();
        if (audioSource == null)
        {
            audioSource = gameObject.AddComponent<AudioSource>();
        }
        
        // Find player controller for checkpoint functionality
        if (doorType == DoorType.Checkpoint)
        {
            playerController = GameObject.FindGameObjectWithTag("Player").GetComponent<PlayerController>();
        }
        
        // Register with targets if this is a target door
        if (doorType == DoorType.Target)
        {
            foreach (Target target in targets)
            {
                if (target != null)
                {
                    target.SetDoor(this);
                }
            }
        }
    }
    
    void Update()
    {
        // Check if player is near the door and presses the interaction key
        if (playerInRange && Input.GetKeyDown(KeyCode.E))
        {
            TryOpen();
        }
    }
    
    public void TryOpen()
    {
        if (isOpen || isOpening)
            return;
            
        switch (doorType)
        {
            case DoorType.Key:
                // Get player's key inventory
                GameObject player = GameObject.FindGameObjectWithTag("Player");
                KeyInventory inventory = player.GetComponent<KeyInventory>();
            
                if (inventory != null && inventory.HasKey(keyID))
                {
                    Debug.Log("Player has key " + keyID + ", opening door");
                
                    // Disable collider so player can walk through
                    Collider doorCollider = GetComponent<Collider>();
                    if (doorCollider != null)
                    {
                        doorCollider.enabled = false;
                    }
                
                    Open();
                
                    // Consume key if specified
                    if (consumeKey)
                    {
                        inventory.UseKey(keyID);
                    }
                
                    // Save checkpoint
                    SaveCheckpoint();
                }
                else
                {
                    Debug.Log("Door is locked! Need key " + keyID);
                    // Play locked sound
                    if (lockedSound != null)
                    {
                        audioSource.PlayOneShot(lockedSound);
                    }
                }
                break;
                
            case DoorType.Target:
                // Target doors are opened by the target hit events
                // Nothing to do here
                break;
                
            case DoorType.Checkpoint:
                // Checkpoint doors open immediately and save a checkpoint
                Open();
                SaveCheckpoint();
                break;
        }
    }
    
    void TryOpenWithKey()
    {
        // Try to find the player's key inventory
        if (playerKeyInventory == null)
        {
            Debug.LogWarning("Cannot find player's key inventory!");
            return;
        }
    
        // Check if player has the key
        if (playerKeyInventory.HasKey(keyID))
        {
            // Open the door
            Open();
        
            // Consume key if specified
            if (consumeKey)
            {
                playerKeyInventory.UseKey(keyID);
            }
        
            // Save checkpoint
            SaveCheckpoint();
        
            // Update UI
            GameUI.Instance.ShowInteractionPrompt("Door unlocked!");
            // Hide the message after a delay
            StartCoroutine(HidePromptAfterDelay(1.5f));
        }
        else
        {
            // Play locked sound
            if (lockedSound != null)
            {
                audioSource.PlayOneShot(lockedSound);
            }
        
            // Update UI
            GameUI.Instance.ShowInteractionPrompt("Door is locked! Need key #" + keyID);
        
            Debug.Log("Door is locked! Need key " + keyID);
        }
    }
    
    IEnumerator HidePromptAfterDelay(float delay)
    {
        yield return new WaitForSeconds(delay);
        if (!playerInRange)
        {
            GameUI.Instance.HideInteractionPrompt();
        }
    }
    
    public void NotifyTargetHit()
    {
        if (doorType != DoorType.Target)
            return;
            
        targetsHit++;
        
        // If all targets have been hit, open the door
        if (targetsHit >= targets.Count && !isOpen && !isOpening)
        {
            Open();
            SaveCheckpoint();
        }
    }
    
    void Open()
    {
        if (isOpen || isOpening)
            return;
            
        isOpening = true;
        
        // Play open sound
        if (openSound != null)
        {
            audioSource.PlayOneShot(openSound);
        }
        
        Collider[] colliders = GetComponents<Collider>();
        foreach (Collider col in colliders)
        {
            // Disable only the non-trigger collider
            if (!col.isTrigger)
            {
                col.enabled = false;
            }
        }
        
        // Start opening animation
        StartCoroutine(OpenDoorAnimation());
    }
    
    IEnumerator OpenDoorAnimation()
    {
        // Target rotation is current + openAngle around Y axis
        Vector3 targetRotation = initialRotation + new Vector3(0, openAngle, 0);
        float elapsed = 0;
        
        while (elapsed < 1.0f)
        {
            elapsed += Time.deltaTime * openSpeed;
            float t = Mathf.Clamp01(elapsed);
            
            // Interpolate rotation
            transform.eulerAngles = Vector3.Lerp(initialRotation, targetRotation, t);
            
            yield return null;
        }
        
        // Make sure door ends at exactly the target angle
        transform.eulerAngles = targetRotation;
        
        isOpen = true;
        isOpening = false;
    }
    
    void SaveCheckpoint()
    {
        if (playerController != null)
        {
            playerController.SaveCheckpoint();
            Debug.Log("Checkpoint saved after door opened");
        }
    }
    
    void OnTriggerEnter(Collider other)
    {
        Debug.Log("Something entered door trigger: " + other.gameObject.name + " with tag: " + other.tag);
    
        if (other.CompareTag("Player"))
        {
            playerInRange = true;
            playerKeyInventory = other.GetComponent<KeyInventory>();
            Debug.Log("Player entered door trigger. Door type: " + doorType + ", Key ID needed: " + keyID);
        
            if (doorType == DoorType.Key)
            {
                // Get the player's key inventory
                KeyInventory keyInventory = other.GetComponent<KeyInventory>();
                if (keyInventory != null)
                {
                    Debug.Log("Player has KeyInventory. Checking for key " + keyID + ": " + keyInventory.HasKey(keyID));
                }
                else
                {
                    Debug.LogError("Player doesn't have KeyInventory component!");
                }
                if (playerKeyInventory != null && playerKeyInventory.HasKey(keyID))
                {
                    GameUI.Instance.ShowInteractionPrompt("Press E to unlock door");
                }
                else
                {
                    GameUI.Instance.ShowInteractionPrompt("Door is locked. Find key #" + keyID);
                }
            }
            else if (doorType == DoorType.Checkpoint)
            {
                GameUI.Instance.ShowInteractionPrompt("Press E to open door");
            }
        }
    }
    
    void OnTriggerExit(Collider other)
    {
        if (other.CompareTag("Player"))
        {
            playerInRange = false;
            GameUI.Instance.HideInteractionPrompt();
        }
    }
    
    public void PlayerEnterInteractionZone(GameObject player)
    {
        playerInRange = true;
        playerKeyInventory = player.GetComponent<KeyInventory>();
    
        // Show appropriate message
        if (doorType == DoorType.Key)
        {
            if (playerKeyInventory != null && playerKeyInventory.HasKey(keyID))
            {
                Debug.Log("Player has key, showing prompt");
                if (GameUI.Instance != null)
                {
                    GameUI.Instance.ShowInteractionPrompt("Press E to unlock door");
                }
            }
            else
            {
                Debug.Log("Player doesn't have key");
                if (GameUI.Instance != null)
                {
                    GameUI.Instance.ShowInteractionPrompt("Door is locked. Find key #" + keyID);
                }
            }
        }
        else if (doorType == DoorType.Checkpoint)
        {
            if (GameUI.Instance != null)
            {
                GameUI.Instance.ShowInteractionPrompt("Press E to open door");
            }
        }
    }

    public void PlayerExitInteractionZone()
    {
        playerInRange = false;
        if (GameUI.Instance != null)
        {
            GameUI.Instance.HideInteractionPrompt();
        }
    }
}