package com.example.login_app.api.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Student {

    @SerializedName("Id")
    @Expose
    private var id : String? = null

    @SerializedName("LastName")
    @Expose
    private var lastName: String? = null

    @SerializedName("FirstName")
    @Expose
    private var name: String? = null

    @SerializedName("Otchestvo")
    @Expose
    private var otch: String? = null

    @SerializedName("IsLogIn")
    @Expose
    private var isLogIn  = 0

    @SerializedName("GroupId")
    @Expose
    private var groupId  = 0

    fun getId(): String? {
        return id
    }

    fun setId(Id: String) {
        this.id = id
    }

    fun getLastName(): String? {
        return lastName
    }

    fun setLastName(lastName: String?) {
        this.lastName = lastName
    }

    fun getName(): String? {
        return name
    }

    fun setName(body: String?) {
        this.name = body
    }

    fun getOtchestvo(): String? {
        return otch
    }

    fun setOtchestvo(otch: String?) {
        this.otch = otch
    }

    fun getIsLogIn(): Int {
        return isLogIn
    }

    fun getGrouId(): Int {
        return groupId
    }

    fun setGroupId(groupId: Int) {
        this.groupId = groupId
    }

}


