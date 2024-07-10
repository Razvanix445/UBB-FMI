<?php
$con = mysqli_connect("localhost", "root", "", "trenuri");
if (!$con) {
    die('Could not connect: ' . mysqli_error());
}

$result = mysqli_query($con, "SELECT DISTINCT placa_video FROM produse");

$data = array();
while($row = mysqli_fetch_assoc($result)) {
    $data[] = $row['placa_video'];
}

echo json_encode($data);

mysqli_close($con);
?>