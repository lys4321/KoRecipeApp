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
import com.naver.korecipeapp.Object.BaseIm
import com.naver.korecipeapp.Object.base
import com.naver.korecipeapp.R

class ReceipeAdapter (val context: Context, val Baseim:ArrayList<BaseIm>): BaseAdapter() {
    override fun getCount(): Int {
        return Baseim.size
    }

    override fun getItem(p0: Int): BaseIm {
        return Baseim.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    fun getByteAraay(p0: Int): ByteArray{
        return Baseim.get(p0).byteimage
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.recipe_list_item,null)
        val image = view.findViewById<ImageView>(R.id.main_image)
        val title = view.findViewById<TextView>(R.id.title)
        val summary = view.findViewById<TextView>(R.id.summary)
        val cookingtime = view.findViewById<TextView>(R.id.cookingtime)
        val userid = view.findViewById<TextView>(R.id.userid)

        var base = Baseim.get(p0).base
        if (Baseim.get(p0).byteimage!=null&&Baseim.get(p0).byteimage.size>0){
            var bitmap: Bitmap = BitmapFactory.decodeByteArray(Baseim.get(p0).byteimage, 0, Baseim.get(p0).byteimage.size)
            image.setImageBitmap(bitmap)
        }
        title.text=Baseim.get(p0).base.recipe_name
        summary.text=Baseim.get(p0).base.recipe_summary
        cookingtime.text=Baseim.get(p0).base.cooking_time
        userid.text=Baseim.get(p0).base.userid

        return view
    }
}