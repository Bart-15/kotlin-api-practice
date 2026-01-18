package com.bart.apipractice.ui.compose.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.bart.apipractice.viewmodel.UserViewModel

import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bart.apipractice.model.Address
import com.bart.apipractice.model.Company
import com.bart.apipractice.model.Geo
import com.bart.apipractice.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: Int,
    onBackClick: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {

    LaunchedEffect(userId) {
        viewModel.fetchByUserId(userId)
    }

    val uiState by viewModel.userUiState.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Details") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go Back")
                    }
                },

            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {

            when(uiState){
                is UserDetailUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is UserDetailUiState.Error -> {
                    Text(
                        text = (uiState as UserDetailUiState.Error).message,
                        color = Color.Red
                    )
                }
                is UserDetailUiState.Success -> {
                    val user = (uiState as UserDetailUiState.Success).user
                    UserDetailContent(user)
                }
            }

        }

    }
}


@Composable
fun UserDetailContent(user: User) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Name Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = user.name, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "@${user.userName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Phone: ${user.phone}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Website: ${user.website}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserDetailContentPreview() {
    val testUser = User(
        id = 1,
        name = "Leanne Graham",
        userName = "Bret",
        email = "Sincere@april.biz",
        address = Address(
            street = "Kulas Light",
            suite = "Apt. 556",
            city = "Gwenborough",
            zipcode = "92998-3874",
            geo = Geo(
                lat = "-37.3159",
                lng = "81.1496"
            )
        ),
        phone = "1-770-736-8031 x56442",
        website = "hildegard.org",
        company = Company(
            name = "Romaguera-Crona",
            catchPhrase = "Multi-layered client-server neural-net",
            bs = "harness real-time e-markets"
        )
    )
    UserDetailContent(
        user = testUser

    )
}
