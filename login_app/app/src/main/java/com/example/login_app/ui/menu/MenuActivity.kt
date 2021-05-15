package com.example.login_app.ui.menu

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.ArrayAdapter
import android.widget.Spinner
import android.util.Log
import android.view.View
import android.widget.ImageView

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.example.login_app.R

import com.example.login_app.api.service.Result

import com.example.login_app.api.service.MySubject
import com.example.login_app.ui.login.LoginViewModel
import com.example.login_app.ui.login.LoginActivity

import com.google.android.material.tabs.TabLayout
import javax.security.auth.Subject

class MenuActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var subject = MySubject()
    var username:String? = null

    private lateinit var testViewModel: TestViewModel

    private var listTests:ArrayList<String>?=null
    private var listResults:ArrayList<Result>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(findViewById(R.id.my_toolbar))


        //TODO проверить Toast
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        val groupId = intent.getStringExtra("groupId")

        updateUiWithUser(username)

        tabLayout = findViewById<TabLayout>(R.id.tabs)
        viewPager = findViewById<ViewPager>(R.id.viewpager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Tests"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Results"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL


        // Todo: Здесь запрос на список тестов и сохранение в переменную


        listTests = ArrayList<String>(arrayOf("x", "y", "z").asList())

        // Todo: Строчка ниже нужна, так как это дефолтное значение
        listTests?.add(0,"Выбери предмет")


        // Todo: А здесь запрос на список результатов
        val res1 = Result()
        res1.id = 1
        res1.mark = 123
        res1.topicId = 11111111
        res1.rightAnswNum = 999

        val res2 = Result()
        res2.id = 2
        res2.mark = 123123
        res2.topicId = 222222222
        res2.rightAnswNum = 8888

        listResults = ArrayList<Result>(arrayOf(res1, res2).asList())

//        testViewModel.subjectResult.observe(this@MenuActivity, Observer {
//            val subjectResult = it ?: return@Observer
//
//            if (subjectResult.error != null) {
//               //TODO нет доступного предмета
//            }
//            if (subjectResult.success != null) {
//               //TODO установить имя предмета и отправить запрос на получение топиков
//            }
//            setResult(RESULT_OK)
//
//        })


        val adapter = PagerAdapter(
            this,
            supportFragmentManager,
            tabLayout!!.tabCount
        )
        android.R.layout.simple_spinner_item
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position

                //Toast.makeText(this, "1", Toast.LENGTH_LONG).show()

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


        val image_button = findViewById<ImageView>(R.id.imageButton3)
        image_button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                // TODO : add logout request
                val intent = Intent(view?.context, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        })

    }


    private fun updateUiWithUser(displayname: String?) {
        val welcome = getString(R.string.welcome)
        // TODO : initiate successful logged in experience
        Log.d("Pretty Printed JSON :", "Здравствуй!" + displayname)
        Toast.makeText(applicationContext, "$welcome $displayname", Toast.LENGTH_LONG).show()
    }

    private fun getSubject(){
        //TODO запрос на получение предмета
          if (subject == null ){
              //TODO выводим на экран "нет досутпных предметов"
          }
    }

    fun getTestList(): ArrayList<String>? {

        return listTests
    }

    fun getResults(): ArrayList<Result>?{
        return listResults
    }
}