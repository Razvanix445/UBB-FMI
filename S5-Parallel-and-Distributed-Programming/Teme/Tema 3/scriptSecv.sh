#!/bin/bash

# Number of times to run the program
NUM_RUNS=10
TOTAL_TIME=0

echo "Running the program $NUM_RUNS times..."

for ((i=1; i<=NUM_RUNS; i++))
do
    echo "Run #$i"
    
    # Execute the program and capture the output
    OUTPUT=$(./secv)
    
    # Capture the total execution time
    RUN_TIME=$(echo "$OUTPUT" | sed -n 's/Total execution time: \(.*\) ns/\1/p')
    
    # Capture the result match status
    MATCH_STATUS=$(echo "$OUTPUT" | grep -E "The result matches the correct output|The result does NOT match the correct output")
    
    # Check if RUN_TIME is not empty
    if [ -n "$RUN_TIME" ]; then
        TOTAL_TIME=$((TOTAL_TIME + RUN_TIME))
        echo "Execution time for run #$i: $RUN_TIME nanoseconds"
    else
        echo "Failed to capture execution time for run #$i"
    fi
    
    # Print whether the result matched the correct output
    if [ -n "$MATCH_STATUS" ]; then
        echo "$MATCH_STATUS"
    else
        echo "Failed to verify the result match status for run #$i"
    fi
done

# Calculate the average time
AVERAGE_TIME=$((TOTAL_TIME / NUM_RUNS))

echo "Total execution time for $NUM_RUNS runs: $TOTAL_TIME nanoseconds"
echo "Average execution time: $AVERAGE_TIME nanoseconds"
