package com.codelabs.marvelcompose.main.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.codelabs.marvelcompose.navigation.AppNavigation
import com.codelabs.marvelcompose.navigation.Navigator

@Composable
fun MainPage() {
    val navController: NavHostController = rememberNavController()
    AppNavigation(navController = navController, navigator = Navigator(navController))
}
