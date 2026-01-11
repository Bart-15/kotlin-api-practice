package com.bart.apipractice.ui.compose.users

import com.bart.apipractice.model.User

sealed interface UserUiState {
    object Loading : UserUiState
    data class Success(val users: List<User>) : UserUiState
    data class Error(val message: String) : UserUiState
}