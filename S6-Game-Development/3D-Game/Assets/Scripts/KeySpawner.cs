using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class KeySpawner : MonoBehaviour
{
    public int keyID = 0;                    // The ID of this key
    public GameObject keyPrefab;             // The key prefab to spawn
    public Transform[] spawnPoints;          // Array of possible spawn points
    
    private GameObject spawnedKey;
    
    void Start()
    {
        // Make sure we have valid spawn points
        if (spawnPoints == null || spawnPoints.Length == 0)
        {
            Debug.LogError("No spawn points assigned to KeySpawner!");
            return;
        }
        
        // Choose a random spawn point
        int randomIndex = Random.Range(0, spawnPoints.Length);
        Transform chosenSpawnPoint = spawnPoints[randomIndex];
        
        // Spawn the key
        SpawnKey(chosenSpawnPoint);
    }
    
    void SpawnKey(Transform spawnPoint)
    {
        // Make sure key prefab is assigned
        if (keyPrefab == null)
        {
            Debug.LogError("Key prefab not assigned to KeySpawner!");
            return;
        }
        
        // Instantiate the key at the chosen spawn point
        spawnedKey = Instantiate(keyPrefab, spawnPoint.position, spawnPoint.rotation);
        
        // Set the key ID
        KeyController keyController = spawnedKey.GetComponent<KeyController>();
        if (keyController != null)
        {
            keyController.keyID = keyID;
        }
        else
        {
            Debug.LogWarning("Key prefab does not have a KeyController component!");
        }
    }
}