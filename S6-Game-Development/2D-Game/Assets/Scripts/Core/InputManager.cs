using UnityEngine;
using System;

public class InputManager : MonoBehaviour
{
    // Singleton instance
    public static InputManager Instance { get; private set; }

    // Events for other scripts to subscribe to
    public event Action<Vector2> OnMoveInput;
    public event Action OnShootInput;
    public event Action OnPauseInput;
    
    // Input state
    private Vector2 moveInput;
    private bool shootInput;
    private bool pauseInput;

    void Awake()
    {
        // Singleton pattern implementation
        if (Instance == null)
        {
            Instance = this;
            DontDestroyOnLoad(gameObject);
        }
        else
        {
            Destroy(gameObject);
        }
    }

    void Update()
    {
        // Process pause input
        if (Input.GetKeyDown(KeyCode.Escape) || Input.GetKeyDown(KeyCode.P))
        {
            pauseInput = true;
            OnPauseInput?.Invoke();
        }
        
        // Don't process other inputs if game is paused or over
        if (GameManager.Instance != null && (GameManager.Instance.gameIsPaused || GameManager.Instance.gameIsOver))
            return;
        
        // Process movement input
        float horizontal = Input.GetAxisRaw("Horizontal");
        float vertical = Input.GetAxisRaw("Vertical");
        moveInput = new Vector2(horizontal, vertical).normalized;
        
        // Fire movement event only if there's actual input
        if (moveInput.magnitude > 0.1f)
        {
            OnMoveInput?.Invoke(moveInput);
        }
        
        // Process shooting input
        if (Input.GetKeyDown(KeyCode.Space) || Input.GetMouseButtonDown(0))
        {
            shootInput = true;
            OnShootInput?.Invoke();
        }
    }
    
    // Public methods to check input state
    public Vector2 GetMoveInput()
    {
        return moveInput;
    }
    
    public bool GetShootInputDown()
    {
        // One-time use input, resets after being checked
        if (shootInput)
        {
            shootInput = false;
            return true;
        }
        return false;
    }
    
    public bool GetPauseInputDown()
    {
        // One-time use input, resets after being checked
        if (pauseInput)
        {
            pauseInput = false;
            return true;
        }
        return false;
    }
}