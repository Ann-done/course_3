package com.example.login_app.ui.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.login_app.api.service.MySubject
import com.example.login_app.api.service.reqGetSubject
import com.example.login_app.ui.login.LoggedInUserView
import com.example.login_app.ui.login.LoginResult
import com.example.login_app.ui.login.ReturnedSubject
import com.example.login_app.ui.login.SubjectResult

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