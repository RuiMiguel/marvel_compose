package com.codelabs.marvelcompose.splash.viewmodel

import com.codelabs.authentication_repository.AuthenticationRepository
import com.codelabs.authentication_repository.model.PrivateKey
import com.codelabs.authentication_repository.model.PublicKey
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
class SplashViewModelTest {
    @get:Rule
    val testCoroutinesRule = TestCoroutinesRule(testDispatcher = StandardTestDispatcher())

    private lateinit var authenticationRepository: AuthenticationRepository

    private lateinit var splashViewModel: SplashViewModel

    @BeforeEach
    fun setUp() {
        authenticationRepository = mockk(relaxed = true)

        splashViewModel = SplashViewModel(
            authenticationRepository = authenticationRepository,
            dispatcher = testCoroutinesRule.testDispatcher,
        )
    }

    @Test
    fun `autoLogin emits Loading then Success when login succeeds`() = testCoroutinesRule.testScope.runTest {
        coEvery { authenticationRepository.privateKey() } returns "mockPrivateKey"
        coEvery { authenticationRepository.publicKey() } returns "mockPublicKey"
        coEvery {
            authenticationRepository.login(PrivateKey("mockPrivateKey"), PublicKey("mockPublicKey"))
        } just Runs

        splashViewModel.autoLogin()

        assertEquals(SplashStatus.Loading, splashViewModel.state.first())

        advanceUntilIdle()

        assertEquals(SplashStatus.Success, splashViewModel.state.first())
    }

    @Test
    fun `autoLogin emits Loading then Error when login fails`() =  testCoroutinesRule.testScope.runTest {
        val errorMessage = "Login failed"
        coEvery { authenticationRepository.privateKey() } returns "mockPrivateKey"
        coEvery { authenticationRepository.publicKey() } returns "mockPublicKey"
        coEvery {
            authenticationRepository.login(PrivateKey("mockPrivateKey"), PublicKey("mockPublicKey"))
        } throws Exception(errorMessage)

        splashViewModel.autoLogin()

        assertEquals(SplashStatus.Loading, splashViewModel.state.first())

        advanceUntilIdle()

        assertTrue(splashViewModel.state.first() is SplashStatus.Error)
        assertEquals(
            errorMessage,
            (splashViewModel.state.first() as SplashStatus.Error).message
        )
    }
}