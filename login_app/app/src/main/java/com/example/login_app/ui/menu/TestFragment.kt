package com.example.login_app.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.login_app.R


class TestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.activity_test, container, false)

        val spinner = root.findViewById<Spinner>(R.id.spinner)

        val activity: MenuActivity? = activity as MenuActivity?
        val list = activity?.getTestList()
        val button = root.findViewById<Button>(R.id.runtest)
        if (list != null) {
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(root.context, android.R.layout.simple_spinner_item, list)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = spinnerArrayAdapter
            spinner.setSelection(0)
            spinner.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                    val selectedItem =
                        parent.getItemAtPosition(position).toString() //this is your selected item
                    button.isEnabled = selectedItem != list[0]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        return root
    }
}// Required empty public constructor