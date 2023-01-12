package com.naver.korecipeapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naver.korecipeapp.Adapter.PcomAdapter
import com.naver.korecipeapp.Object.base
import com.naver.korecipeapp.Object.pcomment
import com.naver.korecipeapp.Object.post
import kotlinx.coroutines.*
import java.io.*
import java.net.Socket
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.Exception
import kotlin.collections.ArrayList

class PostSelect : AppCompatActivity() {
    lateinit var title: TextView
    lateinit var date:TextView
    lateinit var post:TextView
    lateinit var views:TextView
    lateinit var pcomcycle:RecyclerView
    lateinit var chat:EditText
    lateinit var insert:Button
    lateinit var pimage:ImageView
    lateinit var pimage2:ImageView
    lateinit var pimage3:ImageView
    lateinit var pimage4:ImageView
    lateinit var pvideo:VideoView
    var inBarr: ArrayList<ByteArray> = ArrayList<ByteArray>()
    lateinit var imageBarr:ByteArray

    suspend fun PostImageCoroutine(post: post) = withContext(Dispatchers.IO){
        var socket: Socket?
        var ins: InputStream?
        var os: OutputStream?
        var bw:BufferedWriter?
        var br:BufferedReader?
        val data: Int =22
        var ois: ObjectInputStream?
        var oos: ObjectOutputStream?
        try {
            socket = Socket("192.168.0.3", 9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os.write(data)
            os.flush()

            oos = ObjectOutputStream(socket.getOutputStream())
            oos.writeObject(post)
            if(post.check_image == true && post.check_video == true){
                val check = ins.read()
                println("이미지 개수"+check)
                var by:ByteArray
                try{
                    for(i in 0..check-1){
                        println("이미지 1")
                        ois = ObjectInputStream(socket.getInputStream())
                        println("이미지 2")
                        by = ois.readObject() as ByteArray
                        println("이미지 3")
                        inBarr.add(by)
                    }
                    println("영상 1")
                    ois = ObjectInputStream(socket.getInputStream())
                    println("영상 2")
                    imageBarr = ois.readObject() as ByteArray
                    println("영상 3")
                    println("영상 3")
                    println("영상 3")
                    println("영상 3")
                }catch (e: Exception){

                }
            } else if(post.check_image == false && post.check_video == true){
                try{
                    println("영상 1")
                    ois = ObjectInputStream(socket.getInputStream())
                    println("영상 2")
                    imageBarr = ois.readObject() as ByteArray
                    println("영상 3")
                    println("영상 3")
                    println("영상 3")
                    println("영상 3")
                }catch (e:Exception){

                }
            } else if(post.check_image == true && post.check_video == false){
                val check = ins.read()
                println(check)
                var by:ByteArray
                try{
                    for(i in 0..check-1){
                        println("이미지 1")
                        ois = ObjectInputStream(socket.getInputStream())
                        println("이미지 2")
                        by = ois.readObject() as ByteArray
                        println("이미지 3")
                        inBarr.add(by)
                    }
                }catch (e:Exception){

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



    suspend fun PostInsertChatCoroutine(pcom:pcomment) = withContext(Dispatchers.IO){
        var socket: Socket?
        var ins: InputStream?
        var os: OutputStream?
        var bw:BufferedWriter?
        var br:BufferedReader?
        val data: Int = 19
        var ois: ObjectInputStream?
        var oos: ObjectOutputStream?
        lateinit var check:String
        try {
            socket = Socket("192.168.0.3", 9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os.write(data)
            os.flush()

            bw = BufferedWriter(OutputStreamWriter(os))
            oos = ObjectOutputStream(os)
            oos.writeObject(pcom)
            oos.flush()

            br = BufferedReader(InputStreamReader(ins))
            check=br.readLine().toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        check
    }

    suspend fun PostChatListCoroutine(code:String) = withContext(Dispatchers.IO){
        lateinit var arrayList: ArrayList<pcomment>
        var socket: Socket?
        var ins: InputStream?
        var os: OutputStream?
        var bw:BufferedWriter?
        val data: Int = 18
        var ois: ObjectInputStream?
        var oos: ObjectOutputStream?
        try {
            socket = Socket("192.168.0.3", 9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os.write(data)
            os.flush()

            bw = BufferedWriter(OutputStreamWriter(os))

            bw.write(code+"\n")
            bw.flush()

            ois = ObjectInputStream(ins)
            arrayList = ois.readObject() as ArrayList<pcomment>
            if (arrayList == null) {
                println("뭔가 실패")
            }
            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        arrayList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_select)

        var postin: post = intent.getSerializableExtra("post") as post
        title = findViewById<TextView>(R.id.title)
        date = findViewById<TextView>(R.id.date)
        post = findViewById<TextView>(R.id.post)
        views = findViewById<TextView>(R.id.views)
        pcomcycle= findViewById<RecyclerView>(R.id.pcomcycle)
        chat =findViewById<EditText>(R.id.chat)
        insert=findViewById<Button>(R.id.insert)
        pimage= findViewById<ImageView>(R.id.pimage)
        pimage2= findViewById<ImageView>(R.id.pimage2)
        pimage3= findViewById<ImageView>(R.id.pimage3)
        pimage4= findViewById<ImageView>(R.id.pimage4)
        pvideo = findViewById<VideoView>(R.id.pvideo)

        title.text = postin.title
        date.text = postin.post_date.toString()
        post.text = postin.post_in
        views.text = postin.view_count.toString()


        CoroutineScope(Dispatchers.Main).launch {
            val PCL = PostChatListCoroutine(postin.post_code)
            val adapter = PcomAdapter(applicationContext, PCL)
            val lm = LinearLayoutManager(this@PostSelect)
            pcomcycle.layoutManager = lm
            pcomcycle.setHasFixedSize(true)
            pcomcycle.adapter = adapter

            val result = PostImageCoroutine(postin)
            if(postin.check_video == true && postin.check_image == true){
                val size = inBarr.size
                when (size){
                    1->{
                        var bitmap: Bitmap = BitmapFactory.decodeByteArray(inBarr.get(0), 0, inBarr.get(0).size)
                        pimage.visibility = View.VISIBLE
                        pimage.setImageBitmap(bitmap)
                    }
                    2->{
                        var bitmap: Bitmap = BitmapFactory.decodeByteArray(inBarr.get(0), 0, inBarr.get(0).size)
                        pimage.visibility = View.VISIBLE
                        pimage.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(1), 0, inBarr.get(1).size)
                        pimage2.visibility = View.VISIBLE
                        pimage2.setImageBitmap(bitmap)
                    }
                    3->{
                        var bitmap: Bitmap = BitmapFactory.decodeByteArray(inBarr.get(0), 0, inBarr.get(0).size)
                        pimage.visibility = View.VISIBLE
                        pimage.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(1), 0, inBarr.get(1).size)
                        pimage2.visibility = View.VISIBLE
                        pimage2.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(2), 0, inBarr.get(2).size)
                        pimage3.visibility = View.VISIBLE
                        pimage3.setImageBitmap(bitmap)
                    }
                    4->{
                        var bitmap: Bitmap = BitmapFactory.decodeByteArray(inBarr.get(0), 0, inBarr.get(0).size)
                        pimage.visibility = View.VISIBLE
                        pimage.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(1), 0, inBarr.get(1).size)
                        pimage2.visibility = View.VISIBLE
                        pimage2.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(2), 0, inBarr.get(2).size)
                        pimage3.visibility = View.VISIBLE
                        pimage3.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(3), 0, inBarr.get(3).size)
                        pimage4.visibility = View.VISIBLE
                        pimage4.setImageBitmap(bitmap)
                    }

                }


                if (imageBarr != null) {
                    var file = File.createTempFile(postin.post_code, ".mp4")
                    var fout: FileOutputStream = FileOutputStream(file)
                    fout.write(imageBarr)
                    fout.flush()
                    println(cacheDir)
                    var uri: Uri = Uri.fromFile(file)
                    pvideo.visibility = View.VISIBLE
                    pvideo.setMediaController(MediaController(this@PostSelect));
                    pvideo.setVideoURI(uri)
                    file.deleteOnExit()
                }
            }else if(postin.check_video == false && postin.check_image == true){
                val size = inBarr.size
                when (size){
                    1->{
                        var bitmap: Bitmap = BitmapFactory.decodeByteArray(inBarr.get(0), 0, inBarr.get(0).size)
                        pimage.visibility = View.VISIBLE
                        pimage.setImageBitmap(bitmap)
                    }
                    2->{
                        var bitmap: Bitmap = BitmapFactory.decodeByteArray(inBarr.get(0), 0, inBarr.get(0).size)
                        pimage.visibility = View.VISIBLE
                        pimage.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(1), 0, inBarr.get(1).size)
                        pimage2.visibility = View.VISIBLE
                        pimage2.setImageBitmap(bitmap)
                    }
                    3->{
                        var bitmap: Bitmap = BitmapFactory.decodeByteArray(inBarr.get(0), 0, inBarr.get(0).size)
                        pimage.visibility = View.VISIBLE
                        pimage.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(1), 0, inBarr.get(1).size)
                        pimage2.visibility = View.VISIBLE
                        pimage2.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(2), 0, inBarr.get(2).size)
                        pimage3.visibility = View.VISIBLE
                        pimage3.setImageBitmap(bitmap)
                    }
                    4->{
                        var bitmap: Bitmap = BitmapFactory.decodeByteArray(inBarr.get(0), 0, inBarr.get(0).size)
                        pimage.visibility = View.VISIBLE
                        pimage.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(1), 0, inBarr.get(1).size)
                        pimage2.visibility = View.VISIBLE
                        pimage2.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(2), 0, inBarr.get(2).size)
                        pimage3.visibility = View.VISIBLE
                        pimage3.setImageBitmap(bitmap)
                        bitmap= BitmapFactory.decodeByteArray(inBarr.get(3), 0, inBarr.get(3).size)
                        pimage4.visibility = View.VISIBLE
                        pimage4.setImageBitmap(bitmap)
                    }

                }
            }else if(postin.check_video == true && postin.check_image == false){
                if (imageBarr != null) {
                    var file = File.createTempFile(postin.post_code, ".mp4")
                    var fout: FileOutputStream = FileOutputStream(file)
                    fout.write(imageBarr)
                    fout.flush()
                    println(cacheDir)
                    var uri: Uri = Uri.fromFile(file)
                    pvideo.visibility = View.VISIBLE
                    pvideo.setMediaController(MediaController(this@PostSelect));
                    pvideo.setVideoURI(uri)
                    file.deleteOnExit()
                }
            }
        }
        insert.setOnClickListener {
            val code = postin.post_code
            val SP = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
            val id = SP.getString("ID", null)

            val form = SimpleDateFormat("yyyymmdd")
            var date = form.format(System.currentTimeMillis()).toString()
            var dada:Date = SimpleDateFormat("yyyymmdd").parse(date)
            val str = chat.text.toString()
            println(dada)
            val pcomment = pcomment(code, id.toString(), dada, str)
            CoroutineScope(Dispatchers.Main).launch {
                PostInsertChatCoroutine(pcomment)
                //대글목록 새로고침?
            }
        }
    }
}