package com.bart.apipractice.repository

import com.bart.apipractice.model.User
import com.bart.apipractice.network.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun fetchUsers(): List<User> = apiService.getUsers()

    suspend fun fetchUser(userId: Int): User = apiService.getUser(userId)
}
