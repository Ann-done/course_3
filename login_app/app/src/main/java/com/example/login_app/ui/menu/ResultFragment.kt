package com.example.login_app.ui.menu

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.login_app.Db.DbHelper
import com.example.login_app.R
import com.example.login_app.api.service.Result
import com.example.login_app.ui.login.LocResViewModelFactory
import com.example.login_app.ui.login.RemResViewModelFactory


class ResultFragment : Fragment() {

    private lateinit var localResViewModel: LocResViewModel
    private lateinit var remoteResViewModel: RemResViewModel

    var listResults: ArrayList<Result>? = null
    var dbHelper: DbHelper? = null
    var studentId:String? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_result, container, false)
        val activity: MenuActivity? = activity as MenuActivity?
        studentId = activity?.studentId

        val lv_list = root.findViewById<ListView>(R.id.LV_results)
        val refresh = root?.findViewById<ImageButton>(R.id.refreshRes)

        localResViewModel = ViewModelProvider(this, LocResViewModelFactory())
                .get(LocResViewModel::class.java)
        remoteResViewModel = ViewModelProvider(this, RemResViewModelFactory())
                .get(RemResViewModel::class.java)
        // вызываем получение из лок

        dbHelper = DbHelper(root.context);
        localResViewModel.getLocRes(dbHelper, studentId!!)
        localResViewModel.localResResult.observe(viewLifecycleOwner, Observer {
            val locResResult = it ?: return@Observer

            if (locResResult.error != null) {
                Log.d("Pretty Printed JSON :", "Результатов нет" + locResResult.error)
                // вывод во view что нет результатов
            }
            if (locResResult.success != null) {
                // достаем данные из лок
                val adapter = ViewAdapter(root.context, locResResult.success.listRes)
                lv_list.adapter = adapter
            }
        })

        remoteResViewModel.getRemRes(dbHelper, studentId!!)
        remoteResViewModel.remoteResResult.observe(viewLifecycleOwner, Observer {
            val remResResult = it ?: return@Observer

            if (remResResult.error != null) {
                Log.d("Pretty Printed JSON :",  remResResult.error)
            }
            if (remResResult.success != null) {
                // получаем новые добавляем в лок и вызываем получение из лок
                localResViewModel.getLocRes(dbHelper, studentId!!)
            }
        })

        refresh?.setOnClickListener{
            localResViewModel.getLocRes(dbHelper, studentId!!)
        }
        return root
    }
}