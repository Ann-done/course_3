package com.example.login_app.ui.login

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.annotation.RequiresApi
import com.example.login_app.data.LoginRepository
import com.example.login_app.data.Result

import com.example.login_app.R
import com.example.login_app.api.service.Student
import com.example.login_app.api.service.reqlogIn
import com.example.login_app.data.model.LoggedInUser
import okio.Utf8
import java.lang.Exception
import java.nio.charset.StandardCharsets.UTF_8
import java.security.MessageDigest

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    @RequiresApi(Build.VERSION_CODES.O)
    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job

        val lastname =  username.substringBefore(" ");
        val name = username.substringAfter(" ");
        val cardHash = md5(password).toHex()

        reqlogIn( cardHash, lastname, name) {student: Student? ->

            if (student == null) {
                Log.d("Pretty Printed JSON :", "Ошибка отправки запроса")
                val result = Result.Error(exception = Exception("Ошибка соединения с сервером"))
                _loginResult.value = LoginResult(error = result.exception.message)

            } else  if (student.getMessage() != null ){
                     Log.d("Pretty Printed JSON :", "Авторизация не прошла")
                    val result = student.getMessage()
                    _loginResult.value = LoginResult(error = result)
                }
            else {

                val result = loginRepository.login(lastname, name, cardHash, student!!.getGroupId())

                if (result is Result.Success) {
                    _loginResult.value = LoginResult(success = LoggedInUserView(userId = result.data.userId, lastName = result.data.lastName, name = result.data.name, groupId = result.data.groupId))
                } else {
                    _loginResult.value = LoginResult(error = " Error in LoginView Model")

                }
            }

        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (!username.isEmpty()) {
            username.trim().split(" ").size == 2
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length == 7
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun md5(str:String) :ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
    private fun ByteArray.toHex() = joinToString(separator = "") { byte-> "%02x".format(byte) }
}