using UnityEngine;
using System.Collections.Generic;

public class TwoPlayerCameraController : MonoBehaviour
{
    [Header("Target Settings")]
    public Transform player1;
    public Transform player2;
    public bool autoFindPlayers = true;

    [Header("Camera Settings")]
    public float minOrthographicSize = 5f; // Minimum zoom level
    public float maxOrthographicSize = 15f; // Maximum zoom level
    public float screenEdgeBuffer = 2f; // Space between players and screen edge
    public float zoomSpeed = 3f; // How fast camera zooms in/out
    public float moveSpeed = 5f; // How fast camera moves to new position
    
    [Header("Advanced Settings")]
    public float aspectRatio; // Set automatically based on screen dimensions
    public bool maintainOnlyHorizontalDistance = false; // If true, only zoom based on horizontal distance

    // Camera component
    private Camera cam;
    
    // Target position and size
    private Vector3 targetPosition;
    private float targetOrthoSize;
    
    // Fixed z position
    private float zPosition = -10f;

    void Awake()
    {
        cam = GetComponent<Camera>();
        
        // Calculate the screen's aspect ratio
        aspectRatio = (float)Screen.width / Screen.height;
        
        // Initialize at current values
        targetPosition = transform.position;
        targetOrthoSize = cam.orthographicSize;
    }
    
    void Start()
    {
        // Auto-find players if enabled
        if (autoFindPlayers)
        {
            FindPlayers();
        }
        
        // Set initial camera position and size
        if (player1 != null && player2 != null)
        {
            UpdateCameraTargets();
            
            // Set immediately on start
            transform.position = targetPosition;
            cam.orthographicSize = targetOrthoSize;
        }
    }
    
    void FindPlayers()
    {
        // Find players based on tag
        GameObject[] players = GameObject.FindGameObjectsWithTag("Player");
        List<Transform> playerTransforms = new List<Transform>();
        
        foreach (GameObject player in players)
        {
            PlayerController controller = player.GetComponent<PlayerController>();
            if (controller != null)
            {
                if (controller.playerID == 1)
                {
                    player1 = player.transform;
                }
                else if (controller.playerID == 2)
                {
                    player2 = player.transform;
                }
                
                playerTransforms.Add(player.transform);
            }
        }
        
        // If we didn't find players with IDs, just use the first two found
        if (player1 == null && playerTransforms.Count > 0)
        {
            player1 = playerTransforms[0];
        }
        
        if (player2 == null && playerTransforms.Count > 1)
        {
            player2 = playerTransforms[1];
        }
        
        if (player1 == null || player2 == null)
        {
            Debug.LogWarning("Could not find both players!");
        }
    }

    void LateUpdate()
    {
        if (player1 == null || player2 == null)
        {
            FindPlayers();
            return;
        }
        
        // Calculate target position and size
        UpdateCameraTargets();
        
        // Smoothly move camera position
        transform.position = Vector3.Lerp(transform.position, targetPosition, moveSpeed * Time.deltaTime);
        
        // Smoothly adjust orthographic size
        cam.orthographicSize = Mathf.Lerp(cam.orthographicSize, targetOrthoSize, zoomSpeed * Time.deltaTime);
    }
    
    void UpdateCameraTargets()
    {
        // Find the center point between players
        Vector3 centerPoint = (player1.position + player2.position) / 2f;
        
        // Always maintain z position
        targetPosition = new Vector3(centerPoint.x, centerPoint.y, zPosition);
        
        // Calculate required size based on distance between players
        float distanceX = Mathf.Abs(player1.position.x - player2.position.x);
        float distanceY = Mathf.Abs(player1.position.y - player2.position.y);
        
        // Calculate required orthographic size based on the distance
        float requiredSizeX = (distanceX / 2f + screenEdgeBuffer) / aspectRatio;
        float requiredSizeY = distanceY / 2f + screenEdgeBuffer;
        
        // Use the larger of the two sizes to ensure both fit on screen
        float requiredSize;
        
        if (maintainOnlyHorizontalDistance)
        {
            requiredSize = requiredSizeX;
        }
        else
        {
            requiredSize = Mathf.Max(requiredSizeX, requiredSizeY);
        }
        
        // Clamp size between min and max values
        targetOrthoSize = Mathf.Clamp(requiredSize, minOrthographicSize, maxOrthographicSize);
    }
    
    // Draw camera bounds in editor
    void OnDrawGizmos()
    {
        if (!Application.isPlaying || cam == null)
            return;
            
        Gizmos.color = Color.yellow;
        
        // Calculate the bounds of the camera view
        float vertExtent = cam.orthographicSize;
        float horizExtent = vertExtent * aspectRatio;
        
        Vector3 bottomLeft = transform.position + new Vector3(-horizExtent, -vertExtent, 0);
        Vector3 topRight = transform.position + new Vector3(horizExtent, vertExtent, 0);
        Vector3 topLeft = transform.position + new Vector3(-horizExtent, vertExtent, 0);
        Vector3 bottomRight = transform.position + new Vector3(horizExtent, -vertExtent, 0);
        
        // Draw lines representing the camera bounds
        Gizmos.DrawLine(bottomLeft, topLeft);
        Gizmos.DrawLine(topLeft, topRight);
        Gizmos.DrawLine(topRight, bottomRight);
        Gizmos.DrawLine(bottomRight, bottomLeft);
    }
}