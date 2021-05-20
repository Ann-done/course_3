package com.example.login_app.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: LoggedInUserView? = null,
        val error: String? = null
)

data class SubjectResult(
        val success: ReturnedSubject? = null,
        val error:String? = null

)

data class TopicResult(
        val success: ReturnedTopics? = null,
        val error: String? = null
)

data class TaskResult(
        val success: ReturnedTasks? = null,
        val error: String? = null
)

data class SendResResult(
        val success: ReturnedSendResult? = null,
        val error: String? = null
)

// получение результатов
data class LocalResResult(
        val success: ReturnedLocalRes? = null,
        val error: String? = null
)
data class RemoteResResult(
        val success: ReturnedRemoteRes? = null,
        val error: String? = null
)