package com.example.verygoodcore.marvel_compose.navigation

import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.example.verygoodcore.marvel_compose.ComposableHiltTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class NavigationTest : ComposableHiltTest() {
    @Test
    fun hasANavHost() {
        composeTestRule.setContent {
            AppNavigation(navController = rememberNavController(), navigator = Navigator())
        }

        composeTestRule.onNodeWithTag("NavHost").apply {
            assertExists()
            // TODO(ruimiguel): test if NavHost startDestination is SplashPage
        }
    }
}