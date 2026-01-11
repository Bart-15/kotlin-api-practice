package com.bart.apipractice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bart.apipractice.repository.UserRepository
import com.bart.apipractice.ui.compose.users.UserDetailUiState
import com.bart.apipractice.ui.compose.users.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
): ViewModel() {

    private val _usersUiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val usersUiState: StateFlow<UserUiState> = _usersUiState

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _usersUiState.value = UserUiState.Loading

            try {
                val users = repository.fetchUsers()
                _usersUiState.value = UserUiState.Success(users)
            } catch (e: Exception) {
                _usersUiState.value =
                    UserUiState.Error(e.message ?: "Failed to load users")
            }
        }
    }


    private val _userDetailUiState = MutableStateFlow<UserDetailUiState>(UserDetailUiState.Loading)
    val userUiState: StateFlow<UserDetailUiState> = _userDetailUiState


    fun fetchByUserId(id: Int){
        viewModelScope.launch {
            _userDetailUiState.value = UserDetailUiState.Loading

            try {
                val user = repository.fetchUser(id)
                _userDetailUiState.value = UserDetailUiState.Success(user)
            } catch (e: Exception) {
                _userDetailUiState.value = UserDetailUiState.Error(e.message ?: "Failed to load user")
            }
        }
    }

}