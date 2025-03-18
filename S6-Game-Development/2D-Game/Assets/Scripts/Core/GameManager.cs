using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{
    // Singleton instance
    public static GameManager Instance { get; private set; }

    [Header("Game State")]
    public bool gameIsPaused = false;
    public bool gameIsOver = false;

    [Header("Prefab References")]
    public GameObject playerPrefab;

    // Player reference
    private GameObject player;

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
            return;
        }
    }

    void Start()
    {
        InitializeGame();
    }

    public void InitializeGame()
    {
        // Find player in the scene
        player = GameObject.FindGameObjectWithTag("Player");
        
        // If no player exists and we have a prefab, instantiate it
        if (player == null && playerPrefab != null)
        {
            SpawnPlayer();
        }
        
        // Reset game state
        gameIsPaused = false;
        gameIsOver = false;
        
        // Initialize other managers by finding them in the scene
        FindAndInitializeManagers();
        
        Debug.Log("Game initialized successfully!");
    }
    
    void FindAndInitializeManagers()
    {
        // Initialize ScoreManager if it exists
        /*ScoreManager scoreManager = FindObjectOfType<ScoreManager>();
        if (scoreManager != null)
        {
            scoreManager.ResetScore();
        }*/
        
        // Initialize monster spawner if it exists
        MonsterSpawner spawner = FindObjectOfType<MonsterSpawner>();
        if (spawner != null)
        {
            spawner.InitializeSpawner();
        }
    }

    void SpawnPlayer()
    {
        // Spawn player at the center of the scene
        player = Instantiate(playerPrefab, Vector3.zero, Quaternion.identity);
        
        // Find and update camera follow
        CameraFollow cameraFollow = FindObjectOfType<CameraFollow>();
        if (cameraFollow != null)
        {
            cameraFollow.target = player.transform;
        }
    }

    public void PauseGame()
    {
        gameIsPaused = true;
        Time.timeScale = 0f;
        // Additional pause logic here (UI, etc.)
    }

    public void ResumeGame()
    {
        gameIsPaused = false;
        Time.timeScale = 1f;
        // Additional resume logic here
    }

    public void GameOver()
    {
        gameIsOver = true;
        
        // Stop monsters from spawning
        MonsterSpawner spawner = FindObjectOfType<MonsterSpawner>();
        if (spawner != null)
        {
            spawner.enabled = false;
        }
        
        // Finalize score
        ScoreManager scoreManager = FindObjectOfType<ScoreManager>();
        if (scoreManager != null)
        {
            scoreManager.FinalizeScore();
        }
        
        // Game over logic here (UI, etc.)
        Debug.Log("Game Over!");
    }

    public void RestartGame()
    {
        // Reset time scale in case game was paused
        Time.timeScale = 1f;
        
        // Reload the current scene
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }

    public void QuitGame()
    {
        Debug.Log("Quitting game...");
        #if UNITY_EDITOR
            UnityEditor.EditorApplication.isPlaying = false;
        #else
            Application.Quit();
        #endif
    }
}