package com.example.login_app.ui.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login_app.Db.DbHelper
import com.example.login_app.api.service.*
import com.example.login_app.ui.login.*


class TestViewModel : ViewModel(){

    private val _subjectResult = MutableLiveData<SubjectResult>()
    val subjectResult: LiveData<SubjectResult> = _subjectResult

    fun getSubject(groupId: Int) {

        reqGetSubject(groupId) { subject: MySubject? ->

            if (subject == null){
                Log.d("Pretty Printed JSON :", "Нет доступного предмета")
                _subjectResult.value = SubjectResult(error = "Нет доступного предмета")
            }
            else{
                _subjectResult.value = SubjectResult(success = ReturnedSubject(subjectId = subject.getId(), shortName = subject.getShName()!!, fullName = subject.getFullName()!!))
            }
        }

    }
}

class TopicViewModel :ViewModel(){

    private val _topicsResult = MutableLiveData<TopicResult>()
    val topicsResult: LiveData<TopicResult> = _topicsResult

    fun getTopics(subjectId: Int, studentId: String) {

        reqGetTopics(subjectId, studentId) { listTopics: List<Topic>? ->

            if (listTopics == null){
                Log.d("Pretty Printed JSON :", "Темы пришли пустые")
                _topicsResult.value = TopicResult(error = "Нет доступных тем")
            }
            else{

                _topicsResult.value = TopicResult(success = ReturnedTopics(listT = listTopics))
            }
        }

    }
}

class TaskViewModel: ViewModel(){
    private val _tasksResult = MutableLiveData<TaskResult>()
    val tasksResult: LiveData<TaskResult> = _tasksResult

    fun getTasks(topicId: Int) {

        reqGetTest(topicId) { listTasks: List<Task>? ->
            if (listTasks == null){
                Log.d("Pretty Printed JSON :", "Тест пришел пустым")
                _tasksResult.value = TaskResult(error = "Тест не доступен")
            }
            else{
                _tasksResult.value = TaskResult(success = ReturnedTasks(listTasks = listTasks))
            }
        }

    }
}

class SendResViewModel: ViewModel(){
    private val _postResResult = MutableLiveData<SendResResult>()
    val postResResult: LiveData<SendResResult> = _postResResult

    fun sendResult(db:DbHelper? ,result: Result, groupId: Int, subjectId: Int, topicId: Int) {

        reqPostResult(db, result, groupId, subjectId, topicId) { savedResId: Int ->
            if (savedResId == 0){
                Log.d("Pretty Printed JSON :", "Тест не сохранен")
                _postResResult.value = SendResResult(error = "Вы не успели отправить тест вовремя")
            }
            else{
                _postResResult.value = SendResResult(success = ReturnedSendResult(postResId = savedResId))
            }
        }

    }
}

class LocResViewModel: ViewModel(){
    private val _locResResult = MutableLiveData<LocalResResult>()
    val localResResult: LiveData<LocalResResult> = _locResResult

    fun getLocRes(db: DbHelper?, studentId: String) {
        getLocResults(db, studentId){listRes ->
            if (listRes!=null){
                Log.d("Pretty Printed JSON :", "удачно получены результаты из лок" )
                _locResResult.value = LocalResResult(success = ReturnedLocalRes(listRes = listRes))
            }else{
                _locResResult.value = LocalResResult(error="Результатов нет")
            }

        }
    }
}

class RemResViewModel: ViewModel(){
    private val _remResResult = MutableLiveData<RemoteResResult>()
    val remoteResResult: LiveData<RemoteResResult> = _remResResult

    fun getRemRes(db: DbHelper?, studentId: String) {

        //получение из удаленной 0 - отправляем на сервер 0 либо id
        reqGetNewResults(db, studentId ){resInt ->
            if (resInt == 0){
                _remResResult.value = RemoteResResult(error = "Новых результатов нет")
            }
            else  _remResResult.value = RemoteResResult(success = ReturnedRemoteRes(ok="Пришли новые результаты"))
        }
    }
}