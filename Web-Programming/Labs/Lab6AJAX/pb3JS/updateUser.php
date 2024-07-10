<?php
$con = mysqli_connect("localhost", "root", "", "trenuri");
if (!$con) {
    die('Could not connect: ' . mysqli_error($con));
}

$id = $_POST['id'];
$nume = $_POST['nume'];
$prenume = $_POST['prenume'];
$telefon = $_POST['telefon'];
$email = $_POST['email'];

$sql = "UPDATE users SET nume = '$nume', prenume = '$prenume', telefon = '$telefon', email = '$email' WHERE id = $id";
if (mysqli_query($con, $sql)) {
    echo "Record updated successfully";
} else {
    echo "Error updating record: " . mysqli_error($con);
}

mysqli_close($con);
?>