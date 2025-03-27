package com.codelabs.marvelcompose.home.viewmodel

import com.codelabs.marvelcompose.home.widgets.HeroesBottomNavigationItem
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

internal class SectionViewModelTest {
    private lateinit var viewModel: SectionViewModel

    @Before
    fun setUp() {
        viewModel = SectionViewModel()
    }

    @Test
    fun `initial selected item is Characters`() {
        assertEquals(HeroesBottomNavigationItem.Characters, viewModel.selectedNavigationItem)
    }

    @Test
    fun `selectedItem can be updated`() {
        viewModel.selectNavigationItem(HeroesBottomNavigationItem.Comics)
        assertEquals(HeroesBottomNavigationItem.Comics, viewModel.selectedNavigationItem)

        viewModel.selectNavigationItem(HeroesBottomNavigationItem.Characters)
        assertEquals(HeroesBottomNavigationItem.Characters, viewModel.selectedNavigationItem)
    }
}