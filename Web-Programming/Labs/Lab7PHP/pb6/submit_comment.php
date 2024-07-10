<?php
session_start();

if (!isset($_POST['csrf_token']) || $_POST['csrf_token'] !== $_SESSION['csrf_token']) {
    die("Invalid CSRF token");
}

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "comments_app";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $article_id = $_POST['article_id'];
    $name = htmlspecialchars(trim($_POST['name']));
    $comment = htmlspecialchars(trim($_POST['comment']));

    if (empty($name) || empty($comment)) {
        die("Name and comment cannot be empty");
    }

    $sql = "INSERT INTO comments (article_id, name, comment, approved) VALUES (?, ?, ?, 0)";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("iss", $article_id, $name, $comment);
    if ($stmt->execute()) {
        echo "Comment submitted for approval.";
    } else {
        echo "Error: " . $stmt->error;
    }

    $stmt->close();
}

$conn->close();
?>
<br>
<a href="article.php">Back to Article</a>