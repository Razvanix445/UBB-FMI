<?php
session_start();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if (!isset($_SESSION['csrf_token']) || !isset($_POST['csrf_token']) || $_POST['csrf_token'] !== $_SESSION['csrf_token']) {
        die("Invalid CSRF token");
    }

    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "trenuri";

    $conn = new mysqli($servername, $username, $password, $dbname);

    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $username = $conn->real_escape_string($_POST['username']);

    $stmt = $conn->prepare("SELECT * FROM users WHERE nume = ?");
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $result = $stmt->get_result();

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

    $stmt->close();
    $conn->close();
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
    <title>Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container">
    <h2>Login</h2>
    <form action="login_handler.php" method="post">
        <input type="hidden" name="csrf_token" value="<?php echo $_SESSION['csrf_token']; ?>">
        <label for="username">Username:</label><br>
        <input type="text" id="username" name="username" required><br><br>
        <input type="submit" value="Login">
    </form>
</div>
</body>
</html>