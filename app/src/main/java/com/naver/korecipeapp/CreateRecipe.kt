package com.naver.korecipeapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.korecipeapp.Adapter.CreaetROAdapter
import com.naver.korecipeapp.Adapter.CreateRAdapter
import com.naver.korecipeapp.Object.*
import com.naver.korecipeapp.databinding.ActivityCreateRecipeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*


class CreateRecipe : AppCompatActivity() {
    private lateinit var binding: ActivityCreateRecipeBinding

    lateinit var UrI:Uri
    //var UrI:Uri? =null
    lateinit var bytearr:ByteArray

    lateinit var bitmap:Bitmap
    lateinit var baseimarr:ByteArray

    var ing_arr:ArrayList<ingredients> = ArrayList<ingredients>()
    var order_arr:ArrayList<OrderIm> = ArrayList<OrderIm>()

    val getFromAlbumResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if (result.resultCode == Activity.RESULT_OK){
            val uri = result.data?.data
            if (uri != null){
                println(uri.path)
                bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                binding.base.setImageBitmap(bitmap)
                var baseimage:Bitmap = bitmap
                var stream:ByteArrayOutputStream = ByteArrayOutputStream()
                baseimage.compress(Bitmap.CompressFormat.PNG, 100, stream)
                baseimarr = stream.toByteArray()
            }
        }
    }

    val getFromAlbumResultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val uri = result.data?.data
            if (uri != null){
                println(uri.path)
                bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                binding.orderim.setImageBitmap(bitmap)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val getFromAlbumResultVideoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val uri = result.data?.data
            if (uri != null){
                binding.recipevideo.setMediaController(MediaController(this))
                binding.recipevideo.setVideoURI(uri)
                UrI = uri
                println("우리 패스"+ UrI!!.path)
                var file:File = File(UrI!!.path)
                println("파일 패스"+file.absolutePath)
            }
        }
    }

    suspend fun RecipeAdd1(basE: BaseIm, ingarr: ArrayList<ingredients>, orderarr:ArrayList<OrderIm>) =
        withContext(Dispatchers.IO){
            lateinit var socket: Socket
            lateinit var ins: InputStream
            lateinit var os: OutputStream
            val data: Int = 15
            lateinit var ois: ObjectInputStream
            lateinit var oos: ObjectOutputStream
            var result = 0
            try{
                socket = Socket("192.168.0.3",9500)
                ins = socket!!.getInputStream()
                os = socket!!.getOutputStream()
                os.write(data)
                os.flush()

                try{
                    println("기본 체크 1")
                    oos = ObjectOutputStream(socket.getOutputStream())
                    println("기본 체크 2")
                    oos.writeObject(basE)
                    println("기본 체크 3")
                }catch (e: Exception){

                }

                os.write(ingarr.size)
                os.flush()
                try{
                    for(i in 0..ingarr.size-1){
                        println("재료 체크 1")
                        oos = ObjectOutputStream(socket.getOutputStream())
                        println("재료 체크 2")
                        oos.writeObject(ingarr.get(i))
                        println("재료 체크 3")
                    }
                }catch (e: Exception){

                }

                os.write(orderarr.size)
                os.flush()
                try{
                    for(i in 0..orderarr.size-1){
                        println("주문 체크 1")
                        oos = ObjectOutputStream(socket.getOutputStream())
                        println("주문 체크 2")
                        oos.writeObject(orderarr.get(i))
                        println("주문 체크 3")
                    }
                }catch (e:Exception){

                }

                result = ins.read()
            }catch (e:Exception){
                e.printStackTrace()
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
                }catch(e: Exception){
                    e.printStackTrace()
                }
            }
            result
    }

    suspend fun RecipeAdd2(
        basE: BaseIm, ingarr: ArrayList<ingredients>, orderarr:ArrayList<OrderIm>,
        bytearrs: ByteArray?
    ) =
        withContext(Dispatchers.IO){
            lateinit var socket: Socket
            lateinit var ins: InputStream
            lateinit var os: OutputStream
            val data: Int = 25
            lateinit var ois: ObjectInputStream
            lateinit var oos: ObjectOutputStream
            var result = 0
            try{
                socket = Socket("192.168.0.3",9500)
                ins = socket!!.getInputStream()
                os = socket!!.getOutputStream()
                os.write(data)
                os.flush()

                oos = ObjectOutputStream(socket.getOutputStream())
                oos.writeObject(basE)

                os.write(ingarr.size)
                println(ingarr.size)
                os.flush()
                try{
                    for(i in 0..ingarr.size-1){
                        println("25재료 체크 1")
                        oos = ObjectOutputStream(socket.getOutputStream())
                        println("25재료 체크 2")
                        oos.writeObject(ingarr.get(i))
                        println("25재료 체크 3")
                    }
                }catch (e:Exception){

                }

                os.write(orderarr.size)
                println(orderarr.size)
                os.flush()
                try{
                    for(i in 0..orderarr.size-1){
                        println("25주문 체크 1")
                        oos = ObjectOutputStream(socket.getOutputStream())
                        println("25주문 체크 2")
                        oos.writeObject(orderarr.get(i))
                        println("25주문 체크 3")
                    }
                }catch (e: Exception){

                }

                try{
                    println("25영상 체크 1")
                    oos = ObjectOutputStream(socket.getOutputStream())
                    println("25영상 체크 2")
                    oos.writeObject(bytearrs)
                    println("25영상 체크 3")
                }catch (e: Exception){

                }

                result = ins.read()

            }catch (e:Exception){
                e.printStackTrace()
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
                }catch(e: Exception){
                    e.printStackTrace()
                }
            }
            result
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = com.naver.korecipeapp.databinding.ActivityCreateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var adapter = CreateRAdapter(ing_arr)
        binding.inglist.adapter = adapter
        val CRI = LinearLayoutManager(this@CreateRecipe)
        binding.inglist.layoutManager = CRI
        binding.inglist.setHasFixedSize(true)


        var adapterOrder = CreaetROAdapter(order_arr)
        binding.orlist.adapter = adapterOrder
        val CRO = LinearLayoutManager(this@CreateRecipe)
        binding.orlist.layoutManager = CRO
        binding.orlist.setHasFixedSize(true)



        //아이디 받기
        val SP = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        var id = SP.getString("ID", null)
        val dateform = SimpleDateFormat("yyyyMMddhhmmss")
        val curr = System.currentTimeMillis()
        val date = Date(curr)
        val time = dateform.format(date)
        val code = "r"+id+time
        println(code)
        binding.base.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            getFromAlbumResultLauncher.launch(intent)

        }

        binding.ingb.setOnClickListener {
            if(binding.name.text.length == 0 || binding.gram.text.length == 0){
                Toast.makeText(applicationContext, "비어있음", Toast.LENGTH_LONG).show()
            }else{
                val name = binding.name.text.toString()
                val gram = binding.gram.text.toString()
                val ing = ingredients(code, name, gram)
                ing_arr.add(ing)
                adapter.notifyDataSetChanged()

                println("재료" + name)

                binding.name.setText("")
                binding.gram.setText("")
            }
        }
        binding.orderim.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            getFromAlbumResultLauncher2.launch(intent)
        }
        binding.orderb.setOnClickListener {
            if (binding.order.text.length == 0){
                Toast.makeText(applicationContext, "비어있음", Toast.LENGTH_LONG).show()
            }else{
                var ordertext = binding.order.text.toString()
                var orderimage: Bitmap? = null
                if (binding.orderim.drawable != null){
                    orderimage = binding.orderim.drawable.toBitmap()
                }else{

                }
                var orderIim: OrderIm
                println("주문" + ordertext)
                if(orderimage == null){
                    val orderr = cooking(code, order_arr.size, ordertext, null)
                    orderIim = OrderIm(orderr, null)
                }else{
                    var stream:ByteArrayOutputStream = ByteArrayOutputStream()
                    orderimage.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    var byarr: ByteArray = stream.toByteArray()
                    val orderr = cooking(code, order_arr.size, ordertext, "")
                    orderIim = OrderIm(orderr, byarr)
                }

                order_arr.add(orderIim)
                adapterOrder.notifyDataSetChanged()

                binding.order.setText("")
                binding.orderim.setImageResource(0)
            }
        }
        var ch:Int = 0

        binding.videob.setOnClickListener {
            var intent:Intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Video.Media.CONTENT_TYPE
            startActivityForResult(intent,1)
            //getFromAlbumResultVideoLauncher.launch(intent)
            ch = 1
        }
        binding.addb.setOnClickListener {
            var str_t =binding.title.text.toString()
            var str_s=binding.summary.text.toString()
            var str_k = binding.kind.text.toString()
            var str_ti = binding.time.text.toString()
            if (str_t == null || str_s == null || str_k == null || str_ti == null){
                Toast.makeText(applicationContext, "비어있음", Toast.LENGTH_LONG).show()
            }else{
                if (ch == 0){
                    var basE: base = base(code, str_t, str_s, id.toString(), str_k, str_ti, 0, "", false)
                    var baseIm:BaseIm = BaseIm(basE, baseimarr)
                    CoroutineScope(Dispatchers.Main).launch {
                        var result = RecipeAdd1(baseIm, ing_arr, order_arr)
                        println(result)
                        if (result == 1){
                            Toast.makeText(applicationContext, "레시피 추가 성공(영상X)", Toast.LENGTH_LONG).show()
                            val intent = Intent(applicationContext, MainMenu::class.java)
                            finish()
                            startActivity(intent)
                        }
                    }
                }else{
                    var basE: base = base(code, str_t, str_s, id.toString(), str_k, str_ti, 0, "", true)
                    var baseIm:BaseIm = BaseIm(basE, baseimarr)

                    CoroutineScope(Dispatchers.Main).launch {
                        var result = RecipeAdd2(baseIm, ing_arr, order_arr, bytearr)
                        println(result)
                        if (result == 1){
                            Toast.makeText(applicationContext, "레시피 추가 성공(영상O)", Toast.LENGTH_LONG).show()
                            val intent = Intent(applicationContext, MainMenu::class.java)
                            finish()
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode== Activity.RESULT_OK){
            if (requestCode==1){
                val uri = data?.data
                println(uri)
                val isn = application.contentResolver
                if (uri != null) {
                    var inst: InputStream? = isn.openInputStream(uri)
                    bytearr = inst?.readBytes()!!
                    println(bytearr)
                }else{
                    println("뭐야")
                }
            }
        }
    }

}