package com.example.login_app.ui.menu

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.example.login_app.R
import com.example.login_app.api.service.MySubject
import com.example.login_app.ui.login.LoginViewModel
import com.google.android.material.tabs.TabLayout
import javax.security.auth.Subject

class MenuActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var subject = MySubject()
    var username:String? = null

    private lateinit var testViewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        val groupId = intent.getStringExtra("groupId")

        tabLayout = findViewById<TabLayout>(R.id.tabs)
        viewPager = findViewById<ViewPager>(R.id.viewpager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Tests"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Results"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        testViewModel.subjectResult.observe(this@MenuActivity, Observer {
            val subjectResult = it ?: return@Observer

            if (subjectResult.error != null) {
               //TODO нет доступного предмета
            }
            if (subjectResult.success != null) {
               //TODO установить имя предмета и отправить запрос на получение топиков
            }
            setResult(RESULT_OK)

        })

        val adapter = PagerAdapter(
            this,
            supportFragmentManager,
            tabLayout!!.tabCount
        )
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


    }

    override fun onResume() {
        super.onResume()
        updateUiWithUser(username)
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

}