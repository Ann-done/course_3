package com.example.login_app.api.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Result {
    @SerializedName("Id")
    @Expose
    public var id = 0

    @SerializedName("QuNum")
    @Expose
    public var quNum = 0

    @SerializedName("RightAnswNum")
    @Expose
    public var rightAnswNum = 0

    @SerializedName("Mark")
    @Expose
    public var mark = 0

    @SerializedName("StudentId")
    @Expose
    public var studentId: String? = null

    @SerializedName("TopicName")
    @Expose
    public var topicName = null

    @SerializedName("SubjectName")
    @Expose
    public var subjectName = null

}