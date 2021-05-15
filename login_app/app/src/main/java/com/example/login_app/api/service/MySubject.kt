package com.example.login_app.api.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MySubject {
    @SerializedName("Id")
    @Expose
    private var id = 0

    @SerializedName("ShortName")
    @Expose
    private var shortName : String? = null

    @SerializedName("FullName")
    @Expose
    private var fullName : String? = null
    
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

    fun getFullName(): String? {
        return fullName
    }

    fun setFullName(fullName: String) {
        this.fullName = fullName
    }

}