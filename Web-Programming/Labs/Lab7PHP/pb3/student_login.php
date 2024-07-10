<?php
session_start();

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "trenuri";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Conexiune eșuată: " . $conn->connect_error);
}

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    if (!isset($_SESSION['csrf_token']) || !isset($_POST['csrf_token']) || $_POST['csrf_token'] !== $_SESSION['csrf_token']) {
        die("Invalid CSRF token");
    }

    $student_id = $_POST['student_id'];

    $stmt = $conn->prepare("SELECT * FROM students WHERE id = ?");
    $stmt->bind_param("i", $student_id);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $_SESSION['student_id'] = $student_id;
        header("Location: student_grades.php");
        exit();
    } else {
        echo "<p>ID-ul elevului nu există.</p>";
    }

    $stmt->close();
}

if (empty($_SESSION['csrf_token'])) {
    $_SESSION['csrf_token'] = bin2hex(random_bytes(32));
}
?>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="student_login_styles.css">
    <title>Autentificare Elev</title>
</head>
<body>
<div class="container">
    <h1>Autentificare Elev</h1>

    <?php
    $result = $conn->query("SELECT * FROM students");
    if ($result->num_rows > 0) {
        echo "<h2>Tabel cu elevi:</h2>";
        echo "<table><tr><th>ID Elev</th><th>Nume</th></tr>";
        while ($row = $result->fetch_assoc()) {
            echo "<tr><td>" . htmlspecialchars($row["id"]) . "</td><td>" . htmlspecialchars($row["name"]) . "</td></tr>";
        }
        echo "</table>";
    } else {
        echo "<p>Nu există elevi în baza de date.</p>";
    }
    ?>

    <form action="student_login.php" method="post">
        <input type="hidden" name="csrf_token" value="<?php echo $_SESSION['csrf_token']; ?>">
        <label for="student_id">ID Elev:</label>
        <input type="text" id="student_id" name="student_id" required>
        <input type="submit" value="Autentificare">
    </form>
</div>
</body>
</html>