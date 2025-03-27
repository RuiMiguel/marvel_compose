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
import com.codelabs.marvelcompose.splash.viewmodel.SplashState
import com.codelabs.marvelcompose.splash.viewmodel.SplashViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

internal class SplashPageTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var splashViewModel: SplashViewModel
    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        splashViewModel = mockk(relaxed = true)
        navController = mockk(relaxed = true)
    }

    @Test
    fun splashPage_showsImage() {
        every { splashViewModel.state } returns MutableStateFlow(SplashState.Loading)

        composeTestRule.setContent {
            SplashPage(splashViewModel = splashViewModel)
        }

        val contentDescription = composeTestRule.activity.getString(R.string.app_name)
        composeTestRule.onNode(hasContentDescriptionAndRole(contentDescription, Role.Image))
            .assertIsDisplayed()
    }


    @Test
    fun splashPage_showsLoadingIndicator_whenLoadingState() {
        every { splashViewModel.state } returns MutableStateFlow(SplashState.Loading)

        composeTestRule.setContent {
            SplashPage(splashViewModel = splashViewModel)
        }

        composeTestRule.onNode(hasProgressBar())
            .assertIsDisplayed()
    }

    @Test
    fun splashPage_showsSnackbar_whenErrorState() = runTest {
        val errorMessage = "Login failed"
        every { splashViewModel.state } returns MutableStateFlow(
            SplashState.Error(
                errorMessage
            )
        )

        composeTestRule.setContent {
            SplashPage(splashViewModel = splashViewModel)
        }

        composeTestRule.awaitIdle()

        composeTestRule.onNodeWithText(errorMessage)
            .assertIsDisplayed()
    }

    @Test
    @Ignore("Verification failed call to navController was not called")
    fun splashPage_navigatesToHome_whenError() = runTest {
        every { splashViewModel.state } returns MutableStateFlow(SplashState.Error("Login failed"))

        composeTestRule.setContent {
            SplashPage(navController = navController, splashViewModel = splashViewModel)
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
        every { splashViewModel.state } returns MutableStateFlow(SplashState.Success)

        composeTestRule.setContent {
            SplashPage(navController = navController, splashViewModel = splashViewModel)
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