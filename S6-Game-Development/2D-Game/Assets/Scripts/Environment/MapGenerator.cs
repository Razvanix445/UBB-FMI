using UnityEngine;
using UnityEngine.Tilemaps;

public class MapGenerator : MonoBehaviour
{
    private Camera mainCamera;
    public Tilemap tilemap;
    public TileBase GreenTile, Path, PathAllGreen, PathRightGreen, PathRightBottomGreen, PathRightBottomLeftGreen, PathLeftRightGreen;
    public int width = 100;
    public int height = 100;
    public TreePlacer treePlacer;
    private int[,] mapMatrix;

    void Start()
    {
        mainCamera = Camera.main;
        GenerateMatrix();
        GenerateMap();

        if (treePlacer != null)
        {
            treePlacer.PlaceTrees();
        }

        BarrelSpawner barrelSpawner = GetComponent<BarrelSpawner>();
        if (barrelSpawner != null)
        {
            barrelSpawner.PlaceCrates();
        }
        
        CenterCamera();
    }

    void GenerateMatrix()
    {
        mapMatrix = new int[width, height];

        // Fill the map with grass (1)
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                mapMatrix[x, y] = 1; // 1 represents grass
            }
        }

        // Start at a random y-position on the left side
        int startY = Random.Range(1, height - 1);
        CreatePath(startY);
    }

    void CreatePath(int startY)
    {
        int x = 0;
        int y = startY;

        while (x < width - 1)
        {
            mapMatrix[x, y] = 0; // Mark this tile as path

            // Randomly decide next move: right, up, or down
            int direction = Random.Range(0, 3);

            if (direction == 0 && y > 1)
            {
                // Move up and fill the previous step
                y--;
                mapMatrix[x, y] = 0;
            }
            else if (direction == 1 && y < height - 2)
            {
                // Move down and fill the previous step
                y++;
                mapMatrix[x, y] = 0;
            }

            x++; // Always move right
            mapMatrix[x, y] = 0; // Ensure the new step is also path
        }

        // Ensure the rightmost column has at least one path tile
        mapMatrix[width - 1, y] = 0;
    }


    public void GenerateMap()
    {
        Debug.Log("Generating map...");

        if (mapMatrix == null)
        {
            Debug.LogError("Map matrix is not initialized!");
            return;
        }

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                TileBase selectedTile = null;
                Quaternion rotation = Quaternion.identity;

                // Get the neighboring cell values
                bool right = x < width - 1 && mapMatrix[x + 1, y] == 1;
                bool left = x > 0 && mapMatrix[x - 1, y] == 1;
                bool top = y < height - 1 && mapMatrix[x, y + 1] == 1;
                bool bottom = y > 0 && mapMatrix[x, y - 1] == 1;

                int greenNeighbors = CountGreenNeighbors(right, left, top, bottom);

                if (mapMatrix[x, y] == 1)
                {
                    // Rule: GreenTile (Grass) -> Used when the matrix value is 1 and all neighbors are also 1
                    // Note: In practice, you might want to use different grass tiles for edges
                    selectedTile = GreenTile;
                }
                else // mapMatrix[x, y] == 0
                {
                    if (greenNeighbors == 0)
                    {
                        // Rule: Path (Basic Path) -> Used when the matrix value is 0 and all four neighbors are also 0
                        selectedTile = Path;
                    }
                    else if (greenNeighbors == 4)
                    {
                        // Rule: PathAllGreen -> Used when the matrix value is 0 and all four neighbors are 1
                        selectedTile = PathAllGreen;
                    }
                    else if (greenNeighbors == 1)
                    {
                        // Rule: PathRightGreen -> Used when the matrix value is 0 and exactly one neighbor is 1
                        selectedTile = PathRightGreen;

                        // Apply rotation based on which neighbor is green
                        if (right)
                        {
                            // Default orientation (0 degrees) - right neighbor is green
                            rotation = Quaternion.Euler(0, 0, 0);
                        }
                        else if (top)
                        {
                            // Rotate 90 degrees counterclockwise - top neighbor is green
                            rotation = Quaternion.Euler(0, 0, 90);
                        }
                        else if (left)
                        {
                            // Rotate 180 degrees - left neighbor is green
                            rotation = Quaternion.Euler(0, 0, 180);
                        }
                        else if (bottom)
                        {
                            // Rotate 270 degrees counterclockwise - bottom neighbor is green
                            rotation = Quaternion.Euler(0, 0, 270);
                        }
                    }
                    else if (greenNeighbors == 2)
                    {
                        // For two green neighbors, we need to handle different cases

                        // Opposite neighbors (horizontal or vertical)
                        if (left && right)
                        {
                            // Both left and right are green
                            selectedTile = PathLeftRightGreen;
                            rotation = Quaternion.Euler(0, 0, 0); // Horizontal orientation
                        }
                        else if (top && bottom)
                        {
                            // Both top and bottom are green
                            selectedTile = PathLeftRightGreen;
                            rotation = Quaternion.Euler(0, 0, 90); // Vertical orientation
                        }
                        // Adjacent neighbors (corner configurations)
                        else if (right && top)
                        {
                            selectedTile = PathRightBottomGreen;
                            rotation = Quaternion.Euler(0, 0, 90);
                        }
                        else if (top && left)
                        {
                            selectedTile = PathRightBottomGreen;
                            rotation = Quaternion.Euler(0, 0, 180);
                        }
                        else if (left && bottom)
                        {
                            selectedTile = PathRightBottomGreen;
                            rotation = Quaternion.Euler(0, 0, 270);
                        }
                        else if (bottom && right)
                        {
                            selectedTile = PathRightBottomGreen;
                            rotation = Quaternion.Euler(0, 0, 0);
                        }
                    }
                    else if (greenNeighbors == 3)
                    {
                        // For three green neighbors, we need to find the one that is NOT green
                        selectedTile = PathRightBottomLeftGreen;

                        if (!right)
                        {
                            rotation = Quaternion.Euler(0, 0, 0);
                        }
                        else if (!top)
                        {
                            rotation = Quaternion.Euler(0, 0, 90);
                        }
                        else if (!left)
                        {
                            rotation = Quaternion.Euler(0, 0, 180);
                        }
                        else if (!bottom)
                        {
                            rotation = Quaternion.Euler(0, 0, 270);
                        }
                    }
                }

                // Apply the selected tile with rotation
                if (selectedTile != null)
                {
                    tilemap.SetTile(new Vector3Int(x, y, 0), selectedTile);
                    Matrix4x4 transformMatrix = Matrix4x4.Rotate(rotation);
                    tilemap.SetTransformMatrix(new Vector3Int(x, y, 0), transformMatrix);
                }
                else
                {
                    Debug.LogWarning($"No tile selected for position ({x}, {y}) with value {mapMatrix[x, y]}");
                }
            }
        }

        Debug.Log("Map generation complete!");
    }

    // Helper method to count green (value 1) neighbors
    private int CountGreenNeighbors(bool right, bool left, bool top, bool bottom)
    {
        int count = 0;
        if (right) count++;
        if (left) count++;
        if (top) count++;
        if (bottom) count++;
        return count;
    }

    void CenterCamera()
    {
        if (mainCamera != null)
        {
            // Calculate the center of the tilemap
            float centerX = width / 2f;
            float centerY = height / 2f;

            // Move the camera to the center
            mainCamera.transform.position = new Vector3(centerX, centerY, -100);
        }
    }

    public int GetTileValue(int x, int y)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
            return mapMatrix[x, y];
        return -1;
    }
}
