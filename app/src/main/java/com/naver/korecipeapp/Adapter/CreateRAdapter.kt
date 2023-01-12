package com.naver.korecipeapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naver.korecipeapp.Object.ingredients
import com.naver.korecipeapp.R

class CreateRAdapter(val ing: ArrayList<ingredients>): RecyclerView.Adapter<CreateRAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateRAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.create_inglinst, parent, false)
        return CreateRAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreateRAdapter.ViewHolder, position: Int) {
        holder.name.text=ing.get(position).ing_name
        holder.gram.text=ing.get(position).amount
        val layoutParams = holder.itemView.layoutParams
        layoutParams.height=100
        holder.itemView.requestLayout()
    }

    override fun getItemCount(): Int {
        return ing.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.name)
        val gram: TextView = itemView.findViewById(R.id.gram)
    }

}