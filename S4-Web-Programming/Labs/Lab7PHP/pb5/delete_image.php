<?php
session_start();

if (!isset($_SESSION['username'])) {
    header("Location: login.php");
    exit();
}

// Check if image_id is set
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["image_id"])) {
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

    $image_id = $_POST["image_id"];
    $username = $_SESSION['username'];

    $stmt = $conn->prepare("DELETE FROM images WHERE id = ? AND username = ?");
    $stmt->bind_param("is", $image_id, $username);
    $stmt->execute();

    if ($stmt->affected_rows > 0) {
        echo "Image deleted successfully.";
    } else {
        echo "Error deleting image.";
    }

    $conn->close();
}

header("Location: profile.php");
exit();
?>