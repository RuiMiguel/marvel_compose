package com.codelabs.marvelcompose.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.codelabs.authentication_repository.AuthenticationRepository
import com.codelabs.authentication_repository.model.PrivateKey
import com.codelabs.authentication_repository.model.PublicKey
import com.codelabs.marvelcompose.base.BaseViewModel
import com.codelabs.marvelcompose.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel(dispatcher) {

    private val _status = MutableStateFlow<LoginStatus>(LoginStatus.Initial)
    val status: StateFlow<LoginStatus> = _status

    private val _publicKey = MutableStateFlow("")
    val publicKey: StateFlow<String> = _publicKey


    private val _privateKey = MutableStateFlow("")
    val privateKey: StateFlow<String> = _privateKey

    fun publicKeyChange(key: String) {
        _publicKey.value = key
    }

    fun privateKeyChange(key: String) {
        _privateKey.value = key
    }

    fun login() {
        _status.value = LoginStatus.Loading

        viewModelScope.launch(coroutineContext) {
            try {
                val privateKey = privateKey.value
                val publicKey = publicKey.value

                authenticationRepository.login(
                    privateKey = PrivateKey(privateKey),
                    publicKey = PublicKey(publicKey),
                )
                _status.value = LoginStatus.Success
            } catch (exception: Exception) {
                _status.value = LoginStatus.Error
            }
        }
    }

    fun logout() {
        _status.value = LoginStatus.Loading

        viewModelScope.launch(coroutineContext) {
            try {
                authenticationRepository.logout()
                _status.value = LoginStatus.Success
                publicKeyChange("")
                privateKeyChange("")
            } catch (exception: Exception) {
                _status.value = LoginStatus.Error
            }
        }
    }
}

sealed class LoginStatus {
    data object Initial : LoginStatus()
    data object Loading : LoginStatus()
    data object Success : LoginStatus()
    data object Error : LoginStatus()

    val isLoading: Boolean
        get() = this is Loading
}