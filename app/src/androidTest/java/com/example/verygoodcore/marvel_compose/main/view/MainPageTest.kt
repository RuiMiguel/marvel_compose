package com.example.verygoodcore.marvel_compose.main.view

import androidx.compose.ui.test.onNodeWithTag
import com.example.verygoodcore.marvel_compose.ComposableHiltTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
class MainPageTest : ComposableHiltTest() {
    @Test
    fun hasANavHost() {
        composeTestRule.setContent {
            MainPage()
        }

        composeTestRule.onNodeWithTag("NavHost").apply {
            assertExists()
            // TODO(ruimiguel): test if has a NavigationComponent
        }
    }
}