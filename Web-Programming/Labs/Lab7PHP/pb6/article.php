<?php
session_start();

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "comments_app";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$article_id = 1;

$sql = "SELECT * FROM articles WHERE id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $article_id);
$stmt->execute();
$result = $stmt->get_result();
$article = $result->fetch_assoc();

$sql_comments = "SELECT * FROM comments WHERE article_id = ? AND approved = 1";
$stmt_comments = $conn->prepare($sql_comments);
$stmt_comments->bind_param("i", $article_id);
$stmt_comments->execute();
$comments = $stmt_comments->get_result();

if (empty($_SESSION['csrf_token'])) {
    $_SESSION['csrf_token'] = bin2hex(random_bytes(32));
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?php echo htmlspecialchars($article['title']); ?></title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container">
    <h1><?php echo htmlspecialchars($article['title']); ?></h1>
    <p><?php echo nl2br(htmlspecialchars($article['content'])); ?></p>

    <h2>Comments</h2>
    <?php while ($row = $comments->fetch_assoc()): ?>
        <div class="comment">
            <p><strong><?php echo htmlspecialchars($row['name']); ?></strong> on <?php echo $row['created_at']; ?></p>
            <p><?php echo nl2br(htmlspecialchars($row['comment'])); ?></p>
        </div>
    <?php endwhile; ?>

    <h2>Leave a Comment</h2>
    <form action="submit_comment.php" method="post">
        <input type="hidden" name="article_id" value="<?php echo $article_id; ?>">
        <input type="hidden" name="csrf_token" value="<?php echo $_SESSION['csrf_token']; ?>">
        <label for="name">Name:</label><br>
        <input type="text" id="name" name="name" required><br><br>
        <label for="comment">Comment:</label><br>
        <textarea id="comment" name="comment" required></textarea><br><br>
        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>

<?php $conn->close(); ?>