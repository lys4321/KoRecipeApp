package com.naver.korecipeapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naver.korecipeapp.Adapter.MainAdapter
import com.naver.korecipeapp.Adapter.PostAdpater
import com.naver.korecipeapp.Object.BaseIm
import com.naver.korecipeapp.Object.post
import kotlinx.coroutines.*
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.lang.Exception
import java.net.Socket

class PostBoard : AppCompatActivity() {
    lateinit var precycle:RecyclerView
    lateinit var add: Button

    suspend fun PlistCoroutine() = withContext(Dispatchers.IO){
        lateinit var arrayList: ArrayList<post>
        var socket: Socket?
        var ins: InputStream?
        var os: OutputStream?
        val data: Int = 17
        var ois: ObjectInputStream?
        var oos: ObjectOutputStream?
        try{
            socket = Socket("192.168.0.3",9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os.write(data)
            os.flush()

            ois = ObjectInputStream(ins)
            arrayList = ois.readObject() as ArrayList<post>
            if (arrayList == null){
                println("뭔가 실패")
            }
            socket.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
        arrayList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_board)

        precycle = findViewById(R.id.precycle)
        add = findViewById(R.id.add)

        CoroutineScope(Dispatchers.Main).launch {
            val PCro = PlistCoroutine()
            val adapter = PostAdpater(applicationContext, PCro)
            val lm = LinearLayoutManager(this@PostBoard)
            precycle.layoutManager = lm
            precycle.setHasFixedSize(true)
            precycle.adapter = adapter
            adapter.itemClick = object : PostAdpater.ItemClick{
                override fun onClick(view: View, position: Int) {
                    val intent: Intent = Intent(this@PostBoard, PostSelect::class.java)
                    intent.putExtra("post",PCro.get(position))
                    startActivity(intent)
                }
            }
        }

        add.setOnClickListener {
            val SP = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
            val id = SP.getString("ID", null)
            if(id == null){
                Toast.makeText(applicationContext,"로그인이 필요합니다.", Toast.LENGTH_LONG).show()
            }else{
                val intent: Intent = Intent(this@PostBoard, CreatePost::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}