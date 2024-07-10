<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    if (isset($_POST['student_id'])) {
        $_SESSION['student_id'] = $_POST['student_id'];
        header("Location: student_grades.php");
        exit();
    }
}
?>