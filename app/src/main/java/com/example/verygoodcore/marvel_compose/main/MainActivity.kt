package com.example.verygoodcore.marvel_compose.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.example.verygoodcore.marvel_compose.main.viewmodel.AuthenticationViewModel
import com.example.verygoodcore.marvel_compose.main.view.MainPage
import com.example.verygoodcore.marvel_compose.splash.viewmodel.AutoLoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @VisibleForTesting
    internal val authenticationViewModel: AuthenticationViewModel by viewModels()
    internal val autoLoginViewModel: AutoLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainPage()
        }
    }
}