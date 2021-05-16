package com.example.login_app.ui.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login_app.api.service.MySubject
import com.example.login_app.api.service.Topic
import com.example.login_app.api.service.reqGetSubject
import com.example.login_app.api.service.reqGetTopics
import com.example.login_app.ui.login.*

class TestViewModel : ViewModel(){

    private val _subjectResult = MutableLiveData<SubjectResult>()
    val subjectResult: LiveData<SubjectResult> = _subjectResult

    fun getSubject(groupId:Int) {

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

    fun getTopics(subjectId:Int) {

        reqGetTopics(subjectId) { listTopics: List<Topic>? ->

            if (listTopics == null){
                Log.d("Pretty Printed JSON :", "Темы пришли пустые")
                _topicsResult.value = TopicResult(error = "Нет тем")
            }
            else{

                _topicsResult.value = TopicResult(success = ReturnedTopics(listT=listTopics))
            }
        }

    }
}