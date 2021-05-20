package com.example.login_app.api.service

import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import com.example.login_app.Db.DbHelper
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



fun reqlogIn(id: String, lastname: String, firstname: String, callback: (Student?) -> Unit){//настроить возврат

    val req:JSONObject = JSONObject()
    req.put("Id", id)
    req.put("LastName", lastname)
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
    var subject :MySubject? = MySubject()

    NetworkService.getInstance()!!.getJSONApi()!!.postGetSubject(req.toString())?.enqueue(object : Callback<MySubject?> {

        override fun onResponse(call: Call<MySubject?>, response: Response<MySubject?>) {
            val mySubject_temp = response.body()
            Log.d("Pretty Printed JSON :", "Subject from response  : Get subject from server ")
            subject = mySubject_temp //вернет либо null либо subject
            callback(subject)
        }

        override fun onFailure(call: Call<MySubject?>, t: Throwable) {
            Log.e("RETROFIT_ERROR", "error")
            callback(null)
            t.printStackTrace()
        }
    })
}

fun reqGetTopics(subjectId: Int, studentId: String, callback: (List<Topic>?) -> Unit){

    val req:JSONObject = JSONObject()
    req.put("SubjId", subjectId)
    req.put("StudentId", studentId)

    NetworkService.getInstance()!!.getJSONApi()!!.getTopics(req.toString())?.enqueue(object : Callback<List<Topic>?> {
        override fun onResponse(
                call: Call<List<Topic>?>,
                response: Response<List<Topic>?>
        ) {
            val list: List<Topic>? = response.body()
            callback(list)
        }

        override fun onFailure(call: Call<List<Topic>?>, t: Throwable) {
            Log.e("RETROFIT_ERROR", "error")
            callback(null)
            t.printStackTrace()
        }
    })
}

fun reqGetTest(topicId: Int, callback: (List<Task>?) -> Unit){
    val req:JSONObject = JSONObject()
    req.put("Id", topicId)

    NetworkService.getInstance()!!.getJSONApi()!!.postGetTest(req.toString())
            ?.enqueue(object : Callback<List<Task>?> {

                override fun onResponse(call: Call<List<Task>?>, response: Response<List<Task>?>) {
                    val list: List<Task>? = response.body()
                    if (list != null) {
                        Log.d("Pretty Printed JSON :", "Тест с вопросами пришел успешно")
                        callback(list);
                    } else {
                        Log.d("Pretty Printed JSON :", "list task is null")
                        callback(null);
                    }
                }

                override fun onFailure(call: Call<List<Task>?>, t: Throwable) {
                    Log.e("RETROFIT_ERROR", "error")
                    t.printStackTrace()
                }
            })

}

fun reqPostResult(result: Result, groupId: Int, subjectId: Int, topicId: Int, callback: (Int) -> Unit){
    val req:JSONObject = JSONObject()
    req.put("StudentId", result.studentId)
    req.put("GroupId", groupId)
    req.put("SubjectId", subjectId)
    req.put("TopicId", topicId)
    req.put("QuNum", result.quNum)
    req.put("RightAnsws", result.rightAnsNum)
    req.put("Mark", result.mark)

    NetworkService.getInstance()!!.getJSONApi()!!.postResult(req.toString())
            ?.enqueue(object : Callback<Int> {

                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    val list: Int? = response.body()
                    if (list != 0) {
                        Log.d("Pretty Printed JSON :", "Результат сохранился на сервера")
                        callback(list!!);
                    } else {
                        Log.d("Pretty Printed JSON :", "Результат НЕ сохранился на сервера")
                        callback(0);
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Log.e("RETROFIT_ERROR", "error")
                    callback(0);
                    t.printStackTrace()
                }
            })
}

