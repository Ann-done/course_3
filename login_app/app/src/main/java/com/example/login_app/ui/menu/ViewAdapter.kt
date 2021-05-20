package com.example.login_app.ui.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.login_app.R
import com.example.login_app.api.service.Result
import kotlin.collections.ArrayList

class ViewAdapter(context: Context, objects: ArrayList<Result>?) :
    ArrayAdapter<Result?>(context, R.layout.result_item, objects!! as List<Result?>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val convertView = LayoutInflater.from(context).inflate(R.layout.result_item, parent, false)

        val resObject: Result? = getItem(position)
        if (resObject != null) {
            (convertView!!.findViewById<View>(R.id.TopicName) as TextView).text = resObject.topicName.toString()
            (convertView.findViewById<View>(R.id.SubjectShortName) as TextView).text = resObject.id.toString()
            (convertView.findViewById<View>(R.id.Mark) as TextView).text = resObject.mark.toString()
            (convertView.findViewById<View>(R.id.RightAnswersNum) as TextView).text = resObject.rightAnsNum.toString()
        }

        return convertView
    }
}