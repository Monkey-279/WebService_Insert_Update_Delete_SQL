package com.tavanhieu.websiteservices

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class UpdateSinhVienActivity : AppCompatActivity() {
    private lateinit var btnUpdate: Button
    private lateinit var btnHuyUpdate: Button
    private lateinit var edtHoTenUpdate: EditText
    private lateinit var edtNamSinhUpdate: EditText
    private lateinit var edtDiaChiUpdate: EditText
    private lateinit var sv: SinhVien

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_sinh_vien)

        anhXa()
        sv = intent.getSerializableExtra("DataSV") as SinhVien
        edtHoTenUpdate.setText(sv.hoten)
        edtNamSinhUpdate.setText(sv.namsinh.toString())
        edtDiaChiUpdate.setText(sv.diachi)

        btnUpdate.setOnClickListener {
            if(edtHoTenUpdate.text.isEmpty() || edtNamSinhUpdate.text.isEmpty() || edtDiaChiUpdate.text.isEmpty()) {
                Toast.makeText(this, "Dữ liệu trống", Toast.LENGTH_SHORT).show()
            } else {
                capNhatSinhVien("http://192.168.1.6/PHPbai1/UpdateSinhVien.php")
            }
        }
        btnHuyUpdate.setOnClickListener { finish() }
    }
    private fun anhXa() {
        btnHuyUpdate        = findViewById(R.id.buttonHuyUpdate)
        btnUpdate           = findViewById(R.id.buttonUpdate)
        edtHoTenUpdate      = findViewById(R.id.editTextHoTenUpdate)
        edtNamSinhUpdate    = findViewById(R.id.editTextNamSinhUpdate)
        edtDiaChiUpdate     = findViewById(R.id.editTextDiaChiUpdate)
    }
    private fun capNhatSinhVien(uriUpdate: String) {
        val stringRequest = object: StringRequest(Method.POST, uriUpdate,
            {
                if(it.trim() == "success") {
                    Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
            }, {
                Toast.makeText(this, "Error: " + it.message.toString(), Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["idSV"]      = sv.id.toString()
                params["hotenSV"]   = edtHoTenUpdate.text.toString().trim()
                params["namsinhSV"] = edtNamSinhUpdate.text.toString().trim()
                params["diachiSV"]  = edtDiaChiUpdate.text.toString().trim()
                return params
            }
        }
        val requetsQueue = Volley.newRequestQueue(this)
        requetsQueue.add(stringRequest)
    }
}