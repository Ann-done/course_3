package com.example.login_app.api.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Task {

    @SerializedName("question")
    @Expose
    private var question : String? = null

    @SerializedName("right_ids")
    @Expose
    private var right_ids : List<Int>? = null

    @SerializedName("answers")
    @Expose
    private var answers : Map<Int, String>? = null

    fun getQuestion(): String? {
        return question
    }

    fun getAnswers(): Map<Int, String>? {
        return answers
    }

    fun getRightIds(): List<Int>? {
        return right_ids
    }
}