package com.example.login_app.api.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Answer {
    @SerializedName("Id")
    @Expose
    private var id = 0

    @SerializedName("Text")
    @Expose
    private var text : String? = null

    @SerializedName("IsRight")
    @Expose
    private var isRight = 0


    fun getId(): Int {
        return id
    }

    fun setId(Id: Int) {
        this.id = Id
    }

    fun getText(): String? {
        return text
    }

    fun setText(text: String) {
        this.text = text
    }

    fun getIsRight(): Int {
        return isRight
    }

    fun setIsRight(isRight: Int) {
        this.isRight = isRight
    }
}