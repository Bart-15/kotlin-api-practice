package com.bart.apipractice

import com.bart.apipractice.model.Address
import com.bart.apipractice.model.Company
import com.bart.apipractice.model.Geo
import com.bart.apipractice.model.User
import com.bart.apipractice.network.ApiService
import com.bart.apipractice.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

val FAKE_USERS = listOf(
    User(
        id = 1,
        name = "John Doe",
        userName = "johnd",
        email = "john@example.com",
        address = Address(
            street = "123 Main St",
            suite = "Apt 1",
            city = "Metropolis",
            zipcode = "12345",
            geo = Geo(lat = "12.3456", lng = "65.4321")
        ),
        phone = "123-456-7890",
        website = "johndoe.com",
        company = Company(
            name = "Doe Industries",
            catchPhrase = "Innovate Everything",
            bs = "business solutions"
        )
    ),
    User(
        id = 2,
        name = "Jane Smith",
        userName = "janes",
        email = "jane@example.com",
        address = Address(
            street = "456 Side St",
            suite = "Suite 200",
            city = "Gotham",
            zipcode = "67890",
            geo = Geo(lat = "98.7654", lng = "43.2109")
        ),
        phone = "987-654-3210",
        website = "janesmith.io",
        company = Company(
            name = "Smith Co",
            catchPhrase = "Think Different",
            bs = "tech solutions"
        )
    )
)


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