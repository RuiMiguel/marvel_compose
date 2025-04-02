package com.codelabs.marvelcompose.splash.viewmodel

import androidx.lifecycle.viewModelScope
import com.codelabs.authentication_repository.AuthenticationRepository
import com.codelabs.authentication_repository.model.PrivateKey
import com.codelabs.authentication_repository.model.PublicKey
import com.codelabs.marvelcompose.base.viewmodel.BaseViewModel
import com.codelabs.marvelcompose.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and logic of the Splash screen.
 *
 * This ViewModel handles the automatic login process during the app's launch.
 * It communicates with the [AuthenticationRepository] to retrieve keys and
 * attempt a silent login. The state of the login process is exposed through
 * the [state] property, allowing the UI to react to different stages
 * (e.g., loading, success, error).
 *
 * @property authenticationRepository The repository responsible for authentication-related operations.
 * @property dispatcher The coroutine dispatcher used for background operations.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel(dispatcher) {

    private val _state = MutableStateFlow<SplashStatus>(SplashStatus.Initial)
    val state: StateFlow<SplashStatus> = _state

    fun autoLogin() {
        _state.value = SplashStatus.Loading

        viewModelScope.launch(coroutineContext) {
            try {
                // Execute both key functions concurrently
                val (privateKey, publicKey) = coroutineScope {
                    val privateKeyDeferred = async { authenticationRepository.privateKey() }
                    val publicKeyDeferred = async { authenticationRepository.publicKey() }
                    privateKeyDeferred.await() to publicKeyDeferred.await()
                }

                authenticationRepository.login(
                    privateKey = PrivateKey(privateKey),
                    publicKey = PublicKey(publicKey)
                )
                _state.value = SplashStatus.Success
            } catch (exception: Exception) {
                _state.value = SplashStatus.Error(message = exception.message)
            }
        }
    }
}

/**
 * Represents the different states of the splash screen.
 *
 * This sealed class defines the possible states that the splash screen can be in.
 * These states are used to manage the UI and behavior of the splash screen,
 * such as showing a loading indicator, displaying an error message, or
 * transitioning to the next screen upon success.
 */
sealed class SplashStatus {
    data object Initial : SplashStatus()
    data object Loading : SplashStatus()
    data object Success : SplashStatus()
    data class Error(val message: String?) : SplashStatus()
}