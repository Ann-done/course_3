package com.example.login_app.data

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.login_app.BuildConfig
import com.example.login_app.api.service.*
import com.example.login_app.data.model.LoggedInUser
import kotlinx.coroutines.runBlocking
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
    fun login(lastname:String, name: String, password: String,groupId: Int ): Result<LoggedInUser> {
        try {

            var fakeUser = LoggedInUser(password, lastname, name, groupId)

            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(Exception("Login failed",e))

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