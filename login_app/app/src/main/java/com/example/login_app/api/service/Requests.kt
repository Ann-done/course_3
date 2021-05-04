package com.example.login_app.api.service

import android.os.Message
import android.util.Log
import androidx.annotation.NonNull
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


fun rawJSON(){


    NetworkService!!.getInstance()!!.getJSONApi()!!.getStudents()!!
        .enqueue( object:  Callback<List<Student>?> {
            override fun onResponse(
                call: Call<List<Student>?>,
                response: Response<List<Student>?>
            ) {
                val list: List<Student>? = response.body()
                if (list != null) {
                    for(student in list){
                        Log.d("Pretty Printed JSON :", student.getName().toString())
                    }
                }else{
                    Log.d("Pretty Printed JSON :", "list is null")
                }
            }

            override fun onFailure(call: Call<List<Student>?>, t: Throwable) {
                Log.e("RETROFIT_ERROR", "error")
                t.printStackTrace()
            }


        }
        )



}




