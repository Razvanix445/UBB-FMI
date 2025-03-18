using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class MainMenuManager : MonoBehaviour
{
    [Header("UI References")]
    public Button singlePlayerButton;
    public Button twoPlayerButton;
    public Button quitButton;
    
    [Header("Scene Names")]
    public string singlePlayerSceneName = "SinglePlayerScene";
    public string twoPlayerSceneName = "TwoPlayerScene";
    
    void Start()
    {
        // Set up button listeners
        if (singlePlayerButton != null)
            singlePlayerButton.onClick.AddListener(StartSinglePlayer);
        
        if (twoPlayerButton != null)
            twoPlayerButton.onClick.AddListener(StartTwoPlayer);
        
        if (quitButton != null)
            quitButton.onClick.AddListener(QuitGame);
    }
    
    public void StartSinglePlayer()
    {
        Debug.Log("Loading Single Player scene (index 1)");
        SceneManager.LoadScene(singlePlayerSceneName);
    }
    
    public void StartTwoPlayer()
    {
        Debug.Log("Loading Two Player scene (index 2)");
        SceneManager.LoadScene(twoPlayerSceneName);
    }
    
    public void QuitGame()
    {
        Debug.Log("Quitting game");
#if UNITY_EDITOR
        UnityEditor.EditorApplication.isPlaying = false;
#else
            Application.Quit();
#endif
    }
}