using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ArrowController : MonoBehaviour
{
    public float lifeTime = 10f;
    public float damageAmount = 1f;
    private Rigidbody rb;
    
    void Start()
    {
        rb = GetComponent<Rigidbody>();
        
        // Destroy arrow after lifetime has passed
        Destroy(gameObject, lifeTime);
    }
    
    void Update()
    {
        // Align arrow with its velocity direction
        if (rb.velocity.magnitude > 0.1f)
        {
            transform.rotation = Quaternion.LookRotation(rb.velocity) * Quaternion.Euler(-90, 0, 0);
        }
    }
    
    void OnCollisionEnter(Collision collision)
    {
        Debug.Log("Arrow hit: " + collision.gameObject.name);
        
        // Check if we hit a monster
        MonsterController monster = collision.gameObject.GetComponent<MonsterController>();
        if (monster != null)
        {
            monster.TakeDamage();
            Debug.Log("Hit monster!");
        }
        
        // Check if we hit a target
        Target target = collision.gameObject.GetComponent<Target>();
        if (target != null)
        {
            target.Hit();
            Debug.Log("Hit target!");
        }
        
        // Stop arrow physics and make it a child of the hit object
        rb.isKinematic = true;
        transform.SetParent(collision.transform);
    }
}