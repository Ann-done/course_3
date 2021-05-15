package com.example.login_app.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.login_app.R


class TestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater!!.inflate(R.layout.activity_test, container, false)

        val spinner = root.findViewById<Spinner>(R.id.spinner)

        val activity: MenuActivity? = activity as MenuActivity?
        val list = activity?.getTestList()
        if (list != null) {
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(root.context, android.R.layout.simple_spinner_item, list)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinner.adapter = spinnerArrayAdapter

            var selected = spinner.selectedItem.toString()
        }


        return root
    }
}// Required empty public constructor