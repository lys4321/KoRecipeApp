package com.naver.korecipeapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naver.korecipeapp.Object.post
import com.naver.korecipeapp.Object.rcomment
import com.naver.korecipeapp.R
import org.w3c.dom.Text

class PostAdpater(val context: Context, val itemView: ArrayList<post>): RecyclerView.Adapter<PostAdpater.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdpater.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list_item,parent, false)
        val viewHolder = ViewHolder(view)
        return ViewHolder(view)
    }

    interface ItemClick{
        fun onClick(view: View, position: Int)
    }

    var itemClick:ItemClick? = null

    override fun onBindViewHolder(holder: PostAdpater.ViewHolder, position: Int) {
       if (itemClick != null){
           holder?.itemView?.setOnClickListener { v ->
               itemClick?.onClick(v, position)
           }
       }
        holder.bind(itemView.get(position), context)


    }

    override fun getItemCount(): Int {
        return itemView.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.title)
        val userid: TextView = itemView.findViewById(R.id.userid)
        val date: TextView = itemView.findViewById(R.id.date)

        fun bind(item:post, context:Context){
            title.text = item.title
            userid.text = item.userid
            date.text = item.post_date.toString()

            val layoutParams = itemView.layoutParams
            layoutParams.height = 100
            itemView.requestLayout()
        }
    }
}