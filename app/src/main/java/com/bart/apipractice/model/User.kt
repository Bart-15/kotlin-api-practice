package com.bart.apipractice.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id: Int,
    val name: String,
    @Json(name = "username") val userName: String,
    val email: String,
)