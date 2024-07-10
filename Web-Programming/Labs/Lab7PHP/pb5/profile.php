<?php
session_start();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        .container {
            text-align: center;
        }

        .image-container {
            display: inline-block;
            width: 200px;
            height: 200px;
            border-radius: 10px;
            overflow: hidden;
            margin: 10px;
            position: relative;
        }

        .image-container img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .delete-btn {
            position: absolute;
            top: 5px;
            right: 5px;
            background: red;
            color: white;
            border: none;
            padding: 5px;
            cursor: pointer;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Welcome <?php echo htmlspecialchars($_SESSION['username']); ?></h2>
    <h3>Profile</h3>
    <?php
    $servername = "localhost";
    $username = "root";
    $password = "";
    $dbname = "trenuri";

    $conn = new mysqli($servername, $username, $password, $dbname);

    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    }

    $stmt = $conn->prepare("SELECT id, image FROM images WHERE username = ?");
    $stmt->bind_param("s", $username);
    $username = $_SESSION['username'];
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            echo "<div class='image-container'>";
            echo "<img src='data:image/jpeg;base64," . base64_encode($row['image']) . "' alt='User Image'>";
            echo "<form action='delete_image.php' method='post' class='delete-form'>";
            echo "<input type='hidden' name='image_id' value='" . $row['id'] . "'>";
            echo "<button type='submit' class='delete-btn'>Delete</button>";
            echo "</form>";
            echo "</div>";
        }
    } else {
        echo "No images uploaded yet.";
    }

    $conn->close();
    ?>
    <br><br>
    <form action="upload.php" method="post" enctype="multipart/form-data">
        <label for="image">Upload Image:</label><br>
        <input type="file" id="image" name="image" accept="image/*" required><br><br>
        <input type="submit" value="Upload Image">
    </form>
    <br>
    <a href="view_profiles.php">View Other Profiles</a>
    <br>

    <form action="<?php echo htmlspecialchars($_SERVER['HTTP_REFERER']); ?>" method="get">
        <button type="submit">Back</button>
    </form>
</div>
</body>
</html>