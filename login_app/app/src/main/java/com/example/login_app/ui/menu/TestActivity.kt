package com.example.login_app.ui.menu

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.login_app.R
import com.example.login_app.api.service.Result
import com.example.login_app.api.service.Task
import java.time.LocalDateTime
import java.util.*

class TestActivity : AppCompatActivity() {
    var numOfQuestion: TextView? = null
    var question: TextView? = null

    var listcheckBoxes: List<CheckBox>? = null
    var listanswerTexts: List<TextView>? = null

    var buttonNext: Button? = null

    var listOfTasks: List<Task>? = null

    var countOfQ: Int = 0
    var qCounter: Int = 1
    var countOfRightAnswers: Int = 0

    var topicId:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topicId = intent.getIntExtra(("topicid"),0)
        assert(topicId == 123)

        setContentView(R.layout.run_test_activity2)

        numOfQuestion = findViewById(R.id.numOfQuestion)
        question = findViewById(R.id.question)

        listcheckBoxes = listOf(
            findViewById(R.id.checkbox_1),
            findViewById(R.id.checkbox_2),
            findViewById(R.id.checkbox_3),
            findViewById(R.id.checkbox_4)
        )

        listanswerTexts = listOf(
            findViewById(R.id.answer_1),
            findViewById(R.id.answer_2),
            findViewById(R.id.answer_3),
            findViewById(R.id.answer_4)
        )

        buttonNext = findViewById(R.id.buttonNext)

        //Todo: тут запросы
        setListTasks(topicId)

        countOfQ = listOfTasks?.size!!

        qCounter = 1
        countOfRightAnswers = 0
        val dialog = AlertDialog.Builder(
            this@TestActivity
        )
            .setMessage("После нажатия на кнопку 'Подтвердить', будут показаны результаты по этому вопросу")
            .setPositiveButton(
                "Ok"
            ) { _, _ -> setView() }.create()
        dialog.show()
    }

    private fun setListTasks(topicid: Int?) {
        val topic1 = Task()
        val topic2 = Task()

        var q1 = "Question 1"
        var q2 = "Question 2"

        val a1 = HashMap<Int, String>()
        a1[111] = "a"
        a1[222] = "b"
        a1[333] = "c"
        a1[444] = "d"

        val a2 = HashMap<Int, String>()
        a2[1] = "aa"
        a2[2] = "bb"
        a2[3] = "cc"
        a2[4] = "dd"

        var r1 = listOf<Int>(222,444)
        var r2 = listOf<Int>(1)
        topic1.setQuestion(q1)
        topic1.setAnswers(a1)
        topic1.setRightIds(r1)

        topic2.setQuestion(q2)
        topic2.setAnswers(a2)
        topic2.setRightIds(r2)
        listOfTasks = listOf(topic1,topic2)
    }

    private fun setView() {
        val task = listOfTasks!![qCounter-1]

        val answers = task.getAnswers()
        val keys = answers?.keys?.toList()

        numOfQuestion!!.text = qCounter.toString() + " из " + countOfQ.toString()
        question?.text = task.getQuestion()
        for (i in 0..3) {
            listanswerTexts?.get(i)!!.setTextColor(Color.GRAY)
            listanswerTexts?.get(i)?.text = answers!![keys?.get(i)]

            listcheckBoxes?.get(i)!!.isChecked = false
        }

    }

    private fun endtest() {
        var result = Result()
        result.quNum = countOfQ
        result.rightAnswNum = countOfRightAnswers
        result.mark = countOfRightAnswers * 10 / countOfQ
        result.topicId = topicId!!
    }

    fun goNextQ(view: View?) {
        var allAnsRight = true

        val answerids = listOfTasks!![qCounter-1].getRightIds()
        val keys = listOfTasks!![qCounter-1].getAnswers()?.keys?.toList()

        when (buttonNext!!.text.toString()) {
            "Подтвердить" -> {
                for (i in 0..3) {
                    if (answerids != null) {
                        if (answerids.contains(keys?.get(i))) {
                            listanswerTexts?.get(i)!!.setTextColor(Color.GREEN)
                            if (!listcheckBoxes?.get(i)!!.isChecked) {
                                allAnsRight = false

                            }
                        } else {
                            if (listcheckBoxes?.get(i)!!.isChecked) {
                                allAnsRight = false

                            }
                        }
                    }
                }
                if (allAnsRight) {
                    countOfRightAnswers++
                }


                if (qCounter < countOfQ) {
                    buttonNext!!.text = "Далее"
                } else {
                    buttonNext!!.text = "Завершить тест"
                }
            }
            "Далее" -> {

                qCounter++
                setView()
                // меняем текст кнопки
                buttonNext!!.text = "Подтвердить"
            }
            "Завершить тест" -> {
                endtest()
            }
            else -> {
            }
        }
    }

}