package com.example.login_app.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.login_app.R
import com.example.login_app.api.service.Result
import com.google.android.material.tabs.TabLayout

class MenuActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    private var listTests:ArrayList<String>?=null
    private var listResults:ArrayList<Result>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        val displayname = intent.getStringExtra("displayname")

        updateUiWithUser(displayname)

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

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

    private fun updateUiWithUser(displayname: String?) {
        val welcome = getString(R.string.welcome)
        // TODO : initiate successful logged in experience
        Toast.makeText(applicationContext, "$welcome $displayname", Toast.LENGTH_SHORT).show()
    }

    fun getTestList(): ArrayList<String>? {

        return listTests
    }

    fun getResults(): ArrayList<Result>?{
        return listResults
    }
}