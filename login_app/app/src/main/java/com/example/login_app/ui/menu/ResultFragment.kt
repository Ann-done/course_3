package com.example.login_app.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.login_app.R

class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_result, container, false)

        val lv_list = root.findViewById<ListView>(R.id.LV_results)

        val activity: MenuActivity? = activity as MenuActivity?
        val listResults = activity?.getResults()

        if (listResults != null) {
            val adapter = ViewAdapter(root.context, listResults)
            lv_list.adapter = adapter
        }
        return root
    }
}