<?php
$con = mysqli_connect("localhost", "root", "", "trenuri");
if (!$con) {
    die('Could not connect: ' . mysqli_error());
}

$page = isset($_GET['page']) ? $_GET['page'] : 1;
$items_per_page = 3;
$offset = ($page - 1) * $items_per_page;

$sql = "SELECT nume, prenume, telefon, email FROM users LIMIT $offset, $items_per_page";
$result = mysqli_query($con, $sql);

$data = array();
while($row = mysqli_fetch_assoc($result)) {
    $data[] = $row;
}

$count_result = mysqli_query($con, "SELECT COUNT(*) as total FROM users");
$count_row = mysqli_fetch_assoc($count_result);
$total_count = $count_row['total'];

echo json_encode(array('data' => $data, 'total' => $total_count));

mysqli_close($con);
?>