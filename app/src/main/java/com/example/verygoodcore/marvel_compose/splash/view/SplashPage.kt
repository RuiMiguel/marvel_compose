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
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.verygoodcore.authentication_repository.AuthenticationRepository
import com.example.verygoodcore.marvel_compose.R
import com.example.verygoodcore.marvel_compose.splash.viewmodel.AutoLoginStatus
import com.example.verygoodcore.marvel_compose.splash.viewmodel.AutoLoginViewModel
import com.example.verygoodcore.marvel_compose.ui.theme.SplashTheme
import com.example.verygoodcore.marvel_compose.ui.theme.red
import com.example.verygoodcore.marvel_compose.ui.theme.white
import com.example.verygoodcore.secure_storage.SecureStorage

@Composable
fun SplashPage(navController: NavController? = null) {
    val autoLoginViewModel = AutoLoginViewModel(
        AuthenticationRepository(
            SecureStorage(context = LocalContext.current)
        )
    )
    LaunchedEffect(Unit) { autoLoginViewModel.autoLogin() }

    SplashView(navController, autoLoginViewModel)
}

@Composable
fun SplashView(navController: NavController?, autoLoginViewModel: AutoLoginViewModel) {
    val state = autoLoginViewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    when (state.status) {
        AutoLoginStatus.success -> {
            navController?.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        }
        AutoLoginStatus.error -> {
            val errorMessage = stringResource(id = R.string.auto_login_fail)
            LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar(errorMessage)

                navController?.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    Scaffold(
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
                        CircularProgressIndicator(color = white)
                    }
                }
            }
        }
    )
}


@Preview()
@Composable
fun SplashPagePreview() {
    SplashTheme() {
        SplashPage()
    }
}