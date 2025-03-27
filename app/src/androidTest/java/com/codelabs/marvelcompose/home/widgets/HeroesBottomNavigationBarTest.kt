package com.codelabs.marvelcompose.home.widgets

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.home.viewmodel.SectionViewModel
import com.codelabs.marvelcompose.ui.theme.MainTheme
import org.junit.Rule
import org.junit.Test

internal class HeroesBottomNavigationBarTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun heroesBottomNavigationBar_displaysCorrectItems() {
        val sectionViewModel = SectionViewModel().apply {
            selectedItem = HeroeBottomNavigationItem.Characters
        }

        composeTestRule.setContent {
            MainTheme {
                HeroesBottomNavigationBar(
                    sectionViewModel = sectionViewModel,
                    items = listOf(
                        HeroeBottomNavigationItem.Characters,
                        HeroeBottomNavigationItem.Comics,
                        HeroeBottomNavigationItem.Series,
                        HeroeBottomNavigationItem.Stories,
                    )
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
        val sectionViewModel = SectionViewModel().apply {
            selectedItem = HeroeBottomNavigationItem.Comics
        }

        composeTestRule.setContent {
            MainTheme {
                HeroesBottomNavigationBar(
                    sectionViewModel = sectionViewModel,
                    items = listOf(
                        HeroeBottomNavigationItem.Characters,
                        HeroeBottomNavigationItem.Comics,
                        HeroeBottomNavigationItem.Series,
                        HeroeBottomNavigationItem.Stories,
                    )
                )
            }
        }

        val comics = composeTestRule.activity.getString(R.string.menu_comics)
        composeTestRule.onNode(hasText(comics) and hasClickAction())
            .assertIsSelected()
    }


    @Test
    fun heroesBottomNavigationBar_itemClickUpdatesSelection() {
        val sectionViewModel = SectionViewModel().apply {
            selectedItem = HeroeBottomNavigationItem.Characters
        }

        composeTestRule.setContent {
            MainTheme {
                HeroesBottomNavigationBar(
                    sectionViewModel = sectionViewModel,
                    items = listOf(
                        HeroeBottomNavigationItem.Characters,
                        HeroeBottomNavigationItem.Comics,
                        HeroeBottomNavigationItem.Series,
                        HeroeBottomNavigationItem.Stories,
                    )
                )
            }
        }

        val characters = composeTestRule.activity.getString(R.string.menu_characters)
        val comics = composeTestRule.activity.getString(R.string.menu_comics)

        composeTestRule.onNode(hasText(characters) and hasClickAction())
            .assertIsSelected()

        composeTestRule.onNode(hasText(comics) and hasClickAction()).performClick()

        composeTestRule.onNode(hasText(comics) and hasClickAction())
            .assertIsSelected()

        composeTestRule.onNode(hasText(characters) and hasClickAction())
            .assertIsNotSelected()
    }
}