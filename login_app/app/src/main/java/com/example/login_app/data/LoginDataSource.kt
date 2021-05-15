package com.example.login_app.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.login_app.BuildConfig
import com.example.login_app.api.service.*
import com.example.login_app.data.model.LoggedInUser
import kotlinx.coroutines.runBlocking
import java.io.IOException
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


@RequiresApi(Build.VERSION_CODES.O)
fun makeHash(password: String) : String{

    val salt: ByteArray = "CRYPTO".toByteArray();
    val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, 65536, 128)
    val f: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val hash: ByteArray = f.generateSecret(spec).getEncoded()
    val enc: Base64.Encoder = Base64.getEncoder()
    return enc.encodeToString(hash);
}