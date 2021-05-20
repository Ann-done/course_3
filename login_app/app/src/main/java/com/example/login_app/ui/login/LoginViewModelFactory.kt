package com.example.login_app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.login_app.data.LoginDataSource
import com.example.login_app.data.LoginRepository
import com.example.login_app.ui.menu.*

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                    loginRepository = LoginRepository(
                            dataSource = LoginDataSource()
                    )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class TestViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            return TestViewModel( ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class TopicViewModelFactory : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopicViewModel::class.java)) {
            return TopicViewModel( ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class TaskViewModelFactory : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel( ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SendResViewModelFactory : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SendResViewModel::class.java)) {
            return SendResViewModel( ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class LocResViewModelFactory : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocResViewModel::class.java)) {
            return LocResViewModel( ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class RemResViewModelFactory : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemResViewModel::class.java)) {
            return RemResViewModel( ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}