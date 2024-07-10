<!DOCTYPE html>
<html lang="ro">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="script_styles.css">
    <title>Căutare Trenuri</title>
</head>
<body>
    <h1>Curse Trenuri</h1>

    <?php
    session_start();

    function validate_input($data) {
        return htmlspecialchars(stripslashes(trim($data)), ENT_QUOTES, 'UTF-8');
    }

    function check_csrf_token($token) {
        if (empty($token) || $token !== $_SESSION['csrf_token']) {
            die("Invalid CSRF token.");
        }
    }

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        check_csrf_token($_POST['csrf_token']);

        $servername = "localhost";
        $username = "root";
        $password = "";
        $dbname = "trenuri";

        $plecare = validate_input($_POST['plecare']);
        $sosire = validate_input($_POST['sosire']);
        $direct = isset($_POST['direct']) ? true : false;

        $conn = new mysqli($servername, $username, $password, $dbname);

        if ($conn->connect_error) {
            die("Conexiune esuata: " . $conn->connect_error);
        }

        if ($direct) {
            $stmt = $conn->prepare("SELECT * FROM trenuriphp WHERE localitate_plecare = ? AND localitate_sosire = ?");
            $stmt->bind_param("ss", $plecare, $sosire);
            $stmt->execute();
            $result = $stmt->get_result();

            echo "<table><tr><th>Nr. Tren</th><th>Tip Tren</th><th>Plecare</th><th>Sosire</th><th>Ora Plecare</th><th>Ora Sosire</th></tr>";
            while ($row = $result->fetch_assoc()) {
                echo "<tr><td>" . htmlspecialchars($row["nr_tren"], ENT_QUOTES, 'UTF-8') . "</td><td>" . htmlspecialchars($row["tip_tren"], ENT_QUOTES, 'UTF-8') . "</td><td>" . htmlspecialchars($row["localitate_plecare"], ENT_QUOTES, 'UTF-8') . "</td><td>" . htmlspecialchars($row["localitate_sosire"], ENT_QUOTES, 'UTF-8') . "</td><td>" . htmlspecialchars($row["ora_plecare"], ENT_QUOTES, 'UTF-8') . "</td><td>" . htmlspecialchars($row["ora_sosire"], ENT_QUOTES, 'UTF-8') . "</td></tr>";
            }
            echo "</table>";

            $stmt->close();
        } else {
            $trenuri = [];
            $stmt = $conn->prepare("SELECT * FROM trenuriphp");
            $stmt->execute();
            $result = $stmt->get_result();
            while ($row = $result->fetch_assoc()) {
                $trenuri[] = $row;
            }
            $stmt->close();

            function find_routes($trenuri, $plecare, $sosire)
            {
                $routes = [];
                foreach ($trenuri as $tren) {
                    if ($tren['localitate_plecare'] == $plecare) {
                        if ($tren['localitate_sosire'] == $sosire) {
                            $routes[] = [$tren];
                        } else {
                            $subroutes = find_routes($trenuri, $tren['localitate_sosire'], $sosire);
                            foreach ($subroutes as $subroute) {
                                array_unshift($subroute, $tren);
                                $routes[] = $subroute;
                            }
                        }
                    }
                }
                return $routes;
            }

            $routes = find_routes($trenuri, $plecare, $sosire);

            foreach ($routes as $route) {
                echo "<table><tr><th>Nr. Tren</th><th>Tip Tren</th><th>Plecare</th><th>Sosire</th><th>Ora Plecare</th><th>Ora Sosire</th></tr>";
                foreach ($route as $tren) {
                    echo "<tr><td>" . htmlspecialchars($tren["nr_tren"], ENT_QUOTES, 'UTF-8') . "</td><td>" . htmlspecialchars($tren["tip_tren"], ENT_QUOTES, 'UTF-8') . "</td><td>" . htmlspecialchars($tren["localitate_plecare"], ENT_QUOTES, 'UTF-8') . "</td><td>" . htmlspecialchars($tren["localitate_sosire"], ENT_QUOTES, 'UTF-8') . "</td><td>" . htmlspecialchars($tren["ora_plecare"], ENT_QUOTES, 'UTF-8') . "</td><td>" . htmlspecialchars($tren["ora_sosire"], ENT_QUOTES, 'UTF-8') . "</td></tr>";
                }
                echo "</table><br>";
            }
        }

        $conn->close();
    }
    ?>

</body>
</html>