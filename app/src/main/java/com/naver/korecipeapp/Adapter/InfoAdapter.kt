package com.naver.korecipeapp.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naver.korecipeapp.Object.BaseIm
import com.naver.korecipeapp.Object.OrderIm
import com.naver.korecipeapp.Object.cooking
import com.naver.korecipeapp.R
import org.w3c.dom.Text

class InfoAdapter(val itemView: ArrayList<OrderIm>): RecyclerView.Adapter<InfoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: InfoAdapter.ViewHolder, position: Int) {
        holder.orderNum.text = itemView.get(position).order.num.toString()
        if(itemView.get(position).byteimage != null && itemView.get(position).byteimage!!.size>0){
            var bitmap: Bitmap = BitmapFactory.decodeByteArray(itemView.get(position).byteimage, 0, itemView.get(position).byteimage!!.size)
            holder.orderImage.setImageBitmap(bitmap)
        }else{
            holder.orderImage.visibility = View.GONE
        }
        holder.orderOrder.text = itemView.get(position).order.c_order
    }

    override fun getItemCount(): Int {
        return itemView.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val orderNum: TextView = itemView.findViewById(R.id.orderNum)
        val orderImage: ImageView = itemView.findViewById(R.id.orderImage)
        val orderOrder: TextView = itemView.findViewById(R.id.orderOrder)
    }

}