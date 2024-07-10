<?php
// Set the content type to JSON
//header('Content-Type: application/json');

// Connect to the database
$con = mysqli_connect("localhost", "root", "", "trenuri");
if (!$con) {
    die('Could not connect: ' . mysqli_error($con));
}

// Fetch all users
$result = mysqli_query($con, "SELECT * FROM users");

$users = array();
while($row = mysqli_fetch_assoc($result)) {
    $users[] = $row;
}

// Close the connection
mysqli_close($con);

// Return the users as a JSON array
echo json_encode($users);
?>