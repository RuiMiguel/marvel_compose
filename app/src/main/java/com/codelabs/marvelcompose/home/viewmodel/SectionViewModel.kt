package com.codelabs.marvelcompose.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.codelabs.marvelcompose.home.widgets.HeroeBottomNavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SectionViewModel @Inject constructor(): ViewModel() {
    var selectedItem by mutableStateOf<HeroeBottomNavigationItem>(HeroeBottomNavigationItem.Characters)
}