package com.example.login_app.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.login_app.R
import com.example.login_app.ui.login.LoginActivity
import com.google.android.material.tabs.TabLayout

class MenuActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(findViewById(R.id.my_toolbar))
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        val displayname = intent.getStringExtra("displayname")

        updateUiWithUser(displayname)

        tabLayout = findViewById<TabLayout>(R.id.tabs)
        viewPager = findViewById<ViewPager>(R.id.viewpager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Tests"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Results"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

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
        Toast.makeText(applicationContext, "$welcome $displayname", Toast.LENGTH_SHORT).show()
    }

}