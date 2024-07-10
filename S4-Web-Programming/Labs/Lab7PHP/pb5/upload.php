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
    <form action="upload_handler.php" method="post" enctype="multipart/form-data">
        <input type="file" name="image" id="image" required><br><br>
        <input type="submit" value="Upload">
    </form>
</div>
</body>
</html>