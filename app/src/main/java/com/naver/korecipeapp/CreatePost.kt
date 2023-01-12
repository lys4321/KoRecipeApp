package com.naver.korecipeapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.naver.korecipeapp.Object.post
import com.naver.korecipeapp.databinding.ActivityCreatePostBinding
import com.naver.korecipeapp.databinding.ActivityCreateRecipeBinding
import kotlinx.coroutines.*
import java.io.*
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CreatePost : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePostBinding

    lateinit var bytearr:ByteArray

    lateinit var bitmap:Bitmap

    var byteArrArr = ArrayList<ByteArray>()

    var imcheck = 0
    var vicheck = 0

    val getFromAlbumResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val uri = result.data?.data
            println(uri)
            println("씨이이이이이이이이ㅣ잉이이이이이ㅣㅇ")
            val isn = application.contentResolver
            println("씨이이이이이이이이ㅣ잉이이이이이ㅣㅇ2")
            if (uri != null){
                println("씨이이이이이이이이ㅣ잉이이이이이ㅣㅇ3")
                var inst: InputStream? = isn.openInputStream(uri)
                bytearr = inst?.readBytes()!!
                println(bytearr)
                vicheck = 1
            }
        }
    }

    val getFromAlbumResultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val uri = result.data?.data
            if (uri != null){
                println(uri.path)
                val num = byteArrArr.size
                println(byteArrArr)
                when(num){
                    0 -> {
                        bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        binding.imone.setImageBitmap(bitmap)
                        var baseimage:Bitmap = bitmap
                        var stream:ByteArrayOutputStream = ByteArrayOutputStream()
                        baseimage.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        byteArrArr.add(stream.toByteArray())
                    }
                    1 -> {
                        bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        binding.imtwo.setImageBitmap(bitmap)
                        var baseimage:Bitmap = bitmap
                        var stream:ByteArrayOutputStream = ByteArrayOutputStream()
                        baseimage.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        byteArrArr.add(stream.toByteArray())
                    }
                    2 -> {
                        bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        binding.imthree.setImageBitmap(bitmap)
                        var baseimage:Bitmap = bitmap
                        var stream:ByteArrayOutputStream = ByteArrayOutputStream()
                        baseimage.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        byteArrArr.add(stream.toByteArray())
                    }
                    3 -> {
                        bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        binding.imfour.setImageBitmap(bitmap)
                        var baseimage:Bitmap = bitmap
                        var stream:ByteArrayOutputStream = ByteArrayOutputStream()
                        baseimage.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        byteArrArr.add(stream.toByteArray())
                    }
                }
            }
        }
    }


    suspend fun CreatePostCoroutine(post:post, barrarr:ArrayList<ByteArray>, vidarr:ByteArray) = withContext(Dispatchers.IO){
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int = 16
        lateinit var oos: ObjectOutputStream
        var checkString:Int = 0
        try {
            socket = Socket("192.168.0.3", 9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os.write(data)
            os.flush()

            println("게시글 1")
            oos = ObjectOutputStream(socket.getOutputStream())
            println("게시글 2")
            oos.writeObject(post)
            println("게시글 3")

            os.write(barrarr.size)
            os.flush()
            println(barrarr.size)
            try{
                for (i in 0..barrarr.size-1){
                    println("이미지 1")
                    oos = ObjectOutputStream(socket.getOutputStream())
                    println("이미지 2")
                    oos.writeObject(barrarr.get(i))
                    println("이미지 3")
                }
            }catch (e:Exception){

            }

            try{
                println("영상 1")
                oos = ObjectOutputStream(socket.getOutputStream())
                println("영상 2")
                oos.writeObject(vidarr)
                println("영상 3")
            }catch (e:Exception){

            }
            checkString = ins.read()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        checkString
    }

    suspend fun CreatePostCoroutine2(post:post, vidarr:ByteArray) = withContext(Dispatchers.IO){
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int =26
        lateinit var oos: ObjectOutputStream
        var checkString:Int = 0
        try {
            socket = Socket("192.168.0.3", 9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os.write(data)
            os.flush()

            println("게시글 1")
            oos = ObjectOutputStream(socket.getOutputStream())
            println("게시글 2")
            oos.writeObject(post)
            println("게시글 3")


            try{
                println("영상 1")
                oos = ObjectOutputStream(socket.getOutputStream())
                println("영상 2")
                oos.writeObject(vidarr)
                println("영상 3")
            }catch (e:Exception){

            }
            checkString = ins.read()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        checkString
    }

    suspend fun CreatePostCoroutine3(post:post, barrarr:ArrayList<ByteArray>) = withContext(Dispatchers.IO){
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int =27
        lateinit var oos: ObjectOutputStream
        var checkString:Int = 0
        try {
            socket = Socket("192.168.0.3", 9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os.write(data)
            os.flush()

            println("게시글 1")
            oos = ObjectOutputStream(socket.getOutputStream())
            println("게시글 2")
            oos.writeObject(post)
            println("게시글 3")

            os.write(barrarr.size)
            os.flush()
            println(barrarr.size)
            try{
                for (i in 0..barrarr.size-1){
                    println("이미지 1")
                    oos = ObjectOutputStream(socket.getOutputStream())
                    println("이미지 2")
                    oos.writeObject(barrarr.get(i))
                    println("이미지 3")
                }
            }catch (e:Exception){

            }

            checkString = ins.read()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        checkString
    }

    suspend fun CreatePostCoroutine4(post:post) = withContext(Dispatchers.IO){
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int =28
        lateinit var oos: ObjectOutputStream
        var checkString:Int = 0
        try {
            socket = Socket("192.168.0.3", 9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os.write(data)
            os.flush()

            println("게시글 1")
            oos = ObjectOutputStream(socket.getOutputStream())
            println("게시글 2")
            oos.writeObject(post)
            println("게시글 3")
            checkString = ins.read()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        checkString
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePostBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.videoaddB.setOnClickListener {
            var intent:Intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Video.Media.CONTENT_TYPE
            getFromAlbumResultLauncher.launch(intent)
            //startActivityForResult(intent,1)
            vicheck = 1
        }
        binding.imaddB.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            getFromAlbumResultLauncher2.launch(intent)
            imcheck = 1
        }
        binding.add.setOnClickListener {
            var form = SimpleDateFormat("yyyymmdd")
            var date = form.format(System.currentTimeMillis()).toString()
            var dada: Date = SimpleDateFormat("yyyymmdd").parse(date)
            form = SimpleDateFormat("yyyymmddHHmmss")
            var date2 = form.format(System.currentTimeMillis()).toString()
            val SP = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
            val id = SP.getString("ID", null)

            println("버어어엉튼 1------------------------")

            if (binding.title.text.isEmpty() || binding.post.text.isEmpty()){
                Toast.makeText(applicationContext, "입력 칸이 비어있음", Toast.LENGTH_LONG).show()
            }else{
                println("버어어엉튼 2------------------------")
                var post = post(
                    "p" + id + date2,
                    binding.title.text.toString(),
                    id.toString(),
                    dada,
                    binding.post.text.toString(),
                    0,
                    true,
                    true
                )
                println("버어어엉튼 3------------------------")
                if (imcheck == 1 && vicheck == 1) {
                    post.check_image = true
                    post.check_video = true
                }else if (imcheck == 1 && vicheck == 0) {
                    post.check_image = true
                    post.check_video = false
                }
                else if (imcheck == 0 && vicheck == 1) {
                    post.check_image = false
                    post.check_video = true
                }
                else if (imcheck == 0 && vicheck == 0) {
                    post.check_image = false
                    post.check_video = false
                }

                println("버어어엉튼 4------------------------")
                println("post.check_image : "+post.check_image)
                println("post.check_image : "+post.check_image)


                if (imcheck == 1 && vicheck == 1){//사진과 비디오 존재 시
                    CoroutineScope(Dispatchers.Main).launch{
                        val result = CreatePostCoroutine(post, byteArrArr, bytearr)
                        println(result)
                        if (result == 1){
                            Toast.makeText(applicationContext, "게시글 추가 성공", Toast.LENGTH_LONG).show()
                            val intent = Intent(applicationContext, PostBoard::class.java)
                            finish()
                            startActivity(intent)
                        }
                    }
                }else if (imcheck == 1 && vicheck == 0){// 사진만 존재 시
                    CoroutineScope(Dispatchers.Main).launch{
                        val result = CreatePostCoroutine3(post, byteArrArr)
                        println(result)
                        if (result == 1){
                            Toast.makeText(applicationContext, "게시글 추가 성공", Toast.LENGTH_LONG).show()
                            val intent = Intent(applicationContext, PostBoard::class.java)
                            finish()
                            startActivity(intent)
                        }
                    }

                }else if (imcheck == 0 && vicheck == 1){// 영상만 존재 시
                    CoroutineScope(Dispatchers.Main).launch{
                        val result = CreatePostCoroutine2(post, bytearr)
                        println(result)
                        if (result == 1){
                            Toast.makeText(applicationContext, "게시글 추가 성공", Toast.LENGTH_LONG).show()
                            val intent = Intent(applicationContext, PostBoard::class.java)
                            finish()
                            startActivity(intent)
                        }
                    }
                }else{//사진, 영상 없을 시
                    CoroutineScope(Dispatchers.Main).launch{
                        val result = CreatePostCoroutine4(post)
                        println(result)
                        if (result == 1){
                            Toast.makeText(applicationContext, "게시글 추가 성공", Toast.LENGTH_LONG).show()
                            val intent = Intent(applicationContext, PostBoard::class.java)
                            finish()
                            startActivity(intent)
                        }
                    }
                }


            }
        }
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK){
            if (requestCode==1){
                println("아러ㅜㄴ이ㅏ럼니아어ㅏㅐ렁ㄴ먀ㅐㅏ런ㅇㅁ")
                val uri = data?.data
                println(uri)
                println("씨이이이이이이이이ㅣ잉이이이이이ㅣㅇ")
                val isn = application.contentResolver
                println("씨이이이이이이이이ㅣ잉이이이이이ㅣㅇ2")
                if (uri != null) {
                    println("씨이이이이이이이이ㅣ잉이이이이이ㅣㅇ3")
                    var inst: InputStream? = isn.openInputStream(uri)
                    bytearr = inst?.readBytes()!!
                    println(bytearr)
                    vicheck = 1
                }else{
                    println("뭐야")
                }
            }

        }
    }

     */
}