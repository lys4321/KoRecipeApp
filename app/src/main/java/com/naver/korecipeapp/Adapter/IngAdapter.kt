package com.naver.korecipeapp.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naver.korecipeapp.Object.BaseIm
import com.naver.korecipeapp.Object.ingredients
import com.naver.korecipeapp.R

class IngAdapter(val itemView: ArrayList<ingredients>): RecyclerView.Adapter<IngAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ing_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngAdapter.ViewHolder, position: Int) {
        holder.name.text=itemView.get(position).ing_name
        holder.gram.text=itemView.get(position).amount

        val layoutParams = holder.itemView.layoutParams
        layoutParams.height = 100
        holder.itemView.requestLayout()
    }

    override fun getItemCount(): Int {
        return itemView.size
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.name)
        val gram: TextView = itemView.findViewById(R.id.gram)
    }
}