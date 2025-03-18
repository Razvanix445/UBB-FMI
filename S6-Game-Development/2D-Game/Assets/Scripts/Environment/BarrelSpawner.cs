using UnityEngine;

public class BarrelSpawner : MonoBehaviour
{
    [Header("Crate Settings")]
    public GameObject physicsCratePrefab;
    public float crateFrequency = 0.01f; // Chance to spawn a crate on each valid tile
    public int minCrates = 5;            // Minimum number of crates to spawn
    public int maxCrates = 15;           // Maximum number of crates to spawn
    
    [Header("Map References")]
    public MapGenerator mapGenerator;
    
    [Header("Placement Settings")]
    public float minPlayerDistance = 5f; // Minimum distance from players
    public bool avoidOverlapping = true; // Prevent crates from overlapping
    public float minCrateDistance = 2f;  // Minimum distance between crates
    
    // Internal variables
    private Transform cratesParent;
    
    void Start()
    {
        // Create a parent object for organization
        GameObject cratesParentObj = new GameObject("Crates");
        cratesParent = cratesParentObj.transform;
        cratesParent.SetParent(transform);
        
        // Find MapGenerator if not assigned
        if (mapGenerator == null)
        {
            mapGenerator = FindObjectOfType<MapGenerator>();
            if (mapGenerator == null)
            {
                Debug.LogError("No MapGenerator found! CrateSpawner requires a MapGenerator to function.");
                return;
            }
        }
    }
    
    public void PlaceCrates()
    {
        if (mapGenerator == null || physicsCratePrefab == null)
        {
            Debug.LogError("Missing required references for CrateSpawner!");
            return;
        }
        
        Debug.Log("Starting to place crates on the map...");
        
        // Find players for distance calculations
        GameObject[] players = GameObject.FindGameObjectsWithTag("Player");
        
        // Track how many crates we've placed
        int cratesPlaced = 0;
        
        // First pass: try to place crates randomly based on frequency
        for (int x = 0; x < mapGenerator.width; x++)
        {
            for (int y = 0; y < mapGenerator.height; y++)
            {
                // Only place crates on grass tiles (value 1)
                if (mapGenerator.GetTileValue(x, y) == 1)
                {
                    // Random chance to place a crate
                    if (Random.value < crateFrequency)
                    {
                        Vector3 position = new Vector3(x, y, 0);
                        
                        // Skip if too close to players
                        if (IsTooCloseToPlayers(position, players, minPlayerDistance))
                            continue;
                            
                        // Skip if too close to other crates
                        if (avoidOverlapping && IsTooCloseToOtherCrates(position, minCrateDistance))
                            continue;
                        
                        // Create crate at this position
                        GameObject crate = Instantiate(physicsCratePrefab, position, Quaternion.identity, cratesParent);
                        
                        // Add some variation
                        AddVariation(crate);
                        
                        cratesPlaced++;
                    }
                }
            }
        }
        
        // Second pass: ensure minimum number of crates
        if (cratesPlaced < minCrates)
        {
            Debug.Log($"Only placed {cratesPlaced} crates initially, adding more to reach minimum {minCrates}");
            
            int additionalNeeded = minCrates - cratesPlaced;
            int attempts = 0;
            int maxAttempts = 1000; // Prevent infinite loops
            
            while (additionalNeeded > 0 && attempts < maxAttempts)
            {
                attempts++;
                
                // Get a random position on the map
                int x = Random.Range(0, mapGenerator.width);
                int y = Random.Range(0, mapGenerator.height);
                
                // Check if it's a valid grass tile
                if (mapGenerator.GetTileValue(x, y) == 1)
                {
                    Vector3 position = new Vector3(x, y, 0);
                    
                    // Skip if too close to players
                    if (IsTooCloseToPlayers(position, players, minPlayerDistance))
                        continue;
                        
                    // Skip if too close to other crates
                    if (avoidOverlapping && IsTooCloseToOtherCrates(position, minCrateDistance))
                        continue;
                    
                    // Create crate at this position
                    GameObject crate = Instantiate(physicsCratePrefab, position, Quaternion.identity, cratesParent);
                    
                    // Add some variation
                    AddVariation(crate);
                    
                    additionalNeeded--;
                    cratesPlaced++;
                }
            }
        }
        
        // If we have more than the maximum, remove some
        if (cratesPlaced > maxCrates)
        {
            Debug.Log($"Placed {cratesPlaced} crates, removing some to reach maximum {maxCrates}");
            
            // Get all crates
            BarrelObject[] allCrates = FindObjectsOfType<BarrelObject>();
            
            // Shuffle the array
            for (int i = 0; i < allCrates.Length; i++)
            {
                BarrelObject temp = allCrates[i];
                int randomIndex = Random.Range(i, allCrates.Length);
                allCrates[i] = allCrates[randomIndex];
                allCrates[randomIndex] = temp;
            }
            
            // Destroy excess crates
            for (int i = 0; i < cratesPlaced - maxCrates; i++)
            {
                if (i < allCrates.Length)
                {
                    Destroy(allCrates[i].gameObject);
                }
            }
            
            cratesPlaced = maxCrates;
        }
        
        Debug.Log($"Successfully placed {cratesPlaced} crates on the map");
    }
    
    bool IsTooCloseToPlayers(Vector3 position, GameObject[] players, float minDistance)
    {
        foreach (GameObject player in players)
        {
            if (player != null)
            {
                float distance = Vector3.Distance(position, player.transform.position);
                if (distance < minDistance)
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    bool IsTooCloseToOtherCrates(Vector3 position, float minDistance)
    {
        // Find all existing crates
        BarrelObject[] existingCrates = FindObjectsOfType<BarrelObject>();
        
        foreach (BarrelObject crate in existingCrates)
        {
            float distance = Vector3.Distance(position, crate.transform.position);
            if (distance < minDistance)
            {
                return true;
            }
        }
        
        return false;
    }
    
    void AddVariation(GameObject crate)
    {
        // Add some variation to scale
        float scaleVariation = Random.Range(12f, 12f);
        crate.transform.localScale = new Vector3(scaleVariation, scaleVariation, 1f);
        
        // Add some variation to rotation
        crate.transform.rotation = Quaternion.Euler(0, 0, Random.Range(-10f, 10f));
        
        // Add variation to physics properties
        BarrelObject physicsCrate = crate.GetComponent<BarrelObject>();
        if (physicsCrate != null)
        {
            // Vary health slightly
            physicsCrate.health = physicsCrate.health * Random.Range(0.8f, 1.2f);
            
            // Vary mass slightly
            physicsCrate.mass = physicsCrate.mass * Random.Range(0.9f, 1.1f);
            
            // Apply the mass change to the rigidbody
            Rigidbody2D rb = crate.GetComponent<Rigidbody2D>();
            if (rb != null)
            {
                rb.mass = physicsCrate.mass;
            }
        }
    }
}