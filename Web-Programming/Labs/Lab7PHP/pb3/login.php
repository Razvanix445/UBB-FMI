<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="login_styles.css">
    <title>Autentificare Profesor</title>
</head>
<body>
<div class="container">
    <h1>Autentificare Profesor</h1>
    <form action="login.php" method="post">
        <input type="hidden" name="csrf_token" value="<?php echo $_SESSION['csrf_token']; ?>">
        <label for="username">Utilizator:</label>
        <input type="text" id="username" name="username" required>
        <label for="password">Parola:</label>
        <input type="password" id="password" name="password" required>
        <input type="submit" value="Autentificare">
    </form>

    <?php
    session_start();

    if (empty($_SESSION['csrf_token'])) {
        $_SESSION['csrf_token'] = bin2hex(random_bytes(32));
    }

    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "trenuri";

    $conn = new mysqli($servername, $username, $password, $dbname);

    if ($conn->connect_error) {
        die("Conexiune eșuată: " . $conn->connect_error);
    }

    function validate_input($data) {
        return htmlspecialchars(stripslashes(trim($data)));
    }

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        $username = validate_input($_POST['username']);
        $password = validate_input($_POST['password']);

        $stmt = $conn->prepare("SELECT id, password FROM teachers WHERE username = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            $stmt->bind_result($id, $stored_password);
            $stmt->fetch();
            if ($password === $stored_password) {
                $_SESSION['teacher_id'] = $id;
                header("Location: grades.php");
                exit();
            } else {
                echo "<p>Parola este incorectă.</p>";
            }
        } else {
            echo "<p>Utilizatorul nu există.</p>";
        }
        $stmt->close();
    }

    echo "<h2>Profesorii existenți în baza de date:</h2>";
    $result = $conn->query("SELECT * FROM teachers");
    if ($result->num_rows > 0) {
        echo "<table><tr><th>ID</th><th>Utilizator</th><th>Parola</th></tr>";
        while ($row = $result->fetch_assoc()) {
            echo "<tr><td>" . htmlspecialchars($row["id"]) . "</td><td>" . htmlspecialchars($row["username"]) . "</td><td>" . htmlspecialchars($row["password"]) . "</td></tr>";
        }
        echo "</table>";
    } else {
        echo "<p>Nu există profesori în baza de date.</p>";
    }
    $conn->close();
    ?>

</div>
</body>
</html>