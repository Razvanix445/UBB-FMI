#!/bin/bash

# Check if the user provided the number of processes
if [ -z "$1" ]; then
    echo "Usage: $0 <number_of_processes>"
    exit 1
fi

NUM_PROCESSES=$1
NUM_RUNS=10
TOTAL_TIME=0

echo "Running the program $NUM_RUNS times with $NUM_PROCESSES processes..."

# Run the program 10 times and accumulate the total time
for ((i=1; i<=NUM_RUNS; i++))
do
    echo "Run #$i"
    
    # Run the program, capture the entire output, and display it using tee
    OUTPUT=$(mpirun -np $NUM_PROCESSES ./v2 | tee /dev/tty)
    
    # Extract the total execution time
    RUN_TIME=$(echo "$OUTPUT" | sed -n 's/Total execution time: \(.*\) ns/\1/p')
    
    # Check if RUN_TIME is not empty
    if [ -n "$RUN_TIME" ]; then
        TOTAL_TIME=$((TOTAL_TIME + RUN_TIME))
        echo "Execution time for run #$i: $RUN_TIME nanoseconds"
    else
        echo "Failed to capture execution time for run #$i"
    fi
    
    # Check if the result matches the expected output
    MATCH_STATUS=$(echo "$OUTPUT" | grep -E "The result matches the correct output|The result does NOT match the correct output")
    
    # Print the match status
    if [ -n "$MATCH_STATUS" ]; then
        echo "$MATCH_STATUS"
    else
        echo "Failed to verify the result match status for run #$i"
    fi
done

# Calculate the average time
AVERAGE_TIME=$((TOTAL_TIME / NUM_RUNS))

echo "Average execution time: $AVERAGE_TIME nanoseconds"
