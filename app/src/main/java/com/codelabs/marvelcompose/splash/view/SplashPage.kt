package com.codelabs.marvelcompose.splash.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.navigation.PageRoute
import com.codelabs.marvelcompose.splash.viewmodel.SplashState
import com.codelabs.marvelcompose.splash.viewmodel.SplashViewModel
import com.codelabs.marvelcompose.ui.theme.Red
import com.codelabs.marvelcompose.ui.theme.SplashTheme
import com.codelabs.marvelcompose.ui.theme.White

@Composable
fun SplashPage(
    navController: NavController? = null,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    SplashTheme {
        SplashView(navController = navController, splashViewModel = splashViewModel)
    }
}

@Composable
fun SplashView(navController: NavController? = null, splashViewModel: SplashViewModel) {
    val authState by splashViewModel.state.collectAsState()

    // We want to start the auth flow as soon as the app starts. Only do this once.
    LaunchedEffect(Unit) {
        splashViewModel.autoLogin()
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val defaultErrorMessage = stringResource(id = R.string.auto_login_fail)

    // Wee only want to execute this code once, when the authState changes.
    LaunchedEffect(authState) {
        when (authState) {
            SplashState.Success -> {
                navController?.navigate(PageRoute.Home.route) {
                    popUpTo(PageRoute.Splash.route) { inclusive = true }
                }
            }

            is SplashState.Error -> {
                val errorMessage =
                    (authState as? SplashState.Error)?.message ?: defaultErrorMessage
                snackbarHostState.showSnackbar(errorMessage, duration = SnackbarDuration.Short)
                navController?.navigate(PageRoute.Login.route) {
                    popUpTo(PageRoute.Splash.route) { inclusive = true }
                }
            }

            else -> Unit
        }
    }

    SplashContainer(snackbarHostState)
}

@Composable
fun SplashContainer(snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .background(Red)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.splash),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = stringResource(id = R.string.app_name)
                )
                Spacer(modifier = Modifier.size(30.dp))
                CircularProgressIndicator(color = White)
            }
        }
    }
}

@Preview
@Composable
fun SplashContainerPreview() {
    SplashContainer()
}