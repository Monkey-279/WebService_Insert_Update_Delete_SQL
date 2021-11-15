<?php	
	$connect = mysqli_connect("localhost", "root", "", "sinhvien");//Kết nối tới CSDL sinhvien
	mysqli_query($connect, "SET NAMES 'utf8'"); //Cài đặt để trả về kiểu tiếng việt
?>