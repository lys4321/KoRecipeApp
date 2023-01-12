package com.naver.korecipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ScrollView
import android.widget.SearchView
import com.naver.korecipeapp.Adapter.MainAdapter
import com.naver.korecipeapp.Adapter.ReceipeAdapter
import com.naver.korecipeapp.Object.BaseIm
import com.naver.korecipeapp.Object.base
import kotlinx.coroutines.*
import java.io.*
import java.net.Socket
import java.util.*
import kotlin.Exception
import kotlin.collections.ArrayList

class RecipeList : AppCompatActivity() {
    lateinit var scrollView: ScrollView
    lateinit var searchView: SearchView
    lateinit var listView: ListView

    lateinit var str:String

    suspend fun ListCoroutine(code: String) = withContext(Dispatchers.IO){
        var arr: ArrayList<BaseIm> = ArrayList<BaseIm>()
        lateinit var inob: ArrayList<BaseIm>
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int = 6
        lateinit var bw: BufferedWriter
        lateinit var ois: ObjectInputStream
        try{
            socket = Socket("192.168.0.3",9500)
            ins = socket.getInputStream()
            os = socket.getOutputStream()
            os.write(data)
            os.flush()

            bw = BufferedWriter(OutputStreamWriter(os))

            bw.write(code+"\n")
            bw.flush()

            var num = ins.read()
            println(num)
            var inp: BaseIm
            try{
                for (i in 0..num-1){
                    println("레시피 목록 정보체크 1")
                    ois = ObjectInputStream(socket.getInputStream())
                    println("레시피 목록 정보체크 2")
                    inp = ois.readObject() as BaseIm
                    println("레시피 목록 정보체크 3")
                    arr.add(inp)
                    println("레시피 목록 정보체크 4")
                }
            }catch (e:EOFException){

            }

            /*
            for (i in 0..num-1){
                inp = ois.readObject() as BaseIm
                println(inp.base.recipe_name)
                arr.add(inp)
            }

             */
            /*
            var ob = ois.readObject() as ArrayList<BaseIm>
            if (ob != null){
                for (i in 0..ob.size-1){
                    arr.add(ob.get(i))
                    println(ob.get(i).base.recipe_name)
                }
            }
            try{
                ois = ObjectInputStream(ins)
                arr = ois.readObject() as ArrayList<BaseIm>
            }catch (e: Exception){
                e.printStackTrace()
            }

             */
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            try{
                if (ois != null){
                    ois.close()
                }
                if (bw != null){
                    bw.close()
                }
                if (os != null){
                    os.close()
                }
                if (ins != null){
                    ins.close()
                }
                if (socket != null){
                    socket.close()
                }
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
        arr
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        listView = findViewById<ListView>(R.id.Rlist)
        str = intent.getStringExtra("cartegory").toString()

        CoroutineScope(Dispatchers.Main).launch {
            var list = ListCoroutine(str)
            val adapter = ReceipeAdapter(applicationContext, list)
            listView.adapter=adapter
        }


        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val BI = adapterView.getItemAtPosition(i) as BaseIm
            val bas = BI.base
            val intent = Intent(this, RecipeInfo::class.java)
            intent.putExtra("base",bas)
            startActivity(intent)
        }
    }
}