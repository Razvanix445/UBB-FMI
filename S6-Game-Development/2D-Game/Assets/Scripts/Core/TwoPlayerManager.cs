using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class TwoPlayerManager : MonoBehaviour
{
    // Singleton instance
    public static TwoPlayerManager Instance { get; private set; }
    
    [Header("Player Settings")]
    public GameObject player1Prefab;
    public GameObject player2Prefab;
    public Transform player1SpawnPoint;
    public Transform player2SpawnPoint;
    
    [Header("Game Settings")]
    public int winningScore = 1000;
    public bool gameOver = false;
    
    [Header("UI References")]
    public Text player1ScoreText;
    public Text player2ScoreText;
    public GameObject gameOverPanel;
    public Text winnerText;
    public Button mainMenuButton;
    public Button restartButton;
    
    // Player references and scores
    private GameObject player1;
    private GameObject player2;
    private int player1Score = 0;
    private int player2Score = 0;
    
    void Awake()
    {
        // Singleton pattern
        if (Instance == null)
        {
            Instance = this;
        }
        else
        {
            Destroy(gameObject);
            return;
        }
        
        // Hide game over panel at start
        if (gameOverPanel != null)
            gameOverPanel.SetActive(false);
    }
    
    void Start()
    {
        // Set up button listeners
        if (mainMenuButton != null)
            mainMenuButton.onClick.AddListener(ReturnToMainMenu);
        
        if (restartButton != null)
            restartButton.onClick.AddListener(RestartGame);
        
        // Spawn players
        SpawnPlayers();
        
        // Initialize UI
        UpdateScoreUI();
    }
    
    void SpawnPlayers()
    {
        // Set default spawn positions if none assigned
        if (player1SpawnPoint == null)
            player1SpawnPoint = transform;
            
        if (player2SpawnPoint == null)
            player2SpawnPoint = transform;
        
        // Spawn player 1
        if (player1Prefab != null)
        {
            player1 = Instantiate(player1Prefab, player1SpawnPoint.position, Quaternion.identity);
            PlayerController player1Controller = player1.GetComponent<PlayerController>();
            if (player1Controller != null)
            {
                player1Controller.playerID = 1;
                player1.name = "Player1";
            }
        }
        else
        {
            Debug.LogError("Player 1 prefab is missing!");
        }
        
        // Spawn player 2
        if (player2Prefab != null)
        {
            player2 = Instantiate(player2Prefab, player2SpawnPoint.position, Quaternion.identity);
            PlayerController player2Controller = player2.GetComponent<PlayerController>();
            if (player2Controller != null)
            {
                player2Controller.playerID = 2;
                player2.name = "Player2";
            }
        }
        else
        {
            Debug.LogError("Player 2 prefab is missing!");
        }
    }
    
    public void AddScore(int playerID, int points)
    {
        if (gameOver)
            return;
            
        if (playerID == 1)
        {
            player1Score += points;
            if (player1Score >= winningScore)
            {
                EndGame(1);
            }
        }
        else if (playerID == 2)
        {
            player2Score += points;
            if (player2Score >= winningScore)
            {
                EndGame(2);
            }
        }
        
        UpdateScoreUI();
    }
    
    void UpdateScoreUI()
    {
        if (player1ScoreText != null)
            player1ScoreText.text = "Player 1: " + player1Score;
            
        if (player2ScoreText != null)
            player2ScoreText.text = "Player 2: " + player2Score;
    }
    
    void EndGame(int winnerID)
    {
        gameOver = true;
        
        // Show game over UI
        if (gameOverPanel != null)
            gameOverPanel.SetActive(true);
            
        if (winnerText != null)
            winnerText.text = "Player " + winnerID + " Wins!";
            
        // Disable player controls
        DisablePlayerControls();
    }
    
    void DisablePlayerControls()
    {
        if (player1 != null)
        {
            PlayerController controller1 = player1.GetComponent<PlayerController>();
            if (controller1 != null)
                controller1.enabled = false;
                
            PlayerShooting shooting1 = player1.GetComponent<PlayerShooting>();
            if (shooting1 != null)
                shooting1.enabled = false;
        }
        
        if (player2 != null)
        {
            PlayerController controller2 = player2.GetComponent<PlayerController>();
            if (controller2 != null)
                controller2.enabled = false;
                
            PlayerShooting shooting2 = player2.GetComponent<PlayerShooting>();
            if (shooting2 != null)
                shooting2.enabled = false;
        }
    }
    
    public void RestartGame()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().name);
    }
    
    public void ReturnToMainMenu()
    {
        SceneManager.LoadScene("MainMenu");
    }
}