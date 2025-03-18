using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class ScoreManager : MonoBehaviour
{
    [Header("Score Settings")]
    public int pointsPerMonster = 100;
    public int currentScore = 0;
    public int highScore = 0;

    [Header("UI References")]
    public Text scoreText;
    public Text highScoreText;

    [Header("Score Animation")]
    public bool animateScoreChanges = true;
    public float animationDuration = 0.5f;
    private int displayedScore = 0;

    // Singleton instance
    public static ScoreManager Instance { get; private set; }

    void Awake()
    {
        // Singleton pattern
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

        // Load high score
        highScore = PlayerPrefs.GetInt("HighScore", 0);
    }

    void Start()
    {
        // Initialize UI
        UpdateScoreUI();
    }

    public void AddScore(int points)
    {
        // Add points to current score
        currentScore += points;

        // Check for new high score
        if (currentScore > highScore)
        {
            highScore = currentScore;
            PlayerPrefs.SetInt("HighScore", highScore);
            PlayerPrefs.Save();
        }

        // Update UI
        if (animateScoreChanges)
        {
            StartCoroutine(AnimateScore());
        }
        else
        {
            displayedScore = currentScore;
            UpdateScoreUI();
        }
    }

    IEnumerator AnimateScore()
    {
        // Store initial displayed score
        int startScore = displayedScore;
        int targetScore = currentScore;
        float elapsedTime = 0f;

        while (elapsedTime < animationDuration)
        {
            elapsedTime += Time.deltaTime;
            float t = elapsedTime / animationDuration;

            // Ease-out function for smooth animation
            t = 1f - Mathf.Pow(1f - t, 2f);

            // Calculate current displayed score
            displayedScore = Mathf.RoundToInt(Mathf.Lerp(startScore, targetScore, t));

            // Update UI
            UpdateScoreUI();

            yield return null;
        }

        // Ensure the final score is exactly right
        displayedScore = targetScore;
        UpdateScoreUI();
    }

    void UpdateScoreUI()
    {
        // Update score text
        if (scoreText != null)
        {
            scoreText.text = "Score: " + displayedScore.ToString("N0");
        }

        // Update high score text
        if (highScoreText != null)
        {
            highScoreText.text = "High Score: " + highScore.ToString("N0");
        }
    }

    public void ResetScore()
    {
        currentScore = 0;
        displayedScore = 0;
        UpdateScoreUI();
    }

    // Call this method when the player dies or level ends
    public void FinalizeScore()
    {
        Debug.Log("Final Score: " + currentScore);

        // Here you could add additional functionality like
        // saving scores to a leaderboard, etc.
    }
}