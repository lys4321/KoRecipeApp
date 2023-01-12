package com.naver.korecipeapp.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naver.korecipeapp.Object.OrderIm
import com.naver.korecipeapp.Object.ingredients
import com.naver.korecipeapp.R
import org.w3c.dom.Text

class CreaetROAdapter(val orderIm: ArrayList<OrderIm>): RecyclerView.Adapter<CreaetROAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreaetROAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.create_order_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreaetROAdapter.ViewHolder, position: Int) {
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height=100
        holder.itemView.requestLayout()
        holder.orderOrder.text = orderIm.get(position).order.c_order.toString()
        if(orderIm.get(position).byteimage != null && orderIm.get(position).byteimage!!.size>0){
            var bitmap: Bitmap = BitmapFactory.decodeByteArray(orderIm.get(position).byteimage, 0, orderIm.get(position).byteimage!!.size)
            holder.orderImage.setImageBitmap(bitmap)
        }else{
            holder.orderImage.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return orderIm.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val orderImage: ImageView = itemView.findViewById(R.id.orderim)
        val orderOrder: TextView = itemView.findViewById(R.id.order)
    }
}

