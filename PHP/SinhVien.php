<?php	
//Lấy dữ liệu từ Database SQL sever đẩy về Code
	require "ConnectData.php";
	$query = "SELECT * FROM student"; 
	$data = mysqli_query($connect, $query); //Tất cả dữ liệu select được sẽ được data trỏ tới

	class SinhVien{
	    function Student($id, $name, $age, $address){
	    	$this->ID = $id;
	        $this->name = $name;
	        $this->age = $age;
	        $this->address = $address;
	        return $this;
    	}
	}

	$mang = array();
	while($row = mysqli_fetch_assoc($data)) { //Tách dữ liệu thành từng dòng và push vào mảng.
		$sv = new SinhVien;
		array_push($mang, $sv->Student($row['id'], $row['hoten'], $row['namsinh'], $row['diachi']));
	}
	echo json_encode($mang);
?>