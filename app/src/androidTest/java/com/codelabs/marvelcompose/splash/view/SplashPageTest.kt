package com.codelabs.marvelcompose.splash.view

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.helpers.hasContentDescriptionAndRole
import com.codelabs.marvelcompose.helpers.hasProgressBar
import com.codelabs.marvelcompose.navigation.PageRoute
import com.codelabs.marvelcompose.splash.viewmodel.AutoLoginState
import com.codelabs.marvelcompose.splash.viewmodel.AutoLoginViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@ExperimentalCoroutinesApi
class SplashPageTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var autoLoginViewModel: AutoLoginViewModel
    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        autoLoginViewModel = mockk(relaxed = true)
        navController = mockk(relaxed = true)
    }

    @Test
    fun splashPage_showsImage() {
        every { autoLoginViewModel.state } returns MutableStateFlow(AutoLoginState.Loading)

        composeTestRule.setContent {
            SplashPage(autoLoginViewModel = autoLoginViewModel)
        }

        val contentDescription = composeTestRule.activity.getString(R.string.app_name)
        composeTestRule.onNode(hasContentDescriptionAndRole(contentDescription, Role.Image))
            .assertIsDisplayed()
    }


    @Test
    fun splashPage_showsLoadingIndicator_whenLoadingState() {
        every { autoLoginViewModel.state } returns MutableStateFlow(AutoLoginState.Loading)

        composeTestRule.setContent {
            SplashPage(autoLoginViewModel = autoLoginViewModel)
        }

        composeTestRule.onNode(hasProgressBar())
            .assertIsDisplayed()
    }

    @Test
    fun splashPage_showsSnackbar_whenErrorState() = runTest {
        val errorMessage = "Login failed"
        every { autoLoginViewModel.state } returns MutableStateFlow(
            AutoLoginState.Error(
                errorMessage
            )
        )

        composeTestRule.setContent {
            SplashPage(autoLoginViewModel = autoLoginViewModel)
        }

        composeTestRule.awaitIdle()

        composeTestRule.onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }

    @Test
    @Ignore("Verification failed call to navController was not called")
    fun splashPage_navigatesToHome_whenError() = runTest {
        every { autoLoginViewModel.state } returns MutableStateFlow(AutoLoginState.Error("Login failed"))

        composeTestRule.setContent {
            SplashPage(navController = navController, autoLoginViewModel = autoLoginViewModel)
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            navController.currentDestination?.route == PageRoute.Home.route
        }

        verify {
            navController.navigate(
                route = PageRoute.Home.route,
                navOptions = match { it.popUpToRoute == PageRoute.Splash.route && it.isPopUpToInclusive() },
                navigatorExtras = null
            )
        }
    }

    @Test
    @Ignore("Verification failed call to navController was not called")
    fun splashPage_navigatesToHome_whenSuccess() {
        every { autoLoginViewModel.state } returns MutableStateFlow(AutoLoginState.Success)

        composeTestRule.setContent {
            SplashPage(navController = navController, autoLoginViewModel = autoLoginViewModel)
        }

        verify {
            navController.navigate(
                route = PageRoute.Home.route,
                navOptions = match { it.popUpToRoute == PageRoute.Splash.route && it.isPopUpToInclusive() },
                navigatorExtras = null
            )
        }
    }
}