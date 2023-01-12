package com.naver.korecipeapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naver.korecipeapp.Object.rcomment
import com.naver.korecipeapp.R

class RchatAdapter(val itemView: ArrayList<rcomment>):RecyclerView.Adapter<RchatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RchatAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_chat_list_item,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RchatAdapter.ViewHolder, position: Int) {
        holder.rUserid.text = itemView.get(position).userid
        holder.date.text = itemView.get(position).insert_date.toString()
        holder.chat.text = itemView.get(position).chat
        holder.star.rating = itemView.get(position).star
        holder.star.setIsIndicator(true)
    }

    override fun getItemCount(): Int {
        return itemView.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val rUserid: TextView = itemView.findViewById(R.id.rUserid)
        val date: TextView = itemView.findViewById(R.id.date)
        val chat: TextView = itemView.findViewById(R.id.chat)
        val star: RatingBar = itemView.findViewById(R.id.star)
    }
}