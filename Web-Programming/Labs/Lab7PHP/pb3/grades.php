<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="grades_styles.css">
    <title>Gestionare Note</title>
</head>
<body>
<div class="container">
    <h1>Gestionare Note</h1>

    <?php
    session_start();

    if (empty($_SESSION['csrf_token'])) {
        $_SESSION['csrf_token'] = bin2hex(random_bytes(32));
    }

    if (!isset($_SESSION['teacher_id'])) {
        header("Location: login.php");
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

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        if (!isset($_POST['csrf_token']) || $_POST['csrf_token'] !== $_SESSION['csrf_token']) {
            die("Invalid CSRF token");
        }

        function validate_input($data) {
            return htmlspecialchars(stripslashes(trim($data)));
        }

        $student_id = (int)validate_input($_POST['student_id']);
        $subject = validate_input($_POST['subject']);
        $grade = (int)validate_input($_POST['grade']);
        $teacher_id = $_SESSION['teacher_id'];

        $maxIdQuery = "SELECT MAX(id) AS max_id FROM grades";
        $maxIdResult = $conn->query($maxIdQuery);
        $maxIdRow = $maxIdResult->fetch_assoc();
        $maxId = $maxIdRow['max_id'];

        $newId = $maxId + 1;

        $stmt = $conn->prepare("INSERT INTO grades (id, student_id, subject, grade, teacher_id) VALUES (?, ?, ?, ?, ?)");
        $stmt->bind_param("iisii", $newId, $student_id, $subject, $grade, $teacher_id);
        if ($stmt->execute()) {
            echo "<p>Nota a fost adăugată cu succes.</p>";
        } else {
            echo "<p>Eroare la adăugarea notei.</p>";
        }
        $stmt->close();
    }

    $result = $conn->query("SELECT * FROM students");
    if ($result->num_rows > 0) {
        echo "<table><tr><th>ID Student</th><th>Nume</th></tr>";
        while ($row = $result->fetch_assoc()) {
            echo "<tr><td>" . htmlspecialchars($row["id"]) . "</td><td>" . htmlspecialchars($row["name"]) . "</td></tr>";
        }
        echo "</table>";
    } else {
        echo "<p>Nu există studenți disponibili.</p>";
    }
    ?>

    <form action="grades.php" method="post">
        <input type="hidden" name="csrf_token" value="<?php echo $_SESSION['csrf_token']; ?>">
        <label for="student_id">ID Student:</label>
        <input type="number" id="student_id" name="student_id" required>
        <label for="subject">Materie:</label>
        <input type="text" id="subject" name="subject" required>
        <label for="grade">Nota:</label>
        <input type="number" id="grade" name="grade" required>
        <input type="submit" value="Adaugă Nota">
    </form>
</div>
</body>
</html>