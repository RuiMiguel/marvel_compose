package com.codelabs.marvelcompose.splash.viewmodel

import com.codelabs.authentication_repository.AuthenticationRepository
import com.codelabs.marvelcompose.helpers.TestCoroutinesRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@OptIn(ExperimentalCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AutoLoginViewModelTest {
    @get:Rule
    val testCoroutinesRule = TestCoroutinesRule(testDispatcher = StandardTestDispatcher())

    private lateinit var authenticationRepository: AuthenticationRepository

    private lateinit var autoLoginViewModel: AutoLoginViewModel

    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk(relaxed = true)

        autoLoginViewModel = AutoLoginViewModel(
            authenticationRepository = authenticationRepository,
            dispatcher = testCoroutinesRule.testDispatcher,
        )
    }

    @Test
    fun `autoLogin emits Loading then Success when login succeeds`() = runTest {
        coEvery { authenticationRepository.privateKey() } returns "mockPrivateKey"
        coEvery { authenticationRepository.publicKey() } returns "mockPublicKey"
        coEvery { authenticationRepository.login(any(), any()) } just Runs

        autoLoginViewModel.autoLogin()

        assertEquals(AutoLoginState.Loading, autoLoginViewModel.state.first())

        advanceUntilIdle()

        assertEquals(AutoLoginState.Success, autoLoginViewModel.state.first())
    }

    @Test
    fun `autoLogin emits Loading then Error when login fails`() = runTest {
        val errorMessage = "Login failed"
        coEvery { authenticationRepository.privateKey() } throws Exception(errorMessage)

        autoLoginViewModel.autoLogin()

        assertEquals(AutoLoginState.Loading, autoLoginViewModel.state.first())

        advanceUntilIdle()

        assertTrue(autoLoginViewModel.state.first() is AutoLoginState.Error)
        assertEquals(
            errorMessage,
            (autoLoginViewModel.state.first() as AutoLoginState.Error).message
        )
    }
}