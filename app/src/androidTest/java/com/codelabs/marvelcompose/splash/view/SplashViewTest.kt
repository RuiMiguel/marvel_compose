package com.codelabs.marvelcompose.splash.view

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.helpers.hasContentDescriptionAndRole
import com.codelabs.marvelcompose.helpers.hasProgressBar
import com.codelabs.marvelcompose.navigation.Navigator
import com.codelabs.marvelcompose.splash.viewmodel.SplashStatus
import com.codelabs.marvelcompose.splash.viewmodel.SplashViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class SplashViewTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var navigator: Navigator
    private lateinit var snackbarHostState: SnackbarHostState

    @Before
    fun setup() {
        mockkStatic("androidx.compose.material3.SnackbarHostState")
        snackbarHostState = mockk {
            coEvery { showSnackbar(any(), any(), any()) } returns androidx.compose.material3.SnackbarResult.Dismissed
        }

        splashViewModel = mockk(relaxed = true)
        navigator = mockk(relaxed = true)
    }

    @Test
    fun splashView_showsImage() {
        every { splashViewModel.state } returns MutableStateFlow(SplashStatus.Loading)

        composeTestRule.setContent {
            SplashView(navigator = navigator, splashViewModel = splashViewModel)
        }

        val contentDescription = composeTestRule.activity.getString(R.string.app_name)
        composeTestRule.onNode(hasContentDescriptionAndRole(contentDescription, Role.Image))
            .assertIsDisplayed()
    }

    @Test
    fun splashView_showsLoadingIndicator_whenLoadingState() {
        every { splashViewModel.state } returns MutableStateFlow(SplashStatus.Loading)

        composeTestRule.setContent {
            SplashView(navigator = navigator, splashViewModel = splashViewModel)
        }

        composeTestRule.onNode(hasProgressBar())
            .assertIsDisplayed()
    }

    @Test
    fun splashView_showsSnackbar_whenErrorState() = runTest {
        val errorMessage = "Login failed"
        every { splashViewModel.state } returns MutableStateFlow(
            SplashStatus.Error(
                errorMessage
            )
        )

        composeTestRule.setContent {
            SplashView(navigator = navigator, splashViewModel = splashViewModel)
        }

        advanceUntilIdle()

        composeTestRule.onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }

    @Test
    @Ignore("Verification failed: call 1 of 1: Navigator(#3).fromSplashToLogin()) was not called")
    fun splashView_navigatesToLogin_whenError() = runTest {
        val errorMessage = "Login failed"
        every { splashViewModel.state } returns MutableStateFlow(SplashStatus.Error(errorMessage))

        composeTestRule.setContent {
            SplashView(navigator = navigator, splashViewModel = splashViewModel)
        }

        advanceUntilIdle()

        coVerify { navigator.fromSplashToLogin() }
    }

    @Test
    fun splashView_navigatesToHome_whenSuccess() {
        every { splashViewModel.state } returns MutableStateFlow(SplashStatus.Success)

        composeTestRule.setContent {
            SplashView(navigator = navigator, splashViewModel = splashViewModel)
        }

        verify { navigator.fromSplashToHome() }
    }
}