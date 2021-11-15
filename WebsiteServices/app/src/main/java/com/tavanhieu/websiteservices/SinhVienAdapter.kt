package com.tavanhieu.websiteservices

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class SinhVienAdapter constructor(var context: MainActivity, var layout: Int, var arr:ArrayList<SinhVien>): BaseAdapter() {

    override fun getCount(): Int {
        return arr.size
    }

    override fun getItem(position: Int): Any {
        return arr[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ViewHolder{ //Tạo lớp viewholder để lưu trữ dữ liệu cho lần mở sau nếu đã có Data thì ko cần load lại
        lateinit var txtHoTen: TextView
        lateinit var txtNamSinh: TextView
        lateinit var txtDiaChi: TextView
        lateinit var imgXoa: ImageView
        lateinit var imgSua: ImageView
    }
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        val holder: ViewHolder
        if(view == null) {
            holder = ViewHolder()
            val minflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = minflater.inflate(layout, null)//Chuyển layout<dong_sinh_vien.xml> thành view để đổ vào ListItem

            holder.txtHoTen     = view.findViewById(R.id.txtTitle)
            holder.txtNamSinh   = view.findViewById(R.id.txtNamSinh)
            holder.txtDiaChi    = view.findViewById(R.id.txtDiaChi)
            holder.imgSua       = view.findViewById(R.id.imgEdit)
            holder.imgXoa       = view.findViewById(R.id.imgDel)
            view.tag = holder   //Gán dữ liệu cho viewtag để lần load sau ko cần tải lại data
        } else {
            holder = view.tag as ViewHolder //Gán dữ liệu đã có về lại holder
        }

        val sv = arr[position]
        holder.txtHoTen.text    = sv.hoten
        holder.txtNamSinh.text  = "Năm sinh: " + sv.namsinh.toString()
        holder.txtDiaChi.text   = sv.diachi

        holder.imgSua.setOnClickListener {
            val intent = Intent(context, UpdateSinhVienActivity::class.java)
            intent.putExtra("DataSV", sv)
            context.startActivity(intent)
        }
        holder.imgXoa.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setMessage("Bạn có muốn xóa " + sv.hoten + " không?")
            dialog.setNegativeButton("Không", DialogInterface.OnClickListener { dialog, i ->
                dialog.cancel()
            })
            dialog.setPositiveButton("Yess", DialogInterface.OnClickListener { dialog, i ->
                context.xacNhanXoa(sv.id)
                Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, MainActivity::class.java))
            })
            dialog.show()
        }
        return view
    }
}