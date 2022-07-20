package com.example.verygoodcore.marvel_compose.splash.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.verygoodcore.marvel_compose.R
import com.example.verygoodcore.marvel_compose.navigation.PageRoute
import com.example.verygoodcore.marvel_compose.splash.viewmodel.AutoLoginStatus
import com.example.verygoodcore.marvel_compose.splash.viewmodel.AutoLoginViewModel
import com.example.verygoodcore.marvel_compose.ui.theme.SplashTheme
import com.example.verygoodcore.marvel_compose.ui.theme.red
import com.example.verygoodcore.marvel_compose.ui.theme.white

@Composable
fun SplashPage(navController: NavController? = null) {
    val autoLoginViewModel = hiltViewModel<AutoLoginViewModel>()

    LaunchedEffect(Unit) {
        autoLoginViewModel.autoLogin()
    }

    SplashTheme {
        SplashView(navController = navController, autoLoginViewModel = autoLoginViewModel)
    }
}

@Composable
fun SplashView(navController: NavController? = null, autoLoginViewModel: AutoLoginViewModel) {
    val state = autoLoginViewModel.state.value

    val snackbarHostState = remember { SnackbarHostState() }

    when (state.status) {
        AutoLoginStatus.success -> {
            LaunchedEffect(Unit) {
                navController?.navigate(PageRoute.Home.route) {
                    popUpTo(PageRoute.Splash.route) { inclusive = true }
                }
            }
        }
        AutoLoginStatus.error -> {
            val errorMessage = stringResource(id = R.string.auto_login_fail)
            LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar(errorMessage)
                navController?.navigate(PageRoute.Login.route) {
                    popUpTo(PageRoute.Splash.route) { inclusive = true }
                }
            }
        }
        else -> {}
    }

    Scaffold(
        Modifier.semantics { testTag = "SplashScaffold" },
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        content = {
            Box(modifier = Modifier
                .background(red)
                .fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Image(
                        painterResource(R.drawable.splash),
                        contentDescription = stringResource(id = R.string.app_name))
                    Spacer(modifier = Modifier.size(30.dp))
                    if (state.status == AutoLoginStatus.loading) {
                        CircularProgressIndicator(modifier = Modifier.semantics { testTag = "SplashViewCircularProgressIndicator" }, color = white)
                    }
                }
            }
        }
    )
}


@Preview
@Composable
fun SplashPagePreview() {
    SplashPage()
}