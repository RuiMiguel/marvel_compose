package com.codelabs.marvelcompose.main.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.codelabs.marvelcompose.navigation.AppNavigation
import com.codelabs.marvelcompose.navigation.Navigator

@Composable
fun MainPage() {
    val navController: NavHostController = rememberNavController()
    val navigator = remember { Navigator(navController) }

    AppNavigation(navController = navController, navigator = navigator)
}
