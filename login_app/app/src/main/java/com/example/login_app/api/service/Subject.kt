package com.example.login_app.api.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Subject {
    @SerializedName("Id")
    @Expose
    private var id = 0

    @SerializedName("ShortName")
    @Expose
    private var shortName : String? = null

    @SerializedName("FullName")
    @Expose
    private var fullName : String? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    fun getId(): Int {
        return id
    }

    fun setId(Id: Int) {
        this.id = Id
    }

    fun getShName(): String? {
        return shortName
    }

    fun setShName(shortName: String) {
        this.shortName = shortName
    }

    fun getSFullName(): String? {
        return fullName
    }

    fun setFullName(fullName: String) {
        this.fullName = fullName
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }
}