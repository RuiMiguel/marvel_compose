package com.codelabs.marvelcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codelabs.marvelcompose.home.view.HomePage
import com.codelabs.marvelcompose.login.view.LoginPage
import com.codelabs.marvelcompose.splash.view.SplashPage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class Navigator(private val navController: NavHostController) {
    private val _sharedFlow =
        MutableSharedFlow<PageRoute>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    val canPop: Boolean
        get() = navController.previousBackStackEntry != null

    fun goBack() {
        navController.popBackStack()
    }

    fun fromSplashToHome() {
        navController.navigate(PageRoute.Home.route) {
            popUpTo(PageRoute.Splash.route) { inclusive = true }
        }
    }

    fun fromSplashToLogin() {
        navController.navigate(PageRoute.Login.route) {
            popUpTo(PageRoute.Splash.route) { inclusive = true }
        }
    }

    fun toLogin() {
        navController.navigate(PageRoute.Login.route)
    }

    fun toHome() {
        navController.navigate(PageRoute.Home.route) {
            popUpTo(PageRoute.Login.route) { inclusive = true }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, navigator: Navigator) {
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach { pageRoute ->
            navController.navigate(pageRoute.route) {
                pageRoute.popUpTo?.let {
                    popUpTo(it.route) { inclusive = pageRoute.inclusive }
                }
            }
        }.launchIn(this)
    }


    NavHost(navController = navController, startDestination = PageRoute.Splash.route) {
        composable(PageRoute.Splash.route) { SplashPage(navigator) }
        composable(PageRoute.Home.route) { HomePage(navigator) }
        composable(PageRoute.Login.route) { LoginPage(navigator) }
    }
}

sealed class PageRoute(
    val route: String,
    var popUpTo: PageRoute? = null,
    var inclusive: Boolean = false
) {
    data object Splash : PageRoute("splash")
    data object Home : PageRoute("home", popUpTo = Splash, inclusive = true)
    data object Login : PageRoute("login")
}