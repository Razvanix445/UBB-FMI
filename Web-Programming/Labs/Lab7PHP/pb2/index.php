<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="styles.css">
    <title>Produse</title>
</head>
<body>
<div class="container">
    <h1>Produse</h1>
    <form method="GET" action="index.php">
        <label for="num_per_page">Număr produse pe pagină:</label>
        <select id="num_per_page" name="num_per_page">
            <option value="2" <?php echo (isset($_GET['num_per_page']) && $_GET['num_per_page'] == 2) ? 'selected' : ''; ?>>2</option>
            <option value="3" <?php echo (isset($_GET['num_per_page']) && $_GET['num_per_page'] == 3) ? 'selected' : ''; ?>>3</option>
            <option value="5" <?php echo (isset($_GET['num_per_page']) && $_GET['num_per_page'] == 5) ? 'selected' : ''; ?>>5</option>
        </select>
        <input type="submit" value="Afișează">
    </form>

    <?php

    session_start();

    function validate_input($data) {
        return htmlspecialchars(stripslashes(trim($data)));
    }

    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "trenuri";

    $num_per_page = isset($_GET['num_per_page']) ? (int)validate_input($_GET['num_per_page']) : 10;
    $page = isset($_GET['page']) ? (int)validate_input($_GET['page']) : 1;
    $offset = ($page - 1) * $num_per_page;

    $conn = new mysqli($servername, $username, $password, $dbname);

    if ($conn->connect_error) {
        die("Conexiune eșuată: " . $conn->connect_error);
    }

    $stmt = $conn->prepare("SELECT COUNT(*) AS total FROM produsephp");
    $stmt->execute();
    $result = $stmt->get_result();
    $row = $result->fetch_assoc();
    $total_products = $row['total'];
    $total_pages = ceil($total_products / $num_per_page);

    $stmt = $conn->prepare("SELECT * FROM produsephp LIMIT ?, ?");
    $stmt->bind_param("ii", $offset, $num_per_page);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        echo "<table><tr><th>ID</th><th>Nume</th><th>Preț</th><th>Categorie</th></tr>";
        while ($row = $result->fetch_assoc()) {
            echo "<tr><td>" . htmlspecialchars($row["id"]) . "</td><td>" . htmlspecialchars($row["nume"]) . "</td><td>" . htmlspecialchars($row["pret"]) . "</td><td>" . htmlspecialchars($row["categorie"]) . "</td></tr>";
        }
        echo "</table>";
    } else {
        echo "Nu există produse disponibile.";
    }

    $stmt->close();
    $conn->close();
    ?>

    <div class="pagination">
        <form action="index.php" method="GET">
            <input type="hidden" name="page" value="<?php echo max(1, $page - 1); ?>">
            <input type="hidden" name="num_per_page" value="<?php echo $num_per_page; ?>">
            <input type="submit" value="Previous" <?php echo $page <= 1 ? 'disabled' : ''; ?>>
        </form>
        <form action="index.php" method="GET">
            <input type="hidden" name="page" value="<?php echo $page + 1; ?>">
            <input type="hidden" name="num_per_page" value="<?php echo $num_per_page; ?>">
            <input type="submit" value="Next" <?php echo $page >= $total_pages ? 'disabled' : ''; ?>>
        </form>
    </div>
</div>
</body>
</html>