using UnityEngine;

public class TreePlacer : MonoBehaviour
{
    public GameObject treePrefab;
    public MapGenerator mapGenerator;
    public float treeFrequency = 0.01f;

    public void PlaceTrees()
    {
        if (mapGenerator == null || treePrefab == null)
            return;

        for (int x = 0; x < mapGenerator.width; x++)
        {
            for (int y = 0; y < mapGenerator.height; y++)
            {
                // Only place trees on grass tiles (value 1)
                if (mapGenerator.GetTileValue(x, y) == 1)
                {
                    // Random chance to place a tree
                    if (Random.value < treeFrequency)
                    {
                        // Create tree at this position
                        Vector3 position = new Vector3(x, y, 0);
                        Instantiate(treePrefab, position, Quaternion.identity, transform);
                    }
                }
            }
        }
    }
}