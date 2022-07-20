package com.example.verygoodcore.marvel_compose.main.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.verygoodcore.authentication_repository.AuthenticationRepository
import com.example.verygoodcore.authentication_repository.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) : ViewModel() {
    private var _state = mutableStateOf(AuthenticationState())
    val state: State<AuthenticationState>
        get() = _state

    init {
        subscribe()
    }

    private fun subscribe() {
        viewModelScope.launch {
            authenticationRepository.user.collect {
                _state.value = if (it == User.anonymous()) {
                    AuthenticationState.authenticated(it)
                } else {
                    AuthenticationState.unauthenticated()
                }
            }
        }
    }
}

enum class AuthenticationStatus { authenticated, unauthenticated }
data class AuthenticationState(val status: AuthenticationStatus = AuthenticationStatus.unauthenticated, val user: User = User.anonymous()) {
    companion object {
        fun authenticated(user: User) = AuthenticationState(status = AuthenticationStatus.authenticated, user = user)
        fun unauthenticated() = AuthenticationState(status = AuthenticationStatus.unauthenticated, user = User.anonymous())
    }
}