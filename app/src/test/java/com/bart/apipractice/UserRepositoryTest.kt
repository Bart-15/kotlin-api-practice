package com.bart.apipractice

import com.bart.apipractice.network.ApiService
import com.bart.apipractice.repository.UserRepository
import com.bart.apipractice.testdata.FAKE_USERS
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {
    private lateinit var userRepository: UserRepository
    private val apiService: ApiService = mockk()

    @Before
    fun setUp() {
        userRepository = UserRepository(apiService)
    }

    @Test
    fun `fetchUsers should return list of users`() = runTest {
        val users = FAKE_USERS

        coEvery { apiService.getUsers() } returns users

        val result = userRepository.fetchUsers()

        assertEquals(users, result)
        coVerify(exactly = 1) { apiService.getUsers() }
    }

    @Test
    fun `fetchUser should return user by id`() = runTest {
        val user = FAKE_USERS.first()

        coEvery { apiService.getUser(user.id) } returns user

        val result = userRepository.fetchUser(user.id)

        assertEquals(user, result)
        coVerify(exactly = 1) { apiService.getUser(user.id) }
    }
}