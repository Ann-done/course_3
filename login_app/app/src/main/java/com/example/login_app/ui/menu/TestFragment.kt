package com.example.login_app.ui.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.login_app.R
import com.example.login_app.api.service.MySubject
import com.example.login_app.api.service.Topic
import com.example.login_app.ui.login.TestViewModelFactory
import com.example.login_app.ui.login.TopicViewModelFactory


class TestFragment : Fragment() {

    private lateinit var testViewModel: TestViewModel
    private lateinit var topicViewModel: TopicViewModel

    var subject:MySubject? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val  root = inflater.inflate(R.layout.activity_test, container, false)

        val subjAvTV = root?.findViewById<TextView>(R.id.isSubjAv)
        val subjectTV = root?.findViewById<TextView>(R.id.subjectName)
        val spinner = root?.findViewById<Spinner>(R.id.spinner)
        val button = root?.findViewById<Button>(R.id.runtest)
        val loading = root?.findViewById<ProgressBar>(R.id.loadingTest)
        val resend = root?.findViewById<ImageButton>(R.id.resend)

        loading?.visibility = View.VISIBLE
        testViewModel = ViewModelProvider(this, TestViewModelFactory())
                .get(TestViewModel::class.java)

        val activity: MenuActivity? = activity as MenuActivity?
        //здесь отправляется запрос
        if (activity?.groupId == null){
            Log.d("Pretty Printed JSON :", "В TestFragment groupId пришел нулевым")
        }else{
            testViewModel.getSubject(activity.groupId!!)
        }

        var topicId: Int = 0;
        testViewModel.subjectResult.observe(viewLifecycleOwner, Observer {
            val subjectResult = it ?: return@Observer

            if (subjectResult.error != null) {
                loading?.visibility = View.GONE
                subjAvTV?.text = subjectResult.error
                resend?.visibility = View.VISIBLE
            }
            if (subjectResult.success != null) {
                subject = MySubject()
                subject!!.setId(subjectResult.success.subjectId)
                subject!!.setShName(subjectResult.success.shortName)
                subject!!.setFullName(subjectResult.success.fullName)

                Log.d("Pretty Printed JSON :", "Предмет пришел удачно" + subject!!.getFullName())

                topicViewModel = ViewModelProvider(this, TopicViewModelFactory())
                        .get(TopicViewModel::class.java)

                topicViewModel.getTopics(subject!!.getId())

                topicViewModel.topicsResult.observe(viewLifecycleOwner, Observer {
                    val topicsResult = it ?: return@Observer

                    loading?.visibility = View.GONE
                    if (topicsResult.error != null) {
                        Log.d("Pretty Printed JSON :", "Нет доступных тем" + topicsResult.error)
                        loading?.visibility = View.GONE
                        subjAvTV?.text = "Сейчас доступен предмет: "
                        subjAvTV?.text = subject!!.getFullName()
                                // вывод отсутствия тем
                        var list = ArrayList<Topic>()
                        var topic = Topic()
                        topic.setName("-Нет доступных тем-")
                        list.add(topic)
                        var spinnerArrayAdapter =
                                ArrayAdapter<Topic>(root.context, R.layout.spinner_item, list)
                        spinnerArrayAdapter?.setDropDownViewResource(R.layout.spinner_item)
                        spinner?.adapter = spinnerArrayAdapter
                        spinner?.setSelection(0)
                        resend?.visibility = View.VISIBLE
                    }
                    if (topicsResult.success != null) {
                        var list = ArrayList<Topic>()
                        var topic = Topic()
                        topic.setName("Выберите предмет")
                        list.add(topic)
                        list.addAll(topicsResult.success.listT)

                        var spinnerArrayAdapter =
                        ArrayAdapter<Topic>(root.context, R.layout.spinner_item, list)
                       spinnerArrayAdapter?.setDropDownViewResource(R.layout.spinner_item)
                        spinner?.adapter = spinnerArrayAdapter
                        spinner?.setSelection(0)
                         spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View,
                            position: Int,
                            id: Long
                    ) {
                        val selectedItem =
                                parent.getItemAtPosition(position) //this is your selected item
                        button?.isEnabled = selectedItem != list[0]
                        topicId = list[list.indexOf(selectedItem)].getId()
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
                        subjAvTV?.text = "Сейчас доступен предмет: "
                        subjectTV?.text = subject!!.getFullName()
                        spinner?.visibility = View.VISIBLE
                        button?.visibility = View.VISIBLE
                        resend?.visibility = View.VISIBLE
                    }
                })

            }
        })

        resend?.setOnClickListener{
            testViewModel.getSubject(activity?.groupId!!)
            loading?.visibility = View.VISIBLE
            subjAvTV?.text = ""
            subjectTV?.text = ""
            spinner?.visibility = View.INVISIBLE
            button?.visibility = View.INVISIBLE
            resend.visibility = View.INVISIBLE

        }
        button?.setOnClickListener {
            if (topicId!=0){
              runtest(root, topicId)
            }
        }
        return root
    }

    fun runtest(view: View, id: Int){
        val intent = Intent(view.context, TestActivity::class.java).apply {
            putExtra("topicId", id)
        }
        startActivity(intent)
    }

}// Required empty public constructor