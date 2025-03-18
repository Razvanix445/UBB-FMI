using UnityEngine;

public class SpriteSorter : MonoBehaviour
{
    private SpriteRenderer spriteRenderer;

    [Tooltip("Base sorting order. Higher appears in front.")]
    public int baseSortingOrder = 0;

    [Tooltip("Child transform used as the reference point for sorting")]
    public Transform sortingReferencePoint;

    [Tooltip("Multiplier for Y-position sorting")]
    public float yPositionMultiplier = 100f;

    [Tooltip("Debug mode to visualize sorting position")]
    public bool debugMode = false;

    void Start()
    {
        spriteRenderer = GetComponent<SpriteRenderer>();
        if (spriteRenderer == null)
        {
            Debug.LogError("No SpriteRenderer found on " + gameObject.name);
            enabled = false;
            return;
        }

        // Create a sorting reference point if none exists
        if (sortingReferencePoint == null)
        {
            GameObject refPoint = new GameObject(gameObject.name + "_SortPoint");
            sortingReferencePoint = refPoint.transform;
            sortingReferencePoint.parent = transform;

            // For characters, default to bottom center
            // For trees/objects, default to bottom center of collider if exists
            Collider2D collider = GetComponent<Collider2D>();
            if (collider != null)
            {
                // Position at the bottom of the collider
                sortingReferencePoint.localPosition = new Vector3(
                    0,
                    collider.offset.y - collider.bounds.extents.y,
                    0);
            }
            else
            {
                // Position at the bottom of the sprite
                sortingReferencePoint.localPosition = new Vector3(
                    0,
                    -spriteRenderer.bounds.extents.y,
                    0);
            }
        }
    }

    void LateUpdate()
    {
        if (spriteRenderer != null && sortingReferencePoint != null)
        {
            // Calculate order based on Y position of the reference point
            int newOrder = baseSortingOrder - Mathf.RoundToInt(sortingReferencePoint.position.y * yPositionMultiplier);

            // Apply the new sorting order
            spriteRenderer.sortingOrder = newOrder;

            // Debug visualization
            if (debugMode)
            {
                Debug.DrawLine(
                    sortingReferencePoint.position,
                    sortingReferencePoint.position + Vector3.up * 0.3f,
                    Color.red);

                Debug.Log($"{gameObject.name} at Y:{sortingReferencePoint.position.y} has order:{newOrder}");
            }
        }
    }

    void OnDrawGizmosSelected()
    {
        if (sortingReferencePoint != null)
        {
            Gizmos.color = Color.red;
            Gizmos.DrawSphere(sortingReferencePoint.position, 0.1f);
        }
    }
}