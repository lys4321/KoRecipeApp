package com.naver.korecipeapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naver.korecipeapp.Adapter.InfoAdapter
import com.naver.korecipeapp.Adapter.IngAdapter
import com.naver.korecipeapp.Adapter.RchatAdapter
import com.naver.korecipeapp.Object.*
import com.naver.korecipeapp.databinding.ActivityMainBinding
import com.naver.korecipeapp.databinding.ActivityRecipeInfoBinding
import kotlinx.coroutines.*
import java.io.*
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecipeInfo : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeInfoBinding
    var arr_OrderIm: ArrayList<OrderIm> = ArrayList<OrderIm>()
    var arr_Rcomment: ArrayList<rcomment> = ArrayList<rcomment>()
    var arr_Ing: ArrayList<ingredients> = ArrayList<ingredients>()

    var check: Boolean = false

    suspend fun BaseCoroutine(base: base) = withContext(Dispatchers.IO){
        var arr: ArrayList<BaseIm> = ArrayList<BaseIm>()
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int = 0
        lateinit var bw: BufferedWriter
        lateinit var ois: ObjectInputStream
        try{
            socket = Socket("192.168.0.3",9500)

            ins = socket.getInputStream()
            os = socket.getOutputStream()

            os.write(data)
            os.flush()

            bw = BufferedWriter(OutputStreamWriter(os))

            bw.write(base.recipe_code+"\n")
            bw.flush()


            try{
                println("1레시피 기본정보체크 1")
                ois = ObjectInputStream(socket.getInputStream())
                println("1레시피 기본정보체크 2")
                var baseIm = ois.readObject() as BaseIm
                println("1레시피 기본정보체크 3")
                arr.add(baseIm)
                println("1레시피 기본정보체크 4")
            }catch (e:IndexOutOfBoundsException){
                e.printStackTrace()
            }catch (e:EOFException){
                e.printStackTrace()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            if (ois != null) {
                ois.close()
            }
            if (bw != null) {
                bw.close()
            }
            if (os != null) {
                os.close()
            }
            if (ins != null) {
                ins.close()
            }
            if (socket != null) {
                socket.close()
            }
        }
        arr
    }

    suspend fun VideoCoroutine(base: base) = withContext(Dispatchers.IO){
        lateinit var byarr: ByteArray
        lateinit var inob: ByteArray
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        lateinit var ois: ObjectInputStream
        lateinit var oos: ObjectOutputStream
        val data: Int = 24
        try{
            socket = Socket("192.168.0.3",9500)

            ins = socket.getInputStream()
            os = socket.getOutputStream()

            os.write(data)
            os.flush()

            println("비디오정보 1")
            oos = ObjectOutputStream(socket.getOutputStream())
            println("비디오정보 2")
            oos.writeObject(base)
            println("비디오정보 3")

            try{
                println("2레시피 비디오정보 1")
                ois = ObjectInputStream(ins)
                println("2레시피 비디오정보 2")
                byarr = ois.readObject() as ByteArray
                println("2레시피 비디오정보 3")
            }catch (e:EOFException){

            }
        }catch (e:Exception){
            e.printStackTrace()
            socket.close()
        }finally {
            try{
                if (ois != null){
                    ois.close()
                }
                if (oos != null){
                    oos.close()
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
        byarr

    }

    suspend fun IngCoroutine(base: base) = withContext(Dispatchers.IO){
        var arr:ArrayList<ingredients> = ArrayList<ingredients>()
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int = 2
        lateinit var bw: BufferedWriter
        lateinit var ois: ObjectInputStream
        try{
            socket = Socket("192.168.0.3",9500)

            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()

            os.write(data)
            os.flush()

            bw = BufferedWriter(OutputStreamWriter(os))

            bw.write(base.recipe_code+"\n")
            bw.flush()
            var innum = ins.read()
            var inp:ingredients
            println(innum)
            try{
                for (i in 0..innum-1){
                    println("3레시피 기본정보체크 1")
                    ois = ObjectInputStream(socket.getInputStream())
                    println("4레시피 기본정보체크 2")
                    inp = ois.readObject() as ingredients
                    println("3레시피 기본정보체크 3")
                    arr.add(inp)
                    println("3레시피 기본정보체크 4")
                }
            }catch (e: EOFException){
                for (i in 0..innum-1){
                    println(arr.get(i).ing_name)
                }
            }

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

    suspend fun OrderCoroutine(base: base) = withContext(Dispatchers.IO){
        var arr: ArrayList<OrderIm> = ArrayList<OrderIm>()
        lateinit var inob: ArrayList<OrderIm>
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int = 3
        lateinit var bw: BufferedWriter
        lateinit var ois: ObjectInputStream
        try{
            socket = Socket("192.168.0.3",9500)
            ins = socket.getInputStream()
            os = socket.getOutputStream()

            os.write(data)
            os.flush()

            bw = BufferedWriter(OutputStreamWriter(os))

            bw.write(base.recipe_code+"\n")
            bw.flush()

            var num = ins.read()
            println(num)
            var inp:OrderIm
            try{
                for(i in 0..num){
                    println("체크 1")
                    ois = ObjectInputStream(socket.getInputStream())
                    println("체크 2")
                    inp = ois.readObject() as OrderIm
                    println("체크 3")
                    arr.add(inp)
                    println("체크 4")
                    println(arr.get(i).order.c_order)
                }
            }catch (e: EOFException){

            }catch (e: ClassCastException){

            }
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

    suspend fun RcomCoroutin(base: base) = withContext(Dispatchers.IO){
        var arr:ArrayList<rcomment> = ArrayList<rcomment>()
        lateinit var inob:ArrayList<rcomment>
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int = 4
        lateinit var bw: BufferedWriter
        lateinit var ois: ObjectInputStream
        try{
            socket = Socket("192.168.0.3",9500)
            ins = socket.getInputStream()
            os = socket.getOutputStream()
            os.write(data)
            os.flush()
            bw = BufferedWriter(OutputStreamWriter(os))
            bw.write(base.recipe_code+"\n")
            bw.flush()
            var num = ins.read()
            println(num)
            if (num > 0){
                var inp: rcomment
                try{
                    for (i in 0..num-1){
                        println("5레시피 기본정보체크 1")
                        ois = ObjectInputStream(socket.getInputStream())
                        println("5레시피 기본정보체크 2")
                        inp = ois.readObject() as rcomment
                        println("5레시피 기본정보체크 3")
                        arr.add(inp)
                        println("5레시피 기본정보체크 4")
                    }
                }catch (e:EOFException){

                }
            }

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

    suspend fun ChatCoroutin(chat: rcomment) = withContext(Dispatchers.IO){
        var check = 0
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int =5
        lateinit var bw: BufferedWriter
        lateinit var ois: ObjectInputStream
        lateinit var oos: ObjectOutputStream
        try{
            socket = Socket("192.168.0.3",9500)
            ins = socket.getInputStream()
            os = socket.getOutputStream()
            os.write(data)
            os.flush()

            try{
                oos = ObjectOutputStream(socket.getOutputStream())
                oos.writeObject(chat)
                oos.flush()
            }catch (e:Exception){

            }

            check = ins.read()
            println(check)
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            try{
                if (ois != null){
                    ois.close()
                }
                if (oos !=null){
                    oos.close()
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
        check
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //var base: base = intent.getSerializableExtra("baim") as base
        val base_result = intent.getSerializableExtra("base") as base

        val SP = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        val id = SP.getString("ID", null)


        binding.recipeName.text = base_result.recipe_name
        binding.recipeSummary.text = base_result.recipe_summary
        binding.cookingTime.text = base_result.cooking_time

        if(base_result.check_video == false) binding.cookingVideo.visibility = View.GONE

        CoroutineScope(Dispatchers.Main).launch {
            val imresult = BaseCoroutine(base_result)
            if(imresult.get(0).byteimage != null && imresult.get(0).byteimage.size>0){
                var bitmap: Bitmap = BitmapFactory.decodeByteArray(imresult.get(0).byteimage, 0, imresult.get(0).byteimage.size)
                binding.recipeImage.setImageBitmap(bitmap)
            }


            val ing_result = IngCoroutine(base_result)
            val INadapter = IngAdapter(ing_result)
            binding.MList.adapter = INadapter
            val INlm = LinearLayoutManager(this@RecipeInfo)
            binding.MList.layoutManager = INlm
            binding.MList.setHasFixedSize(true)

            val order_result = OrderCoroutine(base_result)
            val ODadapter = InfoAdapter(order_result)
            val ODlm = LinearLayoutManager(this@RecipeInfo)
            binding.orderList.layoutManager = ODlm
            binding.orderList.setHasFixedSize(true)
            binding.orderList.adapter = ODadapter

            val rc_result = RcomCoroutin(base_result)
            val RCadapter = RchatAdapter(rc_result)
            binding.chatcycle.adapter = RCadapter
            val RClm = LinearLayoutManager(this@RecipeInfo)
            binding.chatcycle.layoutManager = RClm
            binding.chatcycle.setHasFixedSize(true)


            println(base_result.check_video)
            if (base_result.check_video == true){
                var barr: ByteArray? = VideoCoroutine(base_result)
                if (barr != null) {
                    var file = File.createTempFile(base_result.recipe_code, ".mp4")
                    var fout: FileOutputStream = FileOutputStream(file)
                    fout.write(barr)
                    fout.flush()
                    println(cacheDir)
                    var uri: Uri = Uri.fromFile(file)
                    binding.cookingVideo.visibility = View.VISIBLE
                    binding.cookingVideo.setMediaController(MediaController(this@RecipeInfo));
                    binding.cookingVideo.setVideoURI(uri)
                    file.deleteOnExit()
                }
            }



        }
        binding.recipechatB.setOnClickListener {
            var result = 0
            if (id == null){
                Toast.makeText(applicationContext,"로그인을 해 주세요",Toast.LENGTH_LONG).show()
            }else{
                if (binding.recipechat.text.toString().length==0){
                    Toast.makeText(applicationContext,"댓글을 입력하세요",Toast.LENGTH_LONG).show()
                }else{
                    CoroutineScope(Dispatchers.Main).launch {
                        val cid = SP.getString("ID", null)
                        val dateform = SimpleDateFormat("yyyyMMddhhmmss")
                        val curr = System.currentTimeMillis()
                        val date = Date(curr)
                        val time = dateform.format(date)
                        val cstar = binding.rate.rating
                        val chat = binding.recipechat.text.toString()
                        val comment:rcomment = rcomment(base_result.recipe_code, cid.toString(), dateform.parse(time), cstar, chat)
                        result = ChatCoroutin(comment)
                        println("체체크"+result)
                    }
                    if (result==1){
                        val refresh = Intent(applicationContext, RecipeInfo::class.java)
                        finish()
                        startActivity(refresh)
                    }else{
                        Toast.makeText(applicationContext,"레시피 댓글 입력을 실패",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}