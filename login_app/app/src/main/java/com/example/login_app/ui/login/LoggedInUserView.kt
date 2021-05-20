package com.example.login_app.ui.login

import com.example.login_app.api.service.Task
import com.example.login_app.api.service.Topic
import com.example.login_app.api.service.Result

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(

        val userId: String,
        val lastName: String,
        val name: String,
        val groupId: Int
        //... other data fields that may be accessible to the UI
)

data class ReturnedSubject(
        val subjectId: Int,
        val shortName: String,
        val fullName: String
)

data class ReturnedTopics(
        val listT: List<Topic>
)

data class ReturnedTasks(
        val listTasks: List<Task>
)

data class ReturnedSendResult(
        val postResId: Int
)

data class ReturnedLocalRes(
        val listRes: ArrayList<Result>
)
data class ReturnedRemoteRes(
        val ok : String
)