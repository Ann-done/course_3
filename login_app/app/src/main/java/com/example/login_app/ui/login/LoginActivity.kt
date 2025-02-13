package com.example.login_app.ui.login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.login_app.R
import com.example.login_app.api.service.reqlogIn
import com.example.login_app.ui.menu.MenuActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    @RequiresApi(26)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)


        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            if (loginState.isDataValid){
                login.isEnabled = true
                //login.background = Drawabl
            }

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                username.visibility = View.VISIBLE
                password.visibility = View.VISIBLE
                login.visibility = View.VISIBLE
                username.setText("")
                password.setText("")
               showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                //var login = loginResult.success.displayName
                    var fullname = loginResult.success.lastName + " " + loginResult.success.name
                val intent = Intent(this, MenuActivity::class.java).apply {
                    putExtra("username", fullname)
                    putExtra("password", loginResult.success.userId)
                    putExtra("groupId", loginResult.success.groupId)
                    putExtra("num",1)

                }
                startActivity(intent)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
           // finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                                username.text.toString(),
                                password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
               username.visibility = View.INVISIBLE
                password.visibility = View.INVISIBLE
                login.visibility = View.INVISIBLE
               loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }

    }

    private fun showLoginFailed( errorString: String) {
        Log.d("Pretty Printed JSON :", "Вывод toast о неудачной авторизации" + errorString)
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}