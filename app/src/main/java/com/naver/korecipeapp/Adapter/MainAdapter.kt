package com.naver.korecipeapp.Adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.naver.korecipeapp.Object.BaseIm
import com.naver.korecipeapp.Object.base
import com.naver.korecipeapp.R
import org.w3c.dom.Text

class MainAdapter(val context: Context, val Baseim:ArrayList<BaseIm>): BaseAdapter() {
    override fun getCount(): Int {
        return Baseim.size
    }

    override fun getItem(p0: Int): BaseIm {
        return Baseim.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.main_list_item,null)
        val image = view.findViewById<ImageView>(R.id.imageView)
        val text = view.findViewById<TextView>(R.id.textView)

        var base = Baseim.get(p0).base
        println("삐트맵"+Baseim.get(p0).byteimage.size)
        if(Baseim.get(p0).byteimage!=null&&Baseim.get(p0).byteimage.size>0){
            var bitmap:Bitmap = BitmapFactory.decodeByteArray(Baseim.get(p0).byteimage, 0, Baseim.get(p0).byteimage.size)
            image.setImageBitmap(bitmap)
        }
        text.text=Baseim.get(p0).base.recipe_summary

        return view
    }


}