using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BowController : MonoBehaviour
{
    public GameObject arrowPrefab;
    public Transform arrowSpawnPoint;
    public float arrowForce = 30f;
    public float drawSpeed = 1f;
    public AudioClip bowDrawSound;
    public AudioClip bowReleaseSound;
    
    private AudioSource audioSource;
    private bool isDrawing = false;
    private float drawAmount = 0f;
    
    void Start()
    {
        audioSource = GetComponent<AudioSource>();
        if (audioSource == null)
        {
            audioSource = gameObject.AddComponent<AudioSource>();
        }
    }
    
    void Update()
    {
        // Start drawing bow when mouse button is pressed
        if (Input.GetMouseButtonDown(0))
        {
            StartDrawingBow();
        }
        
        // Continue drawing bow while mouse button is held
        if (Input.GetMouseButton(0) && isDrawing)
        {
            ContinueDrawingBow();
        }
        
        // Release arrow when mouse button is released
        if (Input.GetMouseButtonUp(0) && isDrawing)
        {
            ReleaseArrow();
        }
    }
    
    void StartDrawingBow()
    {
        isDrawing = true;
        drawAmount = 0f;
        
        if (bowDrawSound != null)
        {
            audioSource.PlayOneShot(bowDrawSound);
        }
    }
    
    void ContinueDrawingBow()
    {
        // Increase draw amount up to a maximum of 1.0
        drawAmount += Time.deltaTime * drawSpeed;
        drawAmount = Mathf.Clamp01(drawAmount);
    }
    
    void ReleaseArrow()
    {
        // Calculate force based on draw amount
        float currentForce = arrowForce * drawAmount;
                
        // Create arrow
        GameObject arrow = Instantiate(arrowPrefab, arrowSpawnPoint.position, arrowSpawnPoint.rotation);
        
        // Add physics force to arrow
        Rigidbody rb = arrow.GetComponent<Rigidbody>();
        if (rb != null)
        {
            rb.AddForce(arrowSpawnPoint.forward * currentForce, ForceMode.Impulse);
        }
        
        // Play release sound
        if (bowReleaseSound != null)
        {
            audioSource.PlayOneShot(bowReleaseSound);
        }
        
        // Reset drawing state
        isDrawing = false;
        drawAmount = 0f;
    }
}