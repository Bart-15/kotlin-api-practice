package com.bart.apipractice.ui.compose.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bart.apipractice.viewmodel.UserViewModel

@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    onUserClick: (Int) -> Unit,
    viewModel: UserViewModel = hiltViewModel()
){
    val context = LocalContext.current


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
                modifier = Modifier.fillMaxSize()
                    .statusBarsPadding(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {

                item {
                    Text(
                        text = "User List",
                        modifier = Modifier.padding(8.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                items(users) { user ->
                    UserCard(user = user, onClick = onUserClick)
                }
            }
        }
    }
}