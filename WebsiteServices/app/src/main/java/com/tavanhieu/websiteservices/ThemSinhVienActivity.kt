package com.tavanhieu.websiteservices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ThemSinhVienActivity : AppCompatActivity() {
    private lateinit var btnThem:Button
    private lateinit var btnHuy:Button
    private lateinit var edtHoTen:EditText
    private lateinit var edtNamSinh:EditText
    private lateinit var edtDiaChi:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_them_sinh_vien)

        anhXa()
        btnThem.setOnClickListener {
            if(edtHoTen.text.isEmpty() || edtDiaChi.text.isEmpty() || edtNamSinh.text.isEmpty()) {
                Toast.makeText(this, "Cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                themSinhVien("http://192.168.1.6/PHPbai1/InsertSinhVien.php")
            }
        }
        btnHuy.setOnClickListener {
            finish()
        }
    }

    private fun anhXa() {
        btnThem = findViewById(R.id.buttonThem)
        btnHuy = findViewById(R.id.buttonHuy)
        edtHoTen = findViewById(R.id.editTextHoTen)
        edtNamSinh = findViewById(R.id.editTextNamSinh)
        edtDiaChi = findViewById(R.id.editTextDiaChi)
    }

    private fun themSinhVien(uri: String) {
        val stringRequest = object : StringRequest(Method.POST, uri, //Sử dụng Object ở trước StringRequest để hiện Override get sParams()
            {
                if(it.toString() == "success") { //Kiểm tra chuỗi trả về từ web
                    Toast.makeText(this, "Đã thêm", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java)) //Load lại để tải dữ liệu sau khi thêm
                    finish()
                } else {
                    Toast.makeText(this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show()
                }
            }, {
                Toast.makeText(this, "Error" + it.message.toString(), Toast.LENGTH_SHORT).show()
            }){
            override fun getParams(): Map<String, String> { //Chưa hiểu cách thức hoạt động của hàm này :<
                val params: MutableMap<String, String> = HashMap()
                params["hotenSV"]   = edtHoTen.text.toString().trim()   //Key: tên $_POST trên file PHP
                params["namsinhSV"] = edtNamSinh.text.toString().trim() //Values: Giá trị truyền lên để thực hiện Insert
                params["diachiSV"]  = edtDiaChi.text.toString().trim()
                return params
            }
        }
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}