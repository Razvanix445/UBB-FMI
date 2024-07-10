<?php
session_start();

// Database connection
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

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username = htmlspecialchars(trim($_POST['username']));
    $password = htmlspecialchars(trim($_POST['password']));

    $stmt = $conn->prepare("SELECT id, password FROM users WHERE username = ?");
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $stmt->store_result();

    if ($stmt->num_rows > 0) {
        $stmt->bind_result($id, $stored_password);
        $stmt->fetch();
        if ($password == $stored_password) {
            $_SESSION['admin'] = true;
            header("Location: moderate_comments.php");
            exit();
        } else {
            echo "<p>Invalid password.</p>";
        }
    } else {
        echo "<p>User does not exist.</p>";
    }
    $stmt->close();
}

if (empty($_SESSION['csrf_token'])) {
    $_SESSION['csrf_token'] = bin2hex(random_bytes(32));
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container">
    <h2>Admin Login</h2>
    <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="post">
        <label for="username">Username:</label><br>
        <input type="text" id="username" name="username" required><br><br>
        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password" required><br><br>
        <input type="hidden" name="csrf_token" value="<?php echo $_SESSION['csrf_token']; ?>">
        <input type="submit" value="Login">
    </form>

    <?php
    echo "<h2>Users in the database:</h2>";
    $result = $conn->query("SELECT * FROM users");
    if ($result->num_rows > 0) {
        echo "<table><tr><th>ID</th><th>Username</th><th>Password</th></tr>";
        while ($row = $result->fetch_assoc()) {
            echo "<tr><td>" . $row["id"] . "</td><td>" . $row["username"] . "</td><td>" . $row["password"] . "</td></tr>";
        }
        echo "</table>";
    } else {
        echo "<p>No users in the database.</p>";
    }
    $conn->close();
    ?>
</div>
</body>
</html>
