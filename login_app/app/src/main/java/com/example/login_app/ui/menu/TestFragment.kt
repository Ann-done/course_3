package com.example.login_app.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.example.login_app.R
import com.example.login_app.api.service.Topic

class TestFragment : Fragment() {

    //var subjectTV:TextView? = null
   // var spinner:Spinner? = null
   //  var button:Button? = null
    //var spinnerArrayAdapter: ArrayAdapter<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.activity_test, container, false)

        setData(root)
        return root
    }

    fun setData(root:View){

        val subjectTV = root.findViewById<TextView>(R.id.subjectName)
        val spinner = root.findViewById<Spinner>(R.id.spinner)
        val loadTV = root.findViewById<TextView>(R.id.loadingTV)
        val button = root.findViewById<Button>(R.id.runtest)

        val activity: MenuActivity? = activity as MenuActivity?
        val list = activity?.getTopicList()
        val subject = activity?.getSubject()


        if (subject !=null ){
            Log.d("Pretty Printed JSON :", "TestFregment subject not null : " + subject.getFullName() )
            subjectTV.text = subject.getFullName()
            loadTV.visibility = View.INVISIBLE
        }
        if (list != null) {
            var spinnerArrayAdapter =
                    ArrayAdapter<Topic>(root.context, android.R.layout.simple_spinner_item, list)
            spinnerArrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner?.adapter = spinnerArrayAdapter
            spinner?.setSelection(0)
            spinner?.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                ) {

                    val selectedItem =
                            parent.getItemAtPosition(position) //this is your selected item
                    button?.isEnabled = selectedItem != list[0]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

    }

}// Required empty public constructor