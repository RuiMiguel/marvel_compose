package com.codelabs.marvelcompose.login.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.codelabs.authentication_repository.model.PrivateKey
import com.codelabs.authentication_repository.model.PublicKey
import com.codelabs.authentication_repository.model.User
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.home.widgets.HeroesAppBar
import com.codelabs.marvelcompose.login.viewmodel.LoginStatus
import com.codelabs.marvelcompose.login.viewmodel.LoginViewModel
import com.codelabs.marvelcompose.login.widgets.AuthenticatedLoginForm
import com.codelabs.marvelcompose.login.widgets.UnauthenticatedLoginForm
import com.codelabs.marvelcompose.main.viewmodel.AuthenticationStatus
import com.codelabs.marvelcompose.main.viewmodel.AuthenticationViewModel
import com.codelabs.marvelcompose.navigation.PageRoute
import com.codelabs.marvelcompose.ui.theme.MainTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun LoginPage(
    navController: NavController? = null,
    loginViewModel: LoginViewModel = hiltViewModel(),
    authenticationViewModel: AuthenticationViewModel = hiltViewModel(),
) {
    MainTheme {
        LoginView(
            navController = navController,
            loginViewModel = loginViewModel,
            authenticationViewModel = authenticationViewModel
        )
    }
}

@Composable
fun LoginView(
    navController: NavController? = null,
    loginViewModel: LoginViewModel,
    authenticationViewModel: AuthenticationViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val defaultErrorMessage = stringResource(id = R.string.login_fail)

    val canPop = navController?.previousBackStackEntry != null

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HeroesAppBar(withBack = canPop, onBack = { navController?.popBackStack() })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(30.dp)
        ) {

            val authenticationStatus by authenticationViewModel.status.collectAsStateWithLifecycle()
            val userPrivateKey = authenticationStatus.user.privateKey.key
            val userPublicKey = authenticationStatus.user.publicKey.key

            val loginStatus by loginViewModel.status.collectAsStateWithLifecycle()
            val privateKey by loginViewModel.privateKey.collectAsStateWithLifecycle()
            val publicKey by loginViewModel.publicKey.collectAsStateWithLifecycle()

            val isAuthenticated = authenticationStatus.isAuthenticated
            val isLoading = loginStatus.isLoading

            LaunchedEffect(userPrivateKey, userPublicKey){
                loginViewModel.publicKeyChange(userPublicKey)
                loginViewModel.privateKeyChange(userPrivateKey)
            }

            LaunchedEffect(loginStatus){
                when (loginStatus) {
                    is LoginStatus.Success -> {
                        navController?.navigate(PageRoute.Home.route) {
                            popUpTo(PageRoute.Home.route) { inclusive = true }
                        }
                    }
                    is LoginStatus.Error -> {
                        snackbarHostState.showSnackbar(defaultErrorMessage, duration = SnackbarDuration.Short)
                    }
                    else -> Unit
                }
            }

            if (isAuthenticated) {
                AuthenticatedLoginForm(
                    privateKey = privateKey,
                    publicKey = publicKey,
                    onSave = { loginViewModel.login() },
                    onLogout = { loginViewModel.logout() },
                    onPublicKeyChange = loginViewModel::publicKeyChange,
                    onPrivateKeyChange = loginViewModel::privateKeyChange,
                    isLoading = isLoading
                )
            } else {
                UnauthenticatedLoginForm(
                    privateKey = privateKey,
                    publicKey = publicKey,
                    onLogin = { loginViewModel.login() },
                    onPublicKeyChange = loginViewModel::publicKeyChange,
                    onPrivateKeyChange = loginViewModel::privateKeyChange ,
                    isLoading = isLoading
                )
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun AuthenticatedLoginPagePreview() {
    val authenticationViewModel = mockk<AuthenticationViewModel>(relaxed = true)
    val loginViewModel = mockk<LoginViewModel>(relaxed = true)

    val authStateFlow =
        MutableStateFlow(
            AuthenticationStatus.Authenticated(
                user = User(
                    publicKey = PublicKey("publicKey"),
                    privateKey = PrivateKey("privateKey")
                )
            )
        )
    val loginStatusFlow = MutableStateFlow(LoginStatus.Success)

    every { authenticationViewModel.status } returns authStateFlow
    every { loginViewModel.status } returns loginStatusFlow

    MainTheme {
        LoginPage(
            loginViewModel = loginViewModel,
            authenticationViewModel = authenticationViewModel
        )
    }
}

@DarkLightPreviews
@Composable
fun UnauthenticatedLoginPagePreview() {
    val authenticationViewModel = mockk<AuthenticationViewModel>(relaxed = true)
    val loginViewModel = mockk<LoginViewModel>(relaxed = true)

    val authStateFlow =
        MutableStateFlow(AuthenticationStatus.Unauthenticated)
    val loginStatusFlow = MutableStateFlow(LoginStatus.Success)

    every { authenticationViewModel.status } returns authStateFlow
    every { loginViewModel.status } returns loginStatusFlow

    MainTheme {
        LoginPage(
            loginViewModel = loginViewModel,
            authenticationViewModel = authenticationViewModel
        )
    }
}