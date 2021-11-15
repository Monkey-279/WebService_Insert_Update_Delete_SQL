<?php 
	require "ConnectData.php";

	$id = $_POST['idSV'];

	$query = "DELETE FROM student WHERE id = '$id'";
	if(mysqli_query($connect, $query)) {
		echo "success";
	} else {
		echo "error";
	}
?>