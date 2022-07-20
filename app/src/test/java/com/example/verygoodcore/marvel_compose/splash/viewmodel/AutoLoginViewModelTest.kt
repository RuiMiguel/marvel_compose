package com.example.verygoodcore.marvel_compose.splash.viewmodel

import com.example.verygoodcore.authentication_repository.AuthenticationRepository
import com.example.verygoodcore.marvel_compose.TestCoroutinesRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.jupiter.api.Test

internal class AutoLoginViewModelTest {
    private val authenticationRepository: AuthenticationRepository = mock()
    private val autoLoginViewModel: AutoLoginViewModel = AutoLoginViewModel(authenticationRepository)

    @get:Rule
    val testCoroutinesRule = TestCoroutinesRule()

    @Test
    fun `can be instantiated`(): Unit = runTest {
        assertThat(AutoLoginViewModel(authenticationRepository)).isNotNull
    }

    @Test
    fun `emit loading, success when login succeeded`(): Unit = testCoroutinesRule.testScope.runTest {
        whenever(authenticationRepository.login(privateKey = any(), publicKey = any())).thenReturn(Unit)

        autoLoginViewModel.autoLogin()

        assertThat(autoLoginViewModel.state).isEqualTo(
            flowOf(
                AutoLoginState(status = AutoLoginStatus.loading),
                AutoLoginState(status = AutoLoginStatus.success),
            )
        )
    }

    @Test
    fun `emit loading, success when login fails`() = runTest {
        val authenticationRepository = mock<AuthenticationRepository>() {
            on(this.mock.login(privateKey = any(), publicKey = any())).thenReturn(Unit)
        }
        whenever(authenticationRepository.login(privateKey = any(), publicKey = any())).thenThrow(RuntimeException(""))

        autoLoginViewModel.autoLogin()

        assertThat(autoLoginViewModel.state).isEqualTo(
            flowOf(
                AutoLoginState(status = AutoLoginStatus.loading),
                AutoLoginState(status = AutoLoginStatus.error),
            )
        )
    }
}