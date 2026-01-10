package com.bart.apipractice.repository

import com.bart.apipractice.model.User
import com.bart.apipractice.network.RetrofitInstance

class UserRepository {
    suspend fun fetchUsers(): List<User> {
        return RetrofitInstance.api.getUsers()
    }
}