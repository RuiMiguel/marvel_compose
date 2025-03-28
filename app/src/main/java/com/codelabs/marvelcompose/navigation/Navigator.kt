package com.codelabs.marvelcompose.navigation

import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

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