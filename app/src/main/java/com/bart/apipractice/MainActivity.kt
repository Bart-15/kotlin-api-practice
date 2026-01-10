package com.bart.apipractice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bart.apipractice.model.User
import com.bart.apipractice.network.RetrofitInstance
import com.bart.apipractice.repository.UserRepository
import com.bart.apipractice.ui.theme.APIPracticeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val repository = UserRepository() // initialize repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APIPracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        fetchUsers()
    }


    private fun fetchUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val users = repository.fetchUsers() // ✅ fetch via repository
                users.forEach {
                    Log.d("API_FETCH", "User: ${it.name} (${it.email})")
                }
            } catch (e: Exception) {
                Log.e("API_FETCH", "Error fetching users", e)
            }
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    APIPracticeTheme {
        Greeting("Android")
    }
}