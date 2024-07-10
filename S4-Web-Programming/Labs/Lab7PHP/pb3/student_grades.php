<?php
session_start();

if (!isset($_SESSION['student_id'])) {
    header("Location: student_login.php");
    exit();
}

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "trenuri";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Conexiune eșuată: " . $conn->connect_error);
}

$student_id = $_SESSION['student_id'];

// Obține notele elevului
$stmt = $conn->prepare("SELECT subject, grade FROM grades WHERE student_id = ?");
$stmt->bind_param("i", $student_id);
$stmt->execute();
$result = $stmt->get_result();
?>

<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="student_grades_styles.css">
    <title>Note Elev</title>
</head>
<body>
<div class="container">
    <h1>Note Elev</h1>

    <?php
    echo "<h1>Notele Tale:</h1>";
    if ($result->num_rows > 0) {
        echo "<table><tr><th>Materie</th><th>Nota</th></tr>";
        while ($row = $result->fetch_assoc()) {
            echo "<tr><td>" . htmlspecialchars($row["subject"]) . "</td><td>" . htmlspecialchars($row["grade"]) . "</td></tr>";
        }
        echo "</table>";
    } else {
        echo "<p>Nu există note disponibile pentru tine.</p>";
    }

    $stmt->close();
    $conn->close();
    ?>

</div>
</body>
</html>
