package com.codelabs.marvelcompose.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.codelabs.marvelcompose.home.widgets.HeroesBottomNavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SectionViewModel @Inject constructor(): ViewModel() {
    private var _selectedNavigationItem by mutableStateOf<HeroesBottomNavigationItem>(HeroesBottomNavigationItem.Characters)
    val selectedNavigationItem: HeroesBottomNavigationItem
        get() = _selectedNavigationItem

    fun selectNavigationItem(item: HeroesBottomNavigationItem) {
        _selectedNavigationItem = item
    }
}