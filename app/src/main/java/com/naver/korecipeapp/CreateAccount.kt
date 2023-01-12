package com.naver.korecipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.naver.korecipeapp.Object.rcomment
import com.naver.korecipeapp.Object.user
import com.naver.korecipeapp.databinding.ActivityCreateAccountBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.net.Socket

class CreateAccount : AppCompatActivity() {
    private lateinit var binding:ActivityCreateAccountBinding

    suspend fun IdCheckCoroutine(id:String) = withContext(Dispatchers.IO){
        var check:Int=0
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        lateinit var br: BufferedReader
        lateinit var bw: BufferedWriter
        try{
            val data: Int =9
            socket = Socket("192.168.0.3",9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os!!.write(data)
            os!!.flush()

            bw = BufferedWriter(OutputStreamWriter(os))
            bw.write(id+"\n")
            bw.flush()

            br = BufferedReader(InputStreamReader(ins))
            check = Integer.parseInt(br.readLine())
            println("테스트"+check)
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            try{
                if (br != null){
                    br.close()
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
        check
    }

    suspend fun PnumCheckCoroutine(pnum:String) = withContext(Dispatchers.IO){
        var check = 0
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        lateinit var br: BufferedReader
        lateinit var bw: BufferedWriter
        val data: Int =10
        try{
            socket = Socket("192.168.0.3",9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os!!.write(data)
            os!!.flush()

            bw = BufferedWriter(OutputStreamWriter(os))
            bw.write(pnum+"\n")
            bw.flush()

            br = BufferedReader(InputStreamReader(ins))
            check = Integer.parseInt(br.readLine())
            println("테스트"+check)
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            try{
                if (br != null){
                    br.close()
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
        check
    }

    suspend fun createAccountCoroutine(user:user) = withContext(Dispatchers.IO){
        var check = 0
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int =11
        lateinit var oos: ObjectOutputStream
        try{
            socket = Socket("192.168.0.3",9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os!!.write(data)
            os!!.flush()


            oos = ObjectOutputStream(os)
            oos.writeObject(user)
            oos.flush()

            check = ins.read()
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            try{
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
        check
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var idcheck = 0
        var pnumcheck = 0


        binding.insetId.setOnClickListener {
            idcheck = 0
        }

        binding.insetpnum.setOnClickListener {
            pnumcheck = 0
        }

        binding.idcheck.setOnClickListener {
            var check:Int = 3
            if (binding.insetId.text.toString().isEmpty()){
                Toast.makeText(applicationContext,"내용이 비어있습니다",Toast.LENGTH_LONG).show()
            }
            else{
                CoroutineScope(Dispatchers.Main).launch {
                    check = IdCheckCoroutine(binding.insetId.text.toString())
                    println(check)
                    if (check ==1) {
                        println("체크는 1이야")
                        Toast.makeText(applicationContext,"사용가능한 ID입니다.",Toast.LENGTH_LONG).show()
                        idcheck=1
                    }else if (check==0){
                        println("체크는 0이야")
                        Toast.makeText(applicationContext,"ID가 중복되었습니다.",Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        binding.pnumcheck.setOnClickListener {
            var check = 3
            if (binding.insetpnum.text.toString().isEmpty() ){
                Toast.makeText(applicationContext,"내용이 비어있습니다",Toast.LENGTH_LONG).show()
            }
            else{
                CoroutineScope(Dispatchers.Main).launch {
                    check = PnumCheckCoroutine(binding.insetpnum.text.toString())
                    if (check ==1) {
                        println("체크는 1이야")
                        Toast.makeText(applicationContext,"사용가능한 전화번호 입니다.",Toast.LENGTH_LONG).show()
                        pnumcheck=1
                    }else if (check == 0){
                        println("체크는 0이야")
                        Toast.makeText(applicationContext,"전화번호가 중복되었습니다.",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.createB.setOnClickListener {
            if (binding.insetId.text.length == 0 || binding.insetname.text.length == 0 || binding.insetpw.text.length == 0 || binding.insetpnum.text.length == 0){
                Toast.makeText(applicationContext,"입력창이 비어 있습니다.",Toast.LENGTH_LONG)
            }else{
                if (idcheck == 0 || pnumcheck == 0){
                    Toast.makeText(applicationContext,"중복체크를 해야 합니다.",Toast.LENGTH_LONG)
                }else{
                    //유저객체 생성
                    val uname= binding.insetname.text.toString()
                    val uid= binding.insetId.text.toString()
                    val upw= binding.insetpw.text.toString()
                    val upnum= binding.insetpnum.text.toString()
                    var userOb:user = user(uid, upw, uname, upnum, 0)
                    CoroutineScope(Dispatchers.Main).launch {
                        val result = createAccountCoroutine(userOb)
                        if (result == 1){
                            val intent = Intent(applicationContext, MainMenu::class.java)
                            Toast.makeText(applicationContext,"계정생성을 성공 하였습니다.",Toast.LENGTH_LONG)
                            finish()
                            startActivity(intent)
                        }else{
                            Toast.makeText(applicationContext,"계정생성을 실패 하였습니다.",Toast.LENGTH_LONG)
                        }
                    }
                }
            }
        }
    }
}