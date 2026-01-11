package com.bart.apipractice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bart.apipractice.repository.UserRepository
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


}