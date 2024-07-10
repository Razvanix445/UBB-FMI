<?php
session_start();

// Database connection
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "trenuri";

$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Check if username exists in the database
$username = $_POST['username'];
$sql = "SELECT * FROM users WHERE nume = '$username'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $_SESSION['username'] = $username;
    header('Location: profile.php');
    exit();
} else {
    echo "Invalid username.";
    echo '<form action="'. $_SERVER['HTTP_REFERER'] .'" method="get">';
    echo '<button type="submit">Back</button>';
    echo '</form>';
}

$conn->close();
?>