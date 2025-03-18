using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Target : MonoBehaviour
{
    public float resetTime = 5.0f;     // Time until the target resets after being hit
    public float rotationSpeed = 50f;  // Rotation speed when active
    public AudioClip hitSound;
    public GameObject hitEffect;        // Optional particle effect when hit
    
    private bool isHit = false;
    private DoorController linkedDoor;
    private Material originalMaterial;
    private AudioSource audioSource;
    private Vector3 originalPosition;
    private Quaternion originalRotation;
    
    void Start()
    {
        audioSource = GetComponent<AudioSource>();
        if (audioSource == null)
        {
            audioSource = gameObject.AddComponent<AudioSource>();
        }
        
        // Store original material and transform
        Renderer renderer = GetComponent<Renderer>();
        if (renderer != null)
        {
            originalMaterial = renderer.material;
        }
        
        originalPosition = transform.position;
        originalRotation = transform.rotation;
    }
    
    void Update()
    {
        // Add some movement/rotation to make the target more visible
        if (!isHit)
        {
            transform.Rotate(Vector3.up, rotationSpeed * Time.deltaTime);
        }
    }
    
    public void SetDoor(DoorController door)
    {
        linkedDoor = door;
    }
    
    public void Hit()
    {
        if (isHit)
            return;
            
        isHit = true;
        
        // Play hit sound
        if (hitSound != null)
        {
            audioSource.PlayOneShot(hitSound);
        }
        
        // Show hit effect
        if (hitEffect != null)
        {
            Instantiate(hitEffect, transform.position, Quaternion.identity);
        }
        
        // Change appearance
        Renderer renderer = GetComponent<Renderer>();
        if (renderer != null)
        {
            // Change color to red
            Material hitMaterial = new Material(originalMaterial);
            hitMaterial.color = Color.red;
            renderer.material = hitMaterial;
        }
        
        // Notify the linked door
        if (linkedDoor != null)
        {
            linkedDoor.NotifyTargetHit();
        }
        
        // Knock over the target
        StartCoroutine(KnockOverAnimation());
        
        // Reset after delay (optional)
        StartCoroutine(ResetAfterDelay());
    }
    
    IEnumerator KnockOverAnimation()
    {
        // Calculate a random direction to fall in
        Vector3 fallDirection = new Vector3(Random.Range(-1f, 1f), 0, Random.Range(-1f, 1f)).normalized;
        Quaternion targetRotation = Quaternion.Euler(90f * fallDirection.x, 0, 90f * fallDirection.z);
        
        float elapsed = 0;
        float fallDuration = 0.5f;
        
        while (elapsed < fallDuration)
        {
            elapsed += Time.deltaTime;
            float t = Mathf.Clamp01(elapsed / fallDuration);
            
            // Interpolate rotation
            transform.rotation = Quaternion.Slerp(originalRotation, targetRotation, t);
            
            yield return null;
        }
    }
    
    IEnumerator ResetAfterDelay()
    {
        yield return new WaitForSeconds(resetTime);
        
        // Only reset if not all targets have been hit (door not opened)
        if (linkedDoor != null)
        {
            // Reset target
            isHit = false;
            
            // Reset appearance
            Renderer renderer = GetComponent<Renderer>();
            if (renderer != null)
            {
                renderer.material = originalMaterial;
            }
            
            // Reset position and rotation
            transform.position = originalPosition;
            transform.rotation = originalRotation;
        }
    }
}