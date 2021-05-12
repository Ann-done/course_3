package com.example.login_app.api.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Question {
    @SerializedName("Id")
    @Expose
    private var id = 0

    @SerializedName("QuName")
    @Expose
    private var quName : String? = null

    fun getId(): Int {
        return id
    }

    fun setId(Id: Int) {
        this.id = Id
    }

    fun getQuName(): String? {
        return quName
    }

    fun setQuName(quName: String) {
        this.quName = quName
    }
}