package com.example.verygoodcore.marvel_compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.verygoodcore.marvel_compose.home.view.HomePage
import com.example.verygoodcore.marvel_compose.login.view.LoginPage
import com.example.verygoodcore.marvel_compose.splash.view.SplashPage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// TODO(ruimiguel): try to use Navigator from screens instead of NavHostController
class Navigator() {
    private val _sharedFlow =
        MutableSharedFlow<PageRoute>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun navigateTo(PageRoute: PageRoute) {
        _sharedFlow.tryEmit(PageRoute)
    }

    fun goBack() {
        //popBackStack
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


    NavHost(modifier = Modifier.semantics { testTag = "NavHost" }, navController = navController, startDestination = PageRoute.Splash.route) {
        composable(PageRoute.Splash.route) { SplashPage(navController) }
        composable(PageRoute.Home.route) { HomePage(navController) }
        composable(PageRoute.Login.route) { LoginPage(navController) }
    }
}

sealed class PageRoute(val route: String, var popUpTo: PageRoute? = null, var inclusive: Boolean = false) {
    object Splash : PageRoute("splash")
    object Home : PageRoute("home", popUpTo = Splash, inclusive = true)
    object Login : PageRoute("login")
}