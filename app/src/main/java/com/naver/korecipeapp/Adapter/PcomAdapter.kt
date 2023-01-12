package com.naver.korecipeapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naver.korecipeapp.Object.pcomment
import com.naver.korecipeapp.Object.post
import com.naver.korecipeapp.R

class PcomAdapter(val context: Context, val itemView: ArrayList<pcomment>): RecyclerView.Adapter<PcomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PcomAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pcomment_list_item,parent, false)
        val viewHolder = PcomAdapter.ViewHolder(view)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PcomAdapter.ViewHolder, position: Int) {
        holder.bind(itemView.get(position), context)
    }

    override fun getItemCount(): Int {
        return itemView.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val pchat: TextView = itemView.findViewById(R.id.pchat)
        val userid: TextView = itemView.findViewById(R.id.userid)
        val date: TextView = itemView.findViewById(R.id.date)

        fun bind(item:pcomment, context: Context){
            userid.text = item.userid
            date.text = item.chat_date.toString()
            pchat.text = item.chat
            val layoutParams = itemView.layoutParams
            layoutParams.height = 250
            itemView.requestLayout()

        }
    }
}