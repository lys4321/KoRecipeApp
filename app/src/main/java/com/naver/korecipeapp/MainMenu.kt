package com.naver.korecipeapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.naver.korecipeapp.Adapter.MainAdapter
import com.naver.korecipeapp.Object.*

import kotlinx.coroutines.*
import java.io.*
import java.lang.Exception
import java.net.Socket

class MainMenu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var R_all:ImageButton
    lateinit var R_rice:ImageButton
    lateinit var R_noodle:ImageButton
    lateinit var R_soup:ImageButton
    lateinit var R_fry:ImageButton
    lateinit var R_salad:ImageButton
    lateinit var R_bread:ImageButton
    lateinit var R_meat:ImageButton
    lateinit var todayTOP:ListView
    lateinit var monthTOP:ListView
    lateinit var button:Button
    lateinit var buttonr:Button
    lateinit var drawer:DrawerLayout
    lateinit var navigation:NavigationView

    lateinit var navigation_id:TextView
    lateinit var navigation_pnum:TextView
    lateinit var navigation_count:TextView

    lateinit var dnbn:Button

    lateinit var SP:SharedPreferences
    lateinit var id:String
    lateinit var pw:String


    var check = 0


    suspend fun MainCoroutine() = withContext(Dispatchers.IO){
        var in_val: ArrayList<BaseIm> = ArrayList<BaseIm>()
        lateinit var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int = 1
        lateinit var ois: ObjectInputStream
        lateinit var inob:ArrayList<BaseIm>
        try{
            socket = Socket("192.168.0.3",9500)

            ins = socket.getInputStream()
            os = socket.getOutputStream()
            os.write(data)
            os.flush()

            var num = ins.read()
            println("이거네"+num)
            var inp: BaseIm
            try{
                for (i in 0..num-1){
                    println(i)
                    ois = ObjectInputStream(socket.getInputStream())
                    println("체크 1")
                    inp = ois.readObject() as BaseIm
                    println("체크 2")
                    in_val.add(inp)
                    println("체크 3")
                    println(in_val.get(i).base.recipe_name)
                    println("체크 4")
                    println("체크 5")
                    println("체크 6")
                }
            }catch (e:EOFException){

            }

            /*
            try{
                in_val = (ois.readObject()) as ArrayList<BaseIm>
            } catch (e: EOFException){
                if (in_val.size == 0) {
                    println("받아오기가 이상하다? 탑3")
                    e.printStackTrace()
                }else{
                    println("받아옴")
                }
            }

             */
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            try{
                if (ois != null){
                    ois.close()
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
        in_val
    }


    suspend fun ADeleteCoroutine(userid:String) = withContext(Dispatchers.IO){
        var result = 0
        lateinit  var socket: Socket
        lateinit var ins: InputStream
        lateinit var os: OutputStream
        val data: Int =14
        lateinit var br: BufferedReader
        lateinit var bw: BufferedWriter
        try{
            socket = Socket("192.168.0.3",9500)
            ins = socket!!.getInputStream()
            os = socket!!.getOutputStream()
            os.write(data)
            os.flush()
            bw = BufferedWriter(OutputStreamWriter(os))
            bw.write(userid+"\n")
            bw.flush()

            br = BufferedReader(InputStreamReader(ins))
            result = Integer.parseInt(br.readLine())

        }catch (e:Exception, ){
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
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
        result
    }

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        SP = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
        id = SP.getString("ID", null).toString()
        pw = SP.getString("PW", null).toString()

        drawer = findViewById(R.id.drawer)
        navigation = findViewById(R.id.navigation)
        R_all = findViewById<ImageButton>(R.id.all)
        R_rice = findViewById<ImageButton>(R.id.rice)
        R_noodle = findViewById<ImageButton>(R.id.noodle)
        R_soup = findViewById<ImageButton>(R.id.soup)
        R_fry = findViewById<ImageButton>(R.id.fry)
        R_salad = findViewById<ImageButton>(R.id.salad)
        R_bread = findViewById<ImageButton>(R.id.bread)
        R_meat = findViewById<ImageButton>(R.id.meat)

        todayTOP = findViewById<ListView>(R.id.todayTOP)
        //monthTOP = findViewById<ListView>(R.id.monthTOP)
        button = findViewById<Button>(R.id.button)
        buttonr = findViewById<Button>(R.id.buttonr)

        dnbn = findViewById(R.id.dnbn)

        dnbn.setOnClickListener {
            drawer.openDrawer(Gravity.LEFT)
        }

        CoroutineScope(Dispatchers.Main).launch {
            val Maco = MainCoroutine()
            val adapter = MainAdapter(applicationContext, Maco)
            todayTOP.adapter = adapter
        }

        R_all.setOnClickListener {
            val intent = Intent(this, RecipeList::class.java)
            intent.putExtra("cartegory", "없음")
            startActivity(intent)
        }
        R_rice.setOnClickListener {
            val intent = Intent(this, RecipeList::class.java)
            intent.putExtra("cartegory", "밥")
            startActivity(intent)
        }
        R_noodle.setOnClickListener {
            val intent = Intent(this, RecipeList::class.java)
            intent.putExtra("cartegory", "만두/면류")
            startActivity(intent)
        }
        R_soup.setOnClickListener {
            val intent = Intent(this, RecipeList::class.java)
            intent.putExtra("cartegory", "찌개/전골/스튜")
            startActivity(intent)
        }
        R_fry.setOnClickListener {
            val intent = Intent(this, RecipeList::class.java)
            intent.putExtra("cartegory", "튀김/커틀릿")
            startActivity(intent)
        }
        R_salad.setOnClickListener {
            val intent = Intent(this, RecipeList::class.java)
            intent.putExtra("cartegory", "나물/생채/샐러드")
            startActivity(intent)
        }
        R_bread.setOnClickListener {
            val intent = Intent(this, RecipeList::class.java)
            intent.putExtra("cartegory", "빵/과자")
            startActivity(intent)
        }
        R_meat.setOnClickListener {
            val intent = Intent(this, RecipeList::class.java)
            intent.putExtra("cartegory", "구이")
            startActivity(intent)
        }

        todayTOP.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            var BI = adapterView.getItemAtPosition(i) as BaseIm
            val select = BI.base
            val byteArray = BI.byteimage
            val intent = Intent(this, RecipeInfo::class.java)
            intent.putExtra("base",select)
            intent.putExtra("image", byteArray)
            startActivity(intent)
        }

        buttonr.setOnClickListener {
            if (id == "null"){
                Toast.makeText(applicationContext,"로그인이 필요합니다.", Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(this, CreateRecipe::class.java)
                startActivity(intent)
            }
        }
        button.setOnClickListener {
            val intent = Intent(this, PostBoard::class.java)
            startActivity(intent)
        }

        navigation.menu.clear()
        //드로우바 클릭 이벤트 처리하기
        if(id != "null"){
            val nav_header_view = navigation.inflateHeaderView(R.layout.user_header)
            navigation_id = nav_header_view.findViewById(R.id.id)
            navigation_id.text = id
            navigation_pnum = nav_header_view.findViewById(R.id.pnum)
            navigation_pnum.text = SP.getString("pnum", "")
            navigation_count = nav_header_view.findViewById(R.id.count)
            navigation_count.text = SP.getInt("recipe_count",0).toString()
            navigation.inflateMenu(R.menu.user_menu)
            navigation.setNavigationItemSelectedListener(this)
        }else{
            navigation.inflateHeaderView(R.layout.notuser_header)
            navigation.inflateMenu(R.menu.notuser_menu)
            navigation.setNavigationItemSelectedListener(this)
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.notuserlogin -> {
                val intent = Intent(this@MainMenu, UserLogin::class.java)
                startActivity(intent)
                true
            }
            R.id.notusercreate -> {
                val intent = Intent(this@MainMenu, CreateAccount::class.java)
                startActivity(intent)
                true
            }
            R.id.notusersearch -> {
                val intent = Intent(this@MainMenu, SearchAccount::class.java)
                startActivity(intent)
                true
            }
            R.id.logout -> {
                val SPE = SP.edit()
                SPE.clear()
                SPE.commit()
                val intent = Intent(this@MainMenu, MainMenu::class.java)
                finish()
                startActivity(intent)
                true
            }
            R.id.delete -> {
                CoroutineScope(Dispatchers.Main).launch {
                    val result = ADeleteCoroutine(id)
                    println(result)
                    if(result == 1){
                        val SPE = SP.edit()
                        SPE.clear()
                        SPE.commit()
                        val intent = Intent(this@MainMenu, MainMenu::class.java)
                        finish()
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(applicationContext,"계정삭제 실패.",Toast.LENGTH_LONG).show()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

