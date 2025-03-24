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
import com.codelabs.marvelcompose.splash.viewmodel.AutoLoginState
import com.codelabs.marvelcompose.splash.viewmodel.AutoLoginViewModel
import com.codelabs.marvelcompose.ui.theme.Red
import com.codelabs.marvelcompose.ui.theme.SplashTheme
import com.codelabs.marvelcompose.ui.theme.White

@Composable
fun SplashPage(
    navController: NavController? = null,
    autoLoginViewModel: AutoLoginViewModel = hiltViewModel(),
) {

    SplashTheme {
        SplashView(navController = navController, autoLoginViewModel = autoLoginViewModel)
    }
}

@Composable
fun SplashView(navController: NavController? = null, autoLoginViewModel: AutoLoginViewModel) {
    val authState by autoLoginViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        autoLoginViewModel.autoLogin()
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val defaultErrorMessage = stringResource(id = R.string.auto_login_fail)

    LaunchedEffect(authState) {
        when (authState) {
            AutoLoginState.Success -> {
                navController?.navigate(PageRoute.Home.route) {
                    popUpTo(PageRoute.Splash.route) { inclusive = true }
                }
            }
            is AutoLoginState.Error -> {
                val errorMessage =
                    (authState as? AutoLoginState.Error)?.message ?: defaultErrorMessage
                snackbarHostState.showSnackbar(errorMessage, duration = SnackbarDuration.Short)
                navController?.navigate(PageRoute.Home.route)
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