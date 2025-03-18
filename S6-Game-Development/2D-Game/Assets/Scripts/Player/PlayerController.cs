using UnityEngine;

public class PlayerController : MonoBehaviour
{
    [Header("Player Identity")]
    [Tooltip("1 = Player 1, 2 = Player 2")]
    public int playerID = 1;
    
    [Header("Movement Settings")]
    [SerializeField] private float moveSpeed = 5f;

    // Components
    private Animator animator;
    private SpriteRenderer spriteRenderer;
    private Rigidbody2D rb;
    private PlayerShooting playerShooting;

    // Animation state names
    private const string IDLE = "Player_Idle";
    private const string WALK_LEFT = "Player_WalkLeft";
    private const string WALK_RIGHT = "Player_WalkRight";
    private const string WALK_UP = "Player_WalkBack";
    private const string WALK_DOWN = "Player_WalkDown";
    
    // Direct input keys for more responsive control
    private KeyCode upKey;
    private KeyCode downKey;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode shootKey;

    private void Start()
    {
        // Get components
        animator = GetComponent<Animator>();
        spriteRenderer = GetComponent<SpriteRenderer>();
        rb = GetComponent<Rigidbody2D>();
        playerShooting = GetComponent<PlayerShooting>();
        
        // Set up direct key mapping based on player ID
        if (playerID == 1)
        {
            // Player 1: WASD
            upKey = KeyCode.W;
            downKey = KeyCode.S;
            leftKey = KeyCode.A;
            rightKey = KeyCode.D;
            shootKey = KeyCode.Space; // Space, but Player 1 handles this differently now
        }
        else
        {
            // Player 2: Arrow keys
            upKey = KeyCode.UpArrow;
            downKey = KeyCode.DownArrow;
            leftKey = KeyCode.LeftArrow;
            rightKey = KeyCode.RightArrow;
            shootKey = KeyCode.RightControl;
        }
        
        // Configure rigidbody for smoother movement
        if (rb != null)
        {
            rb.gravityScale = 0f;
            rb.drag = 0f; // No drag for more responsive movement
            rb.freezeRotation = true;
            rb.interpolation = RigidbodyInterpolation2D.Interpolate; // Smoother visual movement
            rb.collisionDetectionMode = CollisionDetectionMode2D.Continuous;
        }
        else
        {
            Debug.LogError("No Rigidbody2D found on player " + playerID);
            rb = gameObject.AddComponent<Rigidbody2D>();
            rb.gravityScale = 0f;
            rb.freezeRotation = true;
        }
        
        // Setup shooting for Player 2 only - Player 1 handles its own input in PlayerShooting
        if (playerShooting != null && playerID == 2)
        {
            playerShooting.playerID = playerID;
            playerShooting.shootKey = shootKey;
        }
        
        Debug.Log("Player " + playerID + " initialized with controls.");
    }

    private void Update()
    {
        // Handle shooting in Update for more responsive input - only for Player 2
        // Player 1 now handles its own shooting input in PlayerShooting
        if (playerID == 2 && Input.GetKeyDown(shootKey) && playerShooting != null)
        {
            playerShooting.AttemptToShoot();
        }
    }
    
    private void FixedUpdate()
    {
        // Get direct key input for more responsive control
        Vector2 movement = Vector2.zero;
        
        if (Input.GetKey(upKey)) movement.y = 1;
        if (Input.GetKey(downKey)) movement.y = -1;
        if (Input.GetKey(leftKey)) movement.x = -1;
        if (Input.GetKey(rightKey)) movement.x = 1;
        
        // Normalize for consistent diagonal speed
        if (movement.magnitude > 1f)
            movement.Normalize();
            
        // Apply movement directly for more responsive control
        rb.velocity = movement * moveSpeed;
        
        // Update animation based on movement
        UpdateAnimation(movement);
    }

    void UpdateAnimation(Vector2 movement)
    {
        // If not moving, play idle animation
        if (movement.magnitude <= 0.1f)
        {
            PlayAnimation(IDLE);
            return;
        }

        // Determine which direction has the greater component
        float absX = Mathf.Abs(movement.x);
        float absY = Mathf.Abs(movement.y);

        // Move in the direction with the larger component
        if (absX > absY)
        {
            // Horizontal movement
            if (movement.x > 0)
            {
                PlayAnimation(WALK_RIGHT);
            }
            else
            {
                PlayAnimation(WALK_LEFT);
            }
        }
        else
        {
            // Vertical movement
            if (movement.y > 0)
            {
                PlayAnimation(WALK_UP);
            }
            else
            {
                // Use WALK_DOWN if you have that animation, otherwise use IDLE
                if (animator != null && animator.HasState(0, Animator.StringToHash(WALK_DOWN)))
                    PlayAnimation(WALK_DOWN);
                else
                    PlayAnimation(IDLE);
            }
        }
    }

    void PlayAnimation(string animationName)
    {
        // Only change animation if it's different from the current one
        if (animator != null && !animator.GetCurrentAnimatorStateInfo(0).IsName(animationName))
        {
            animator.Play(animationName);
        }
    }
}