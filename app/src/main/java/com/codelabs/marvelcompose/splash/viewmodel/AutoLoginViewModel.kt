package com.codelabs.marvelcompose.splash.viewmodel

import androidx.lifecycle.viewModelScope
import com.codelabs.authentication_repository.AuthenticationRepository
import com.codelabs.authentication_repository.model.PrivateKey
import com.codelabs.authentication_repository.model.PublicKey
import com.codelabs.marvelcompose.base.BaseViewModel
import com.codelabs.marvelcompose.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutoLoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel(dispatcher) {

    private val _state = MutableStateFlow<AutoLoginState>(AutoLoginState.Initial)
    val state: StateFlow<AutoLoginState> = _state

    fun autoLogin() {
        _state.value = AutoLoginState.Loading

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
                _state.value = AutoLoginState.Success
            } catch (exception: Exception) {
                _state.value = AutoLoginState.Error(message = exception.message)
            }
        }
    }
}

sealed class AutoLoginState {
    data object Initial : AutoLoginState()
    data object Loading : AutoLoginState()
    data object Success : AutoLoginState()
    data class Error(val message: String?) : AutoLoginState()
}