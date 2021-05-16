package com.example.login_app.api.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Topic {

    @SerializedName("Id")
    @Expose
    private var id = 0

    @SerializedName("TopicName")
    @Expose
    private var topicName : String? = null

    fun getId(): Int {
        return id
    }

    fun setId(Id: Int) {
        this.id = Id
    }

    fun getName(): String? {
        return topicName
    }

    fun setName(name: String) {
        this.topicName = name
    }

    override fun toString(): String {
        return if(this.getName() == null){
            "empty topic name"
        }else {
            this.getName()!!
        }
    }
}