# PowerShell script to run the C++ executable multiple times and calculate the average execution time

$param1 = $args[0]  # Exe file name (e.g., Tema1PPD.exe)
$param2 = $args[1]  # Number of threads
$param3 = $args[2]  # Number of runs

# Initialize total sum of execution times
$suma = 0

for ($i = 0; $i -lt $param3; $i++) {
    Write-Host "Run" ($i + 1)

    # Run the executable and capture the output
    $output = cmd /c .\$param1 $param2 2>&1

    # Output the raw command result for debugging purposes
    Write-Host "`nRaw Output:`n" $output

    # Check if the output is not empty
    if ($output -ne $null -and $output.Length -gt 0) {
        # Filter only lines that contain "Execution Time:" and capture the time in ns
        $executionTimeLine = $output | Select-String -Pattern "Execution Time: (\d+)" | ForEach-Object { $_.Matches[0].Groups[1].Value }

        if ($executionTimeLine -ne $null -and $executionTimeLine.Length -gt 0) {
            # Convert the extracted execution time to an integer
            $executionTime = [int]$executionTimeLine
            Write-Host "Execution Time: $executionTime ns"

            # Add to the total sum of execution times
            $suma += $executionTime
        }
        else {
            Write-Host "Error: Execution time not found in output."
        }
    }
    else {
        Write-Host "Error: No output from the executable."
    }

    Write-Host ""
}

# Calculate the average execution time
if ($param3 -gt 0) {
    $media = $suma / $param3
    Write-Host "Average execution time: $media ns"
}
else {
    Write-Host "Error: No runs were executed."
}

# CSV file creation or update
$csvFile = "outC.csv"

# Check if the CSV file exists, if not, create it
if (!(Test-Path $csvFile)) {
    New-Item $csvFile -ItemType File
    # Write header row
    Set-Content $csvFile 'Matrix Type,Allocation Type,No of Threads,Execution Time'
}

# Append execution data to the CSV file
Add-Content $csvFile ",,$param2,$media"
