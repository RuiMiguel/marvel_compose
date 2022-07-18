package com.example.verygoodcore.marvel_compose.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.verygoodcore.marvel_compose.home.widget.HeroeBottomNavigationItem

class SectionViewModel : ViewModel() {
    var selectedItem by mutableStateOf<HeroeBottomNavigationItem>(HeroeBottomNavigationItem.Characters)
}