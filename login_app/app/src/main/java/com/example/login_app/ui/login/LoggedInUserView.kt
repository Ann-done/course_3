package com.example.login_app.ui.login

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