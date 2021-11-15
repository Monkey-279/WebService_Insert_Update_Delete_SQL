<?php
//Lấy dữ liệu từ code để insert vào Database SQL
	require "ConnectData.php";

//Post dùng để lấy dữ liệu theo key: được trả về
	$hoten = $_POST['hotenSV'];
	$namsinh = $_POST['namsinhSV'];
	$diachi = $_POST['diachiSV'];

	$query = "INSERT INTO student VALUES(null, '$hoten', '$namsinh', '$diachi')";
	if(mysqli_query($connect, $query)) { //hiện ra thông báo ở web để Lấy thông tin set thông báo cho app từ StringRequest
		echo "success";
	} else {
		echo "error";
	}
?>