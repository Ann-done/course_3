package com.example.login_app.api.service

import android.util.Log
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


fun reqlogIn(id: String, lastname: String, firstname: String){ //настроить возврат

    val req:JSONObject = JSONObject()
    req.put("Id",id)
    req.put("LastName",lastname)
    req.put("FirstName", firstname)

    NetworkService.getInstance()!!.getJSONApi()!!.postLogIn(req.toString())!!
        .enqueue(object : Callback<Student?> {

            override fun onResponse(call: Call<Student?>, response: Response<Student?>) {

                //проверить message
                // проверить subjectId
                val student: Student? = response.body()
                if (student?.getMessage() == null) {
                    Log.d("Pretty Printed JSON :", student!!.getName().toString())
                    Log.d("Pretty Printed JSON :", student.getId().toString())
                    Log.d("Pretty Printed JSON :", student.getSubjectId().toString())
                    Log.d("Pretty Printed JSON :", student.getSubjShName().toString())

                } else {
                    Log.d("Pretty Printed JSON :", student.getMessage().toString())
                }
            }

            override fun onFailure(call: Call<Student?>, t: Throwable) {
                Log.e("RETROFIT_ERROR", "error")
                t.printStackTrace()
            }
        })
}

fun reqGetSubject(groupId: Int){

    val req:JSONObject = JSONObject()
    req.put("Id", groupId)
    NetworkService.getInstance()!!.getJSONApi()!!.postGetSubject(req.toString())?.enqueue(object : Callback<Subject?> {

        override fun onResponse(call: Call<Subject?>, response: Response<Subject?>) {
            val subject: Subject? = response.body()
            if (subject?.getMessage() == null) {
                Log.d("Pretty Printed JSON :", "Subject id :" + subject!!.getId())
                Log.d("Pretty Printed JSON :","Short name : " + subject.getShName())
                Log.d("Pretty Printed JSON :", "Full name: "+ subject.getSFullName())

            } else {
                Log.d("Pretty Printed JSON :", subject!!.getMessage().toString())
            }
        }

        override fun onFailure(call: Call<Subject?>, t: Throwable) {
            Log.e("RETROFIT_ERROR", "error")
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
