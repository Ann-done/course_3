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

    @SerializedName("GroupId")
    @Expose
    private var groupId  = 0

    @SerializedName("SubjectId")
    @Expose
    private var subjectId = 0

    @SerializedName("SubjShortName")
    @Expose
    private var subjShName: String? = null

    @SerializedName("SubjFullName")
    @Expose
    private var subjFullName: String? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null


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

    fun getGroupId(): Int {
        return groupId
    }

    fun setGroupId(groupId: Int) {
        this.groupId = groupId
    }
    fun getSubjectId(): Int {
        return subjectId
    }

    fun setSubjectId(subjectId: Int) {
        this.subjectId = subjectId
    }
    fun getSubjShName(): String? {
        return subjShName
    }

    fun setSubjectShName(subjShName: String) {
        this.subjShName = subjShName
    }

    fun getSubjFullName(): String? {
        return subjFullName
    }

    fun setSubjectFullName(subjFullName: String) {
        this.subjFullName = subjFullName
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }
}


