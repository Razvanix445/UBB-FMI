<?php
session_start();

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "trenuri";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_FILES["image"])) {
    $allowed_types = array('jpg', 'jpeg', 'png', 'gif');
    $file_ext = pathinfo($_FILES["image"]["name"], PATHINFO_EXTENSION);
    if (!in_array($file_ext, $allowed_types)) {
        die("Error: Only JPG, JPEG, PNG, GIF files are allowed.");
    }

    $image = file_get_contents($_FILES['image']['tmp_name']);
    $username = $_SESSION['username'];

    $stmt = $conn->prepare("INSERT INTO images (username, image) VALUES (?, ?)");
    $null = NULL;
    $stmt->bind_param("sb", $username, $null);
    $stmt->send_long_data(1, $image);
    $stmt->execute();

    if ($stmt->affected_rows > 0) {
        echo "Image uploaded successfully.";
    } else {
        echo "Error uploading image.";
    }

    $stmt->close();
}

$conn->close();
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Image</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container">
    <h2>Upload Image</h2>

    <form action="<?php echo $_SERVER['HTTP_REFERER']; ?>" method="get">
        <button type="submit">Back</button>
    </form>
</div>
</body>
</html>