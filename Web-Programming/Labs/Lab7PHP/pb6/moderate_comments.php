<?php
session_start();
if (!isset($_SESSION['admin'])) {
    header('Location: admin_login.php');
    exit();
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
    if (!isset($_POST['csrf_token']) || $_POST['csrf_token'] !== $_SESSION['csrf_token']) {
        die("Invalid CSRF token");
    }
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (isset($_POST['approve'])) {
        $comment_id = $_POST['comment_id'];
        $sql = "UPDATE comments SET approved = 1 WHERE id = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $comment_id);
        $stmt->execute();
    } elseif (isset($_POST['delete'])) {
        $comment_id = $_POST['comment_id'];
        $sql = "DELETE FROM comments WHERE id = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $comment_id);
        $stmt->execute();
    }
}

$sql = "SELECT * FROM comments WHERE approved = 0";
$pending_comments = $conn->query($sql);
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Moderate Comments</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container">
    <h2>Moderate Comments</h2>
    <?php while ($row = $pending_comments->fetch_assoc()): ?>
        <div class="comment">
            <p><strong><?php echo htmlspecialchars($row['name']); ?></strong> on <?php echo $row['created_at'];?>:
            <p><?php echo nl2br(htmlspecialchars($row['comment'])); ?></p>
            <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="post">
                <input type="hidden" name="comment_id" value="<?php echo $row['id']; ?>">
                <input type="hidden" name="csrf_token" value="<?php echo $_SESSION['csrf_token']; ?>">
                <input type="submit" name="approve" value="Approve">
                <input type="submit" name="delete" value="Delete">
            </form>
        </div>
    <?php endwhile; ?>
    <br>
    <a href="article.php">Back to Article</a>
</div>
</body>
</html>

<?php $conn->close(); ?>