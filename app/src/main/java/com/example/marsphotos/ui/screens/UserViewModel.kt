package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.User
import com.example.marsphotos.network.UserApi
import kotlinx.coroutines.launch

//represents all possible UIT states
sealed interface UserUiState {
    data class Success(val users: List<User>) : UserUiState
    object Loading : UserUiState
    data class Error(val message: String) : UserUiState
}

//viewmodel for user directory screen
class UserViewModel : ViewModel() {
    //compose state holds current state
    var uiState: UserUiState by mutableStateOf(UserUiState.Loading)
        private set
    //gets users when viewmodel is created
    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            uiState = UserUiState.Loading
            uiState = try {
                val result = UserApi.retrofitService.getUsers()
                UserUiState.Success(result.users)
            } catch (e: Exception) {
                UserUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}