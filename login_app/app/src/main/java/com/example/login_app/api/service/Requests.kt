package com.example.login_app.api.service

import android.util.Log
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun rawJSON(){
    NetworkService!!.getInstance()!!.getJSONApi()!!.getStudents()!!
        .enqueue(object : Callback<List<Student>?> {
            override fun onResponse(
                    call: Call<List<Student>?>,
                    response: Response<List<Student>?>
            ) {
                val list: List<Student>? = response.body()
                if (list != null) {
                    for (student in list) {
                        Log.d("Pretty Printed JSON :", student.getName().toString())
                    }
                } else {
                    Log.d("Pretty Printed JSON :", "list is null")
                }
            }

            override fun onFailure(call: Call<List<Student>?>, t: Throwable) {
                Log.e("RETROFIT_ERROR", "error")
                t.printStackTrace()
            }
        })
}



fun reqlogIn(id: String, lastname: String, firstname: String, callback:(Student?) -> Unit){//настроить возврат

    val req:JSONObject = JSONObject()
    req.put("Id",id)
    req.put("LastName",lastname)
    req.put("FirstName", firstname)
    var student = Student()


    NetworkService.getInstance()!!.getJSONApi()!!.postLogIn(req.toString())!!.enqueue(object : Callback<Student?> {

         override fun onResponse(call: Call<Student?>, response: Response<Student?>) {

            val student_temp = response.body()
            Log.d("Pretty Printed JSON :", "Student from response  : Get student from server ")
            student = student_temp!! // возвращает либо message либо groupid
            callback(student)
        }

        override fun onFailure(call: Call<Student?>, t: Throwable) {
            Log.e("RETROFIT_ERROR", "error")
            callback(null)
            t.printStackTrace()
        }
    })
    }


fun reqGetSubject(groupId: Int, callback: (MySubject?) -> Unit){

    val req:JSONObject = JSONObject()
    req.put("Id", groupId)
    var subject :MySubject = MySubject()

    NetworkService.getInstance()!!.getJSONApi()!!.postGetSubject(req.toString())?.enqueue(object : Callback<MySubject?> {

        override fun onResponse(call: Call<MySubject?>, response: Response<MySubject?>) {
            val mySubject_temp = response.body()
            Log.d("Pretty Printed JSON :", "Subject from response  : Get subject from server ")
            subject = mySubject_temp!! //вернет либо null либо subject
           callback(subject)
        }

        override fun onFailure(call: Call<MySubject?>, t: Throwable) {
            Log.e("RETROFIT_ERROR", "error")
            callback(null)
            t.printStackTrace()
        }
    })
}

fun reqGetTopics (subjectId: Int){

//    val req:JSONObject = JSONObject()
//    req.put("Id", subjectId)
    NetworkService.getInstance()!!.getJSONApi()!!.getTopics(subjectId)?.enqueue(object : Callback<List<Topic>?> {
        override fun onResponse(
                call: Call<List<Topic>?>,
                response: Response<List<Topic>?>
        ) {
            val list: List<Topic>? = response.body()
            if (list != null) {
                for (topic in list) {
                    Log.d("Pretty Printed JSON :", "Topic id: " + topic.getId() + "; Name : " + topic.getName())
                }
            } else {
                Log.d("Pretty Printed JSON :", "list is null")
            }
        }

        override fun onFailure(call: Call<List<Topic>?>, t: Throwable) {
            Log.e("RETROFIT_ERROR", "error")
            t.printStackTrace()
        }
    })
}

fun reqPostTest(topicId: Int){
    val req:JSONObject = JSONObject()
    req.put("Id", topicId)

    NetworkService.getInstance()!!.getJSONApi()!!.postGetTest(req.toString())
            ?.enqueue(object : Callback<List<Task>?> {

                override fun onResponse(call: Call<List<Task>?>, response: Response<List<Task>?>) {
                    val list: List<Task>? = response.body()
                    if (list != null) {
                        for (task in list) {
                            Log.d("Pretty Printed JSON :", "qustion : " + task.getQuestion() + "; "
                            + "Answers : 1" + task.getAnswers()!!.get(1) )
                        }
                    } else {
                        Log.d("Pretty Printed JSON :", "list task is null")
                    }
                }

                override fun onFailure(call: Call<List<Task>?>, t: Throwable) {
                    Log.e("RETROFIT_ERROR", "error")
                    t.printStackTrace()
                }
            })

}
