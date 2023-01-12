package com.naver.korecipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.naver.korecipeapp.Object.rcomment
import com.naver.korecipeapp.Object.user
import com.naver.korecipeapp.databinding.ActivitySearchAccountBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.net.Socket

class SearchAccount : AppCompatActivity() {
    private lateinit var binding:ActivitySearchAccountBinding

    suspend fun SearchCoroutine(name:String, pnum:String) = withContext(Dispatchers.IO){
        lateinit var info: user
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int =12
        lateinit var bw: BufferedWriter
        lateinit var ois: ObjectInputStream
        try{
            socket = Socket("192.168.0.3",9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os!!.write(data)
            os!!.flush()

            bw = BufferedWriter(OutputStreamWriter(os))
            bw!!.write(name+"\n")
            bw!!.flush()
            bw!!.write(pnum+"\n")
            bw!!.flush()
            ois = ObjectInputStream(ins)
            info = ois!!.readObject() as user
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
            }catch(e: Exception){
                e.printStackTrace()
            }
        }
        info
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchB.setOnClickListener {
            val name = binding.searchName.text.toString()
            val pnum = binding.searchPnum.text.toString()
            if (name.length==0 || pnum.length==0){

            }else{
                CoroutineScope(Dispatchers.Main).launch {
                    val result = SearchCoroutine(name, pnum)
                    if (result.userid.length == 0){
                        Toast.makeText(applicationContext,"존재하지 않는 유저의 정보입니다.",Toast.LENGTH_LONG).show()
                    }else{
                        binding.searchId.text = result.userid
                        binding.searchPw.text = result.userpw
                    }
                }
            }
        }
    }
}