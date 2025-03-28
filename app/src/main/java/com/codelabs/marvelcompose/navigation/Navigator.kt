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
        if (canPop) {
            _sharedFlow.tryEmit(PageRoute.GoBack)
        }
    }

    fun fromSplashToHome() {
        _sharedFlow.tryEmit(PageRoute.Home)
    }

    fun fromSplashToLogin() {
        _sharedFlow.tryEmit(PageRoute.Login)
    }

    fun toLogin() {
        _sharedFlow.tryEmit(PageRoute.Login)
    }

    fun toHome() {
        _sharedFlow.tryEmit(PageRoute.Home)
    }
}