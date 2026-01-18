package com.bart.apipractice

import com.bart.apipractice.model.User
import com.bart.apipractice.repository.UserRepository
import com.bart.apipractice.testdata.FAKE_USERS
import com.bart.apipractice.ui.compose.users.UserDetailUiState
import com.bart.apipractice.ui.compose.users.UserUiState
import com.bart.apipractice.viewmodel.UserViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private lateinit var repository: UserRepository


    @Before
    fun setUp() {
        repository = mockk<UserRepository>()
    }

    // ---------- Users List Tests ----------
    @Test
    fun `usersUiState emits Success when repository returns users`() = runTest {
        val users = FAKE_USERS

        coEvery { repository.fetchUsers() } returns users

        val viewModel = UserViewModel(repository)


        val result = viewModel.usersUiState.first() { it !is UserUiState.Loading }

        assertEquals(UserUiState.Success(users), result)
        coVerify(exactly = 1) { repository.fetchUsers() }
    }

    @Test
    fun `usersUiState emits empty list when repository returns empty list`() = runTest {
        coEvery {
            repository.fetchUsers()
        } returns emptyList<User>()

        val viewModel = UserViewModel(repository)

        val result = viewModel.usersUiState.first() { it !is UserUiState.Loading }

        assertEquals(UserUiState.Success(emptyList()), result)
        coVerify(exactly = 1) { repository.fetchUsers() }
    }

    @Test
    fun `userUiState emits Error when repository throws Exception`() = runTest {

        coEvery { repository.fetchUsers() } throws Exception()

        val viewModel: UserViewModel = UserViewModel(repository)


        val result = viewModel.usersUiState.first() { it !is UserUiState.Loading }

        assertEquals(UserUiState.Error("Failed to load users"), result)
        coVerify(exactly = 1) { repository.fetchUsers() }
    }

    // ---------- Single User Tests ----------
    @Test
    fun `userUiState emits Success when repository returns user`() = runTest {
        val user = FAKE_USERS.first()

        coEvery { repository.fetchUser(user.id) } returns user

        val viewModel: UserViewModel = UserViewModel(repository)

        viewModel.fetchByUserId(user.id)

        val result = viewModel.userUiState.first() { it !is UserDetailUiState.Loading }

        assertEquals(UserDetailUiState.Success(user), result)
        coVerify(exactly = 1) { repository.fetchUser(user.id) }
    }

    @Test
    fun `userUiState emits Error with default message when repository throws Exception with no message`() = runTest {
        val userId = 1

        coEvery { repository.fetchUser(userId) } throws Exception()

        val viewModel: UserViewModel = UserViewModel(repository)

        viewModel.fetchByUserId(userId)
        val result = viewModel.userUiState.first { it !is UserDetailUiState.Loading }

        assertEquals(UserDetailUiState.Error("Failed to load user"), result)
        coVerify(exactly = 1) { repository.fetchUser(userId) }
    }

}