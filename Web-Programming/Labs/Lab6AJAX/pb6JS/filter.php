<?php
$con = mysqli_connect("localhost", "root", "", "trenuri");
if (!$con) {
    die('Could not connect: ' . mysqli_error());
}

$producator = $_GET['producator'];
$procesor = $_GET['procesor'];
$memorie = $_GET['memorie'];
$hdd = $_GET['hdd'];
$video = $_GET['video'];

$sql = "SELECT * FROM produse WHERE 1";

if (!empty($producator)) {
    $sql .= " AND producator = '$producator'";
}
if (!empty($procesor)) {
    $sql .= " AND procesor = '$procesor'";
}
if (!empty($memorie)) {
    $sql .= " AND memorie = '$memorie'";
}
if (!empty($hdd)) {
    $sql .= " AND capacitateHDD = '$hdd'";
}
if (!empty($video)) {
    $sql .= " AND placa_video = '$video'";
}

$result = mysqli_query($con, $sql);

$data = array();
while($row = mysqli_fetch_assoc($result)) {
    $data[] = $row;
}

echo json_encode($data);

mysqli_close($con);
?>