using UnityEngine;
using System.Collections;

public class MonsterSpawner : MonoBehaviour
{
    [Header("Spawn Settings")]
    public GameObject monsterPrefab;
    public float spawnInterval = 5f;
    public int maxMonstersAlive = 5;
    public float minDistanceFromPlayer = 10f;
    public float maxDistanceFromPlayer = 15f;

    [Header("Difficulty Settings")]
    public bool increaseDifficulty = true;
    public float difficultyIncreaseInterval = 30f;
    public float spawnIntervalReduction = 0.25f;
    public int maxMonstersIncrement = 1;
    public float minSpawnInterval = 1.5f;
    public int absoluteMaxMonsters = 15;

    // References
    private Transform player;
    private int currentMonsterCount = 0;
    private bool isSpawning = false;

    void OnEnable()
    {
        // Start spawning when enabled
        InitializeSpawner();
    }

    public void InitializeSpawner()
    {
        // Stop any existing spawning
        StopAllCoroutines();
        isSpawning = false;
        
        // Find the player
        if (GameObject.FindGameObjectWithTag("Player") != null)
        {
            player = GameObject.FindGameObjectWithTag("Player").transform;
            
            // Start spawning monsters
            StartCoroutine(SpawnMonsters());

            // Start increasing difficulty if enabled
            if (increaseDifficulty)
            {
                StartCoroutine(IncreaseDifficulty());
            }
            
            isSpawning = true;
            Debug.Log($"Monster spawner initialized. Player found: {player.name}");
        }
        else
        {
            Debug.LogWarning("Player not found yet! Will retry in 1 second.");
            Invoke("InitializeSpawner", 1f); // Retry in 1 second
        }
    }

    IEnumerator SpawnMonsters()
    {
        Debug.Log("Starting to spawn monsters");
        while (true)
        {
            // Wait for spawn interval
            yield return new WaitForSeconds(spawnInterval);

            // Only spawn if we haven't reached the max
            if (currentMonsterCount < maxMonstersAlive)
            {
                SpawnMonster();
            }
        }
    }

    void SpawnMonster()
    {
        if (monsterPrefab == null || player == null)
            return;

        // Get a random spawn position
        Vector2 spawnPosition = GetRandomSpawnPosition();

        // Create the monster
        GameObject monster = Instantiate(monsterPrefab, spawnPosition, Quaternion.identity);
        Debug.Log($"Monster spawned at {spawnPosition}");

        // Increment count
        currentMonsterCount++;

        // Set up listener for when monster is destroyed
        StartCoroutine(CheckIfMonsterDestroyed(monster));
    }

    Vector2 GetRandomSpawnPosition()
    {
        // Get a random angle
        float randomAngle = Random.Range(0f, 360f);

        // Get a random distance between min and max
        float randomDistance = Random.Range(minDistanceFromPlayer, maxDistanceFromPlayer);

        // Calculate the position based on angle and distance
        Vector2 offset = new Vector2(
            Mathf.Cos(randomAngle * Mathf.Deg2Rad),
            Mathf.Sin(randomAngle * Mathf.Deg2Rad)
        ) * randomDistance;

        // Add the offset to the player's position
        Vector2 spawnPosition = (Vector2)player.position + offset;

        return spawnPosition;
    }

    IEnumerator CheckIfMonsterDestroyed(GameObject monster)
    {
        // Wait until the monster is null (destroyed)
        while (monster != null)
        {
            yield return new WaitForSeconds(0.5f);
        }

        // Monster was destroyed, update count
        currentMonsterCount--;
    }

    IEnumerator IncreaseDifficulty()
    {
        while (true)
        {
            // Wait for the difficulty increase interval
            yield return new WaitForSeconds(difficultyIncreaseInterval);

            // Reduce spawn interval
            spawnInterval = Mathf.Max(spawnInterval - spawnIntervalReduction, minSpawnInterval);

            // Increase max monsters
            maxMonstersAlive = Mathf.Min(maxMonstersAlive + maxMonstersIncrement, absoluteMaxMonsters);

            Debug.Log($"Difficulty increased! Spawn interval: {spawnInterval}, Max monsters: {maxMonstersAlive}");
        }
    }
    
    void OnDisable()
    {
        // Stop all coroutines when disabled
        StopAllCoroutines();
        isSpawning = false;
    }
}