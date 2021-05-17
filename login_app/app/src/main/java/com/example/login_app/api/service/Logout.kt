package com.example.login_app.api.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Logout {
    @SerializedName("message")
    @Expose
    var message: String? = null
}