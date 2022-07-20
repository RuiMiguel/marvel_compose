package com.example.verygoodcore.marvel_compose.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.verygoodcore.marvel_compose.home.widget.HeroeBottomNavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SectionViewModel @Inject constructor(): ViewModel() {
    var selectedItem by mutableStateOf<HeroeBottomNavigationItem>(HeroeBottomNavigationItem.Characters)
}