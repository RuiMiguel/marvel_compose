package com.codelabs.marvelcompose.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelabs.authentication_repository.AuthenticationRepository
import com.codelabs.authentication_repository.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {
    private var _status =
        MutableStateFlow<AuthenticationStatus>(AuthenticationStatus.Unauthenticated)
    val status: StateFlow<AuthenticationStatus> = _status

    init {
        subscribe()
    }

    private fun subscribe() {
        viewModelScope.launch {
            authenticationRepository.user.collect { user ->
                _status.value = if (!user.isAnonymous()) {
                    AuthenticationStatus.Authenticated(user)
                } else {
                    AuthenticationStatus.Unauthenticated
                }
            }
        }
    }
}


sealed class AuthenticationStatus {
    abstract val user: User

    data class Authenticated(override val user: User) : AuthenticationStatus()

    data object Unauthenticated : AuthenticationStatus() {
        override val user: User = User.anonymous()
    }

    val isAuthenticated: Boolean
        get() = this is Authenticated

}