package com.example.test_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var number: Byte = 5
    private var number1: Short = 32
    private var number2: Int = 0
    private var number3: Long = 0
    private var numberF: Float = 0.0f
    private var numberD: Double = 0.0
    private var text: String = "asdfasdf"
    private var ch: Char = '5'

    private var tvText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvText = findViewById(R.id.tvText)
        tvText?.setText(number.toString())

        Thread{
            while (number2 < 5){
                Thread.sleep(2000)
                number2++
            }

        }
    }

}