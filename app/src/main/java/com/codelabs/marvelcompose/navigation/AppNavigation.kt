package com.codelabs.marvelcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codelabs.marvelcompose.home.view.HomePage
import com.codelabs.marvelcompose.login.view.LoginPage
import com.codelabs.marvelcompose.splash.view.SplashPage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun AppNavigation(navController: NavHostController, navigator: Navigator) {
    LaunchedEffect(navigator) {
        navigator.sharedFlow.onEach { pageRoute ->
            when (pageRoute) {
                PageRoute.Home -> {
                    navController.navigate(PageRoute.Home.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }

                PageRoute.Login -> {
                    navController.navigate(PageRoute.Login.route)
                }

                is PageRoute.GoBack -> {
                    navController.popBackStack()
                }

                else -> {
                    navController.navigate(pageRoute.route)
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
    val route: String
) {
    data object Splash : PageRoute("splash")
    data object Home : PageRoute("home")
    data object Login : PageRoute("login")
    data object GoBack : PageRoute("goBack")
}