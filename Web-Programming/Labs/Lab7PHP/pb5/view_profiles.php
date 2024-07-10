<?php
session_start();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Profiles</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container">
    <h2>View Profiles</h2>
    <form action="" method="post">
        <label for="username">Select a User:</label>
        <select name="username" id="username">
            <?php
            $servername = "localhost";
            $username = "root";
            $password = "";
            $dbname = "trenuri";

            $conn = new mysqli($servername, $username, $password, $dbname);

            if ($conn->connect_error) {
                die("Connection failed: " . $conn->connect_error);
            }

            $stmt = $conn->prepare("SELECT DISTINCT username FROM images");
            $stmt->execute();
            $result = $stmt->get_result();

            if ($result->num_rows > 0) {
                while ($row = $result->fetch_assoc()) {
                    echo "<option value='" . htmlspecialchars($row['username']) . "'>" . htmlspecialchars($row['username']) . "</option>";
                }
            } else {
                echo "<option value=''>No users found</option>";
            }

            $conn->close();
            ?>
        </select>
        <input type="submit" value="View Profile">
    </form>
    <hr>
    <div class="profile-section">
        <h3>User Profile</h3>
        <?php
        if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["username"])) {
            $conn = new mysqli($servername, $username, $password, $dbname);

            if ($conn->connect_error) {
                die("Connection failed: " . $conn->connect_error);
            }

            $stmt = $conn->prepare("SELECT image FROM images WHERE username = ?");
            $stmt->bind_param("s", $selected_username);
            $selected_username = $_POST["username"];
            $stmt->execute();
            $result = $stmt->get_result();

            if ($result->num_rows > 0) {
                while ($row = $result->fetch_assoc()) {
                    echo "<img src='data:image/jpeg;base64," . base64_encode($row['image']) . "' alt='User Image'>";
                }
            } else {
                echo "No images uploaded by this user.";
            }

            $conn->close();
        }
        ?>
    </div>
</div>
</body>
</html>