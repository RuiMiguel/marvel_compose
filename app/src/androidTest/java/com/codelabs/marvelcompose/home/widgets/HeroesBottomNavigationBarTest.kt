package com.codelabs.marvelcompose.home.widgets

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.ui.theme.MainTheme
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test

internal class HeroesBottomNavigationBarTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val items = persistentListOf(
        HeroesBottomNavigationItem.Characters,
        HeroesBottomNavigationItem.Comics,
        HeroesBottomNavigationItem.Series,
        HeroesBottomNavigationItem.Stories,
    )

    @Test
    fun heroesBottomNavigationBar_displaysCorrectItems() {
        composeTestRule.setContent {
            MainTheme {
                HeroesBottomNavigationBar(
                    isItemSelected = { false },
                    onItemClick = {},
                    items = items
                )
            }
        }

        val characters = composeTestRule.activity.getString(R.string.menu_characters)
        val comics = composeTestRule.activity.getString(R.string.menu_comics)
        val series = composeTestRule.activity.getString(R.string.menu_series)
        val stories = composeTestRule.activity.getString(R.string.menu_stories)

        composeTestRule.onNodeWithText(characters).assertIsDisplayed()
        composeTestRule.onNodeWithText(comics).assertIsDisplayed()
        composeTestRule.onNodeWithText(series).assertIsDisplayed()
        composeTestRule.onNodeWithText(stories).assertIsDisplayed()
    }

    @Test
    fun heroesBottomNavigationBar_showsSelectedItemCorrectly() {
        composeTestRule.setContent {
            MainTheme {
                HeroesBottomNavigationBar(
                    isItemSelected = { it == HeroesBottomNavigationItem.Comics },
                    onItemClick = {},
                    items = items
                )
            }
        }

        val comics = composeTestRule.activity.getString(R.string.menu_comics)
        composeTestRule.onNode(hasText(comics) and hasClickAction())
            .assertIsSelected()
    }

    @Test
    fun bottomNavigationBar_callsOnItemClickWhenItemIsClicked() {
        var clickedItem: HeroesBottomNavigationItem? = null

        composeTestRule.setContent {
            HeroesBottomNavigationBar(
                items = items,
                onItemClick = { clickedItem = it },
                isItemSelected = { it == HeroesBottomNavigationItem.Comics }
            )
        }

        val series = composeTestRule.activity.getString(R.string.menu_series)
        composeTestRule.onNodeWithText(series).performClick()

        assert(clickedItem == HeroesBottomNavigationItem.Series)
    }
}