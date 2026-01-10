package com.bart.apipractice.network

import com.bart.apipractice.model.User
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}
