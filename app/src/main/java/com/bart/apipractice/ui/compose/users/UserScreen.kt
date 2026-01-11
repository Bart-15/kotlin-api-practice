package com.bart.apipractice.ui.compose.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bart.apipractice.repository.UserRepository
import com.bart.apipractice.viewmodel.UserViewModel
import com.bart.apipractice.viewmodel.UserViewModelFactory

@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    onUserClick: (Int) -> Unit
){
    val context = LocalContext.current

    val viewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(UserRepository())
    )

    val uiState by viewModel.usersUiState.collectAsStateWithLifecycle()

    when (uiState){

        // Loading State
        is UserUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // Error State
        is UserUiState.Error -> {
            val message = (uiState as UserUiState.Error).message
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = message)
            }
        }

        is UserUiState.Success -> {
            val users = (uiState as UserUiState.Success).users

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {

            }
        }
    }
}