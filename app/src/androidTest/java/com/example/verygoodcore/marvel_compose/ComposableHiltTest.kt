package com.example.verygoodcore.marvel_compose

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.verygoodcore.marvel_compose.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
open class ComposableHiltTest {
    @get:Rule(order = 1)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    open fun setup() {
        hiltTestRule.inject()
    }
}