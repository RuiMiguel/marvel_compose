package com.codelabs.marvelcompose.login.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.codelabs.marvelcompose.home.widget.HeroesAppBar
import com.codelabs.marvelcompose.login.viewmodel.LoginViewModel
import com.codelabs.marvelcompose.ui.theme.MainTheme

@Composable
fun LoginPage(navController: NavController? = null) {
    val loginViewModel = LoginViewModel()

    MainTheme {
        LoginView(navController = navController, loginViewModel = loginViewModel)
    }
}

@Composable
fun LoginView(navController: NavController? = null, loginViewModel: LoginViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HeroesAppBar(withBack = true, onBack = { navController?.popBackStack() })
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Surface(color = MaterialTheme.colorScheme.background) {

            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun LoginPageDarkPreview() {
    LoginPage()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun LoginPageLightPreview() {
    LoginPage()
}