package com.codelabs.marvelcompose.home.widgets

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.helpers.hasContentDescriptionAndRole
import com.codelabs.marvelcompose.ui.theme.MainTheme
import org.junit.Rule
import org.junit.Test

internal class HeroesAppBarTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun heroesAppBar_displaysCorrectly() {
        composeTestRule.setContent {
            MainTheme {
                HeroesAppBar()
            }
        }

        val contentDescription = composeTestRule.activity.getString(R.string.app_name)
        composeTestRule.onNode(hasContentDescriptionAndRole(contentDescription, Role.Image))
            .assertIsDisplayed()
    }

    @Test
    fun heroesAppBar_showsBackButton_whenWithBackIsTrue() {
        composeTestRule.setContent {
            MainTheme {
                HeroesAppBar(withBack = true)
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.back)
        ).assertIsDisplayed()
    }

    @Test
    fun heroesAppBar_showsLoginButton_whenWithActionsIsTrue() {
        composeTestRule.setContent {
            MainTheme {
                HeroesAppBar(withActions = true)
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.login)
        ).assertIsDisplayed()
    }

    @Test
    fun heroesAppBar_backButtonTriggersCallback() {
        var backClicked = false

        composeTestRule.setContent {
            MainTheme {
                HeroesAppBar(withBack = true, onBack = { backClicked = true })
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.back)
        ).performClick()

        assert(backClicked)
    }

    @Test
    fun heroesAppBar_loginButtonTriggersCallback() {
        var loginClicked = false

        composeTestRule.setContent {
            MainTheme {
                HeroesAppBar(withActions = true, onLoginAction = { loginClicked = true })
            }
        }

        composeTestRule.onNodeWithContentDescription(
            composeTestRule.activity.getString(R.string.login)
        ).performClick()

        assert(loginClicked)
    }
}