fun reqGetNewResults(db: DbHelper?, studentId: String, callback: (Int) -> Unit){

    // получаем последний локальный id
    val database = db!!.writableDatabase
    val cursor: Cursor = database.rawQuery("select top(1) " + DbHelper.KEY_ID + " from " + DbHelper.TABLE_RESULTS
            + " where " + DbHelper.KEY_STUDENTID + "=" + studentId +
            " order by " + DbHelper.KEY_ID + " desc", null)
    var resId : Int
    if (cursor.moveToFirst()){
        resId = cursor.getInt(cursor.getColumnIndex(DbHelper.KEY_ID))
    }
    else {
        Log.d("Pretty Printed JSON :", "Нет последнего id отправляем на сервер 0")
         resId = 0
    }
    // запрос на сервер
    val req:JSONObject = JSONObject()
    req.put("idSt", studentId)
    req.put("idRes", resId)
    NetworkService.getInstance()!!.getJSONApi()!!.postGetNewRes(req.toString())
            ?.enqueue(object : Callback<List<Result>?> {

                override fun onResponse(call: Call<List<Result>?>, response: Response<List<Result>?>) {
                    val list: List<Result>? = response.body()
                    if (list != null) {
                        Log.d("Pretty Printed JSON :", "Новые Результаты пришли с сервера успешно")

                        for ( item in list){
                            val values = ContentValues()
                            values.put(DbHelper.KEY_ID, item.id)
                            values.put(DbHelper.KEY_QUNUM, item.quNum)
                            values.put(DbHelper.KEY_RANSNUM, item.rightAnsNum)
                            values.put(DbHelper.KEY_MARK, item.mark)
                            values.put(DbHelper.KEY_STUDENTID, item.studentId)
                            values.put(DbHelper.KEY_TOPICNAME, item.topicName)
                            values.put(DbHelper.KEY_SUBJECTNAME, item.subjectName)

                            database.insert(DbHelper.TABLE_RESULTS, null, values)
                        }
                        Log.d("Pretty Printed JSON :", "Добавили новые результаты с удаленной в локальную")
                        callback(1)

                    } else {
                        Log.d("Pretty Printed JSON :", "Новых результатов нет")
                        callback(0)
                    }
                }

                override fun onFailure(call: Call<List<Result>?>, t: Throwable) {
                    Log.e("RETROFIT_ERROR", "error")
                    t.printStackTrace()
                }
            })
}

fun getLocResults(db: DbHelper?, studentId: String, callback: (ArrayList<Result>?) -> Unit){

    val listResults : ArrayList<Result> = ArrayList<Result>()
    val database = db!!.writableDatabase
    val cursor: Cursor = database.rawQuery("select * from " + DbHelper.TABLE_RESULTS
            + " where " + DbHelper.KEY_STUDENTID + "=" + studentId, null)

    if (cursor.moveToFirst()) {
        val idIndex: Int = cursor.getColumnIndex(DbHelper.KEY_ID)
        val quNum: Int = cursor.getColumnIndex(DbHelper.KEY_QUNUM)
        val rightAnsNum: Int = cursor.getColumnIndex(DbHelper.KEY_RANSNUM)
        val mark: Int = cursor.getColumnIndex(DbHelper.KEY_MARK)
        val topicName: Int = cursor.getColumnIndex(DbHelper.KEY_TOPICNAME)
        val subjectName: Int = cursor.getColumnIndex(DbHelper.KEY_SUBJECTNAME)

        do {
            val result = Result()
            result.id =  cursor.getInt(idIndex)
            result.quNum =  cursor.getInt(quNum)
            result.rightAnsNum =  cursor.getInt(rightAnsNum)
            result.mark =  cursor.getInt(mark)
            result.topicName =  cursor.getString(topicName)
            result.subjectName =  cursor.getString(subjectName)
            listResults.add(result)

        } while (cursor.moveToNext())
        cursor.close()
        callback(listResults)
    } else  {
        Log.d("mLog", " результаты студента пришли null из локальной")
        callback(null)
    }

}