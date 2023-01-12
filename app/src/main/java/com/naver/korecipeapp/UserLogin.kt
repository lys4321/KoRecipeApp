package com.naver.korecipeapp

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.naver.korecipeapp.Object.user
import com.naver.korecipeapp.databinding.ActivityUserLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.lang.Exception
import java.net.Socket

class UserLogin : AppCompatActivity() {
    private lateinit var binding:ActivityUserLoginBinding

    suspend fun LoginCoroutine(id:String, pw:String) = withContext(Dispatchers.IO){
        lateinit var user: user
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream

        val data: Int =8

        lateinit var bw: BufferedWriter

        lateinit var ois: ObjectInputStream
        try{
            socket = Socket("192.168.0.3",9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os!!.write(data)
            os!!.flush()

            bw= BufferedWriter(OutputStreamWriter(os))
            bw.write(id+"\n")
            bw.flush()
            bw.write(pw+"\n")
            bw.flush()

            //USER 객체를 받아와서 이름과 전화번호도 SHare에 저장하도록 한다
            ois = ObjectInputStream(ins)
            user = ois.readObject() as user
            println(user.user_name)
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
        user
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginB.setOnClickListener {
            val id = binding.insetId.text.toString()
            val pw = binding.insetPw.text.toString()
            if (id.length == 0||pw.length ==0){
                Toast.makeText(applicationContext,"로그인 정보가 비어있음",Toast.LENGTH_LONG).show()
            }else{
                CoroutineScope(Dispatchers.Main).launch {
                    val checkin = LoginCoroutine(id, pw)

                    if (checkin.userid != ""){
                        var autoLogin: SharedPreferences = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
                        var loginEditor:SharedPreferences.Editor = autoLogin.edit()
                        loginEditor.putString("ID", checkin.userid)
                        loginEditor.putString("PW", checkin.userpw)
                        loginEditor.putString("name", checkin.user_name)
                        loginEditor.putString("pnum", checkin.user_pnum)
                        loginEditor.putInt("recipe_count", checkin.recipe_count)
                        loginEditor.commit()
                        finish()
                        val intent = Intent(applicationContext, MainMenu::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(applicationContext,"로그인 정보가 없거나 비어있음",Toast.LENGTH_LONG).show()
                    }


                }
            }
        }
    }
}