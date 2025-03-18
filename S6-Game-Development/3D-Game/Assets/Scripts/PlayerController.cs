using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{
    private GameUI gameUI;
    
    public float walkSpeed = 6.0f;
    public float runSpeed = 10.0f;
    public float jumpSpeed = 8.0f;
    public float gravity = 20.0f;
    public float lookSpeed = 3.0f;
    public float lookXLimit = 45.0f;

    CharacterController characterController;
    Camera playerCamera;
    Vector3 moveDirection = Vector3.zero;
    float rotationX = 0;
    public bool canMove = true;

    // Lives system
    public int maxLives = 5;
    public int currentLives;
    private Vector3 lastCheckpointPosition;

    public float interactionDistance = 2.0f;

    void Start()
    {
        characterController = GetComponent<CharacterController>();
        playerCamera = GetComponentInChildren<Camera>();
        
        gameUI = GameUI.Instance;
        if (gameUI == null)
        {
            Debug.LogWarning("GameUI.Instance is null! Make sure GameUI exists in the scene and has initialized properly.");
        }
        
        // Lock cursor
        Cursor.lockState = CursorLockMode.Locked;
        Cursor.visible = false;
        
        // Initialize lives
        currentLives = maxLives;
        lastCheckpointPosition = transform.position;
    }

    void Update()
    {
        // We are grounded, so recalculate move direction based on axes
        Vector3 forward = transform.TransformDirection(Vector3.forward);
        Vector3 right = transform.TransformDirection(Vector3.right);
        
        // Press Left Shift to run
        bool isRunning = Input.GetKey(KeyCode.LeftShift);
        float curSpeedX = canMove ? (isRunning ? runSpeed : walkSpeed) * Input.GetAxis("Vertical") : 0;
        float curSpeedY = canMove ? (isRunning ? runSpeed : walkSpeed) * Input.GetAxis("Horizontal") : 0;
        float movementDirectionY = moveDirection.y;
        moveDirection = (forward * curSpeedX) + (right * curSpeedY);

        if (Input.GetButton("Jump") && canMove && characterController.isGrounded)
        {
            moveDirection.y = jumpSpeed;
        }
        else
        {
            moveDirection.y = movementDirectionY;
        }

        // Apply gravity. Gravity is multiplied by deltaTime twice (once here, and once below
        // when the moveDirection is multiplied by deltaTime). This is because gravity should be applied
        // as an acceleration (m/s^2)
        if (!characterController.isGrounded)
        {
            moveDirection.y -= gravity * Time.deltaTime;
        }

        // Move the controller
        characterController.Move(moveDirection * Time.deltaTime);

        // Player and Camera rotation
        if (canMove)
        {
            rotationX += -Input.GetAxis("Mouse Y") * lookSpeed;
            rotationX = Mathf.Clamp(rotationX, -lookXLimit, lookXLimit);
            playerCamera.transform.localRotation = Quaternion.Euler(rotationX, 0, 0);
            transform.rotation *= Quaternion.Euler(0, Input.GetAxis("Mouse X") * lookSpeed, 0);
        }

        // Door interaction with prompt
        Ray ray = new Ray(playerCamera.transform.position, playerCamera.transform.forward);
        RaycastHit hit;
    
        if (Physics.Raycast(ray, out hit, interactionDistance))
        {
            // Check if we're looking at a door
            DoorController door = hit.collider.GetComponent<DoorController>();
            if (door != null)
            {
                // Show appropriate prompt
                if (door.doorType == DoorController.DoorType.Key)
                {
                    KeyInventory inventory = GetComponent<KeyInventory>();
                    if (inventory != null && inventory.HasKey(door.keyID))
                    {
                        gameUI.ShowInteractionPrompt("Press E to unlock door");
                    }
                    else
                    {
                        gameUI.ShowInteractionPrompt("Door is locked. Need key #" + door.keyID);
                    }
                }
                else
                {
                    gameUI.ShowInteractionPrompt("Press E to open door");
                }
            
                // Try to open door when E is pressed
                if (Input.GetKeyDown(KeyCode.E))
                {
                    door.TryOpen();
                }
            }
            else
            {
                gameUI.HideInteractionPrompt();
            }
        }
        else
        {
            gameUI.HideInteractionPrompt();
        }
    }

    public void SaveCheckpoint()
    {
        lastCheckpointPosition = transform.position;
        Debug.Log("Checkpoint saved at: " + lastCheckpointPosition);
    }

    public void TakeDamage()
    {
        currentLives--;
        Debug.Log("Lives remaining: " + currentLives);
        
        if (currentLives <= 0)
        {
            RestartGame();
        }
        else
        {
            TeleportToCheckpoint();
        }
    }

    private void TeleportToCheckpoint()
    {
        characterController.enabled = false;
        transform.position = lastCheckpointPosition;
        characterController.enabled = true;
        Debug.Log("Teleported to checkpoint");
    }

    private void RestartGame()
    {
        Debug.Log("Game Over! Restarting game...");
        // In a complete game, you would reload the scene or restart the game logic here
        currentLives = maxLives;
        TeleportToCheckpoint();
    }
}