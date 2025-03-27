package com.codelabs.marvelcompose.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.codelabs.authentication_repository.AuthenticationRepository
import com.codelabs.authentication_repository.model.User
import com.codelabs.marvelcompose.base.viewmodel.BaseViewModel
import com.codelabs.marvelcompose.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the authentication state of the user.
 *
 * This ViewModel interacts with the [AuthenticationRepository] to monitor the user's
 * authentication status and provides a reactive [StateFlow] to observe changes in the
 * authentication state.
 *
 * @property authenticationRepository The repository responsible for handling authentication-related
 * operations.
 * @property dispatcher The coroutine dispatcher to be used for background operations.
 */
@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    @IODispatcher
    private val dispatcher: CoroutineDispatcher,
) : BaseViewModel(dispatcher) {

    private var _status =
        MutableStateFlow<AuthenticationStatus>(AuthenticationStatus.Unauthenticated)
    val status: StateFlow<AuthenticationStatus> = _status

    // Run subscription as soon as the view model is created, to get the current user state from the repository
    init {
        subscribe()
    }

    private fun subscribe() {
        viewModelScope.launch {
            authenticationRepository.user.collect { user ->
                // When the user state changes, emit the updated status
                _status.value = if (!user.isAnonymous()) {
                    AuthenticationStatus.Authenticated(user)
                } else {
                    AuthenticationStatus.Unauthenticated
                }
            }
        }
    }
}


/**
 * Represents the authentication status of a user.
 *
 * This sealed class encapsulates the different states a user can be in regarding authentication:
 * - [Authenticated]: The user is successfully authenticated and associated with a [User] object.
 * - [Unauthenticated]: The user is not authenticated. A default anonymous [User] is provided in this state.
 *
 * The class also provides a convenient property [isAuthenticated] to check if the current state represents an authenticated user.
 */
sealed class AuthenticationStatus {
    abstract val user: User

    data class Authenticated(override val user: User) : AuthenticationStatus()

    data object Unauthenticated : AuthenticationStatus() {
        override val user: User = User.anonymous()
    }

    val isAuthenticated: Boolean
        get() = this is Authenticated

}