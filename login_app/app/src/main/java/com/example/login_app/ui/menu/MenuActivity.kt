package com.example.login_app.ui.menu

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
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.login_app.R

import com.example.login_app.api.service.Result

import com.example.login_app.api.service.MySubject
import com.example.login_app.api.service.Topic
import com.example.login_app.ui.login.LoginActivity

import com.example.login_app.ui.login.TestViewModelFactory
import com.google.android.material.tabs.TabLayout

class MenuActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    private var subject :MySubject? = null
    var username:String? = null

    private lateinit var testViewModel: TestViewModel

    private var listTopics:ArrayList<Topic>?=null
    private var listResults:ArrayList<Result>?=null // отправить запрос на получение id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        updateUiWithUser(username)

        //TODO проверить Toast
//        if (getSupportActionBar() != null) {
//            getSupportActionBar()?.setDisplayHomeAsUpEnabled(false);
//        }
       // supportActionBar?.setDisplayHomeAsUpEnabled(true)

        username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        val groupId = intent.getIntExtra("groupId", 0)

        updateUiWithUser(username)

        tabLayout = findViewById<TabLayout>(R.id.tabs)
        viewPager = findViewById<ViewPager>(R.id.viewpager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Tests"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Results"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        testViewModel = ViewModelProvider(this, TestViewModelFactory())
                .get(TestViewModel::class.java)

        //здесь отправляется запрос
        testViewModel.getSubject(groupId)

        val adapter = PagerAdapter(
            this,
            supportFragmentManager,
            tabLayout!!.tabCount
        )

        //android.R.layout.simple_spinner_item

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


        testViewModel.subjectResult.observe(this@MenuActivity, Observer {
            val subjectResult = it ?: return@Observer

            if (subjectResult.error != null) {
                //TODO нет доступного предмета
                    subject = MySubject()
                subject!!.setFullName("Нет доступного предмета")
                viewPager!!.adapter?.notifyDataSetChanged()
            }
            if (subjectResult.success != null) {
                //TODO установить имя предмета и отправить запрос на получение топиков

                subject = MySubject()
                subject!!.setId(subjectResult.success.subjectId)
                subject!!.setFullName(subjectResult.success.fullName)
                subject!!.setShName(subjectResult.success.shortName)
                Log.d("Pretty Printed JSON :", "Предмет пришел удачно" + subject!!.getFullName())
                viewPager!!.adapter?.notifyDataSetChanged()
                // второй запрос на получение топиков
                //listTopics?.add(0,"Выбери предмет") добавить дефолтное значение при получении топиков
            }
            setResult(RESULT_OK)

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

    fun getSubject(): MySubject?{
        return subject
    }


    fun getTopicList(): List<Topic>? {
        return listTopics

    }

    fun getResults(): ArrayList<Result>?{
        return listResults
    }
}