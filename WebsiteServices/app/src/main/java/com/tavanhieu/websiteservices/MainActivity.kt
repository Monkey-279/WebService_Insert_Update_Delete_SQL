package com.tavanhieu.websiteservices

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    lateinit var adapter: SinhVienAdapter
    var arr: ArrayList<SinhVien> = ArrayList()
    lateinit var lsvDanhSach: ListView
    val uri = "http://192.168.1.6/PHPbai1/SinhVien.php"
    val uriDelete = "http://192.168.1.6/PHPbai1/DeleteSinhVien.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lsvDanhSach = findViewById(R.id.lsvDanhSach)
        adapter = SinhVienAdapter(this, R.layout.dong_sinh_vien, arr)
        lsvDanhSach.adapter = adapter

        readJSON(uri)
    }
    private fun readJSON(uri: String) {
        try {
            val requestQueue = Volley.newRequestQueue(this)
            val jsonArray = JsonArrayRequest(Request.Method.GET, uri, null,
                {
                    arr.clear()
                    for(i in 1 .. it.length()) {    //duyệt mảng JSONArray
                        val obj = it.getJSONObject(i-1)
                        arr.add(SinhVien(obj.getInt("ID"),
                            obj.getString("name"),
                            obj.getInt("age"),
                            obj.getString("address")
                        ))
                    }
                    adapter.notifyDataSetChanged()//cập nhật lại dữ liệu sau khi thay đổi
                }, {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                })
            requestQueue.add(jsonArray)
        } catch(e: Exception) {
            Toast.makeText(this, "Error URI! " + e.message.toString(), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)//truyền vào tên layout
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuAdd) {//so sánh với id của item được chọn
            startActivity(Intent(this, ThemSinhVienActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    fun  xacNhanXoa(id: Int) {
        val stringRequest = object: StringRequest(Method.POST, uriDelete, {
            if(it.toString().equals("succes")) {
                Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show()
                readJSON(uri)
            }
        }, {
            Toast.makeText(this, "Error: " + it.message.toString(), Toast.LENGTH_SHORT).show()
        }) {
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["idSV"] = id.toString()
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}