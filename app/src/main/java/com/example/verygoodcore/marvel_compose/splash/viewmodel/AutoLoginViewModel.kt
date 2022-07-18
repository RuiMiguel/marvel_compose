package com.example.verygoodcore.marvel_compose.splash.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class AutoLoginViewModel : ViewModel() {
    private var _state = mutableStateOf(AutoLoginState())
    val state: State<AutoLoginState>
        get() = _state

    fun autoLogin() {
        _state.value = _state.value.copy(status = AutoLoginStatus.loading)

        viewModelScope.async {
            delay(5000)
            _state.value = _state.value.copy(status = AutoLoginStatus.error)
        }
    }
}

enum class AutoLoginStatus { initial, loading, success, error }
data class AutoLoginState(val status: AutoLoginStatus = AutoLoginStatus.initial)