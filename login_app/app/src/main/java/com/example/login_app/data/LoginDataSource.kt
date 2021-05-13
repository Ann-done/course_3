package com.example.login_app.data

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.login_app.BuildConfig
import com.example.login_app.api.service.*
import com.example.login_app.data.model.LoggedInUser
import java.io.IOException
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    @RequiresApi(Build.VERSION_CODES.O)
    fun login(username: String, password: String): Result<LoggedInUser> {
        try {

            val lastname =  username.substringBefore(" ");
            val name = username.substringAfter(" ");
            val cardHash = makeHash(password);

            //TODO поменять метод хэширования
            Log.d("Pretty Printed JSON :", cardHash.toString())

            //TODO установить ожидание выполнения запроса
            val student = reqlogIn(cardHash, lastname, name)
            if (student?.getMessage() == null) {
                Log.d("Pretty Printed JSON :", student!!.getGroupId().toString())
            } else {

                //TODO проверить вылетает ли ошибка
                Log.d("Pretty Printed JSON :", student.getMessage().toString())
                throw Throwable(student.getMessage())
            }
          //TODO в новую активити id студента передастся в виде byteArrray.toString()
             val fakeUser = LoggedInUser(cardHash.toString(), "$lastname $name", student.getGroupId())
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(Exception(e.message,e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}



@RequiresApi(Build.VERSION_CODES.KITKAT)
fun makeHash(password: String) : ByteArray {
    var md = MessageDigest.getInstance("SHA-512")
    return md.digest(password.toByteArray(StandardCharsets.UTF_8));
}