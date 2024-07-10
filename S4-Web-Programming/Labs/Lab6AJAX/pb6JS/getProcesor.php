<?php
$con = mysqli_connect("localhost", "root", "", "trenuri");
if (!$con) {
    die('Could not connect: ' . mysqli_error());
}

$result = mysqli_query($con, "SELECT DISTINCT procesor FROM produse");

$data = array();
while($row = mysqli_fetch_assoc($result)) {
    $data[] = $row['procesor'];
}

echo json_encode($data);

mysqli_close($con);
?>