package com.example.verygoodcore.marvel_compose.splash.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.verygoodcore.authentication_repository.AuthenticationRepository
import com.example.verygoodcore.authentication_repository.model.PrivateKey
import com.example.verygoodcore.authentication_repository.model.PublicKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class AutoLoginViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) : ViewModel() {
    private var _state = mutableStateOf(AutoLoginState())
    val state: State<AutoLoginState>
        get() = _state

    fun autoLogin() {
        _state.value = _state.value.copy(status = AutoLoginStatus.loading)

        viewModelScope.async {
            try {
                val privateKey = authenticationRepository.privateKey()
                val publicKey = authenticationRepository.publicKey()

                authenticationRepository.login(
                    privateKey = PrivateKey(privateKey),
                    publicKey = PublicKey(publicKey)
                )
                _state.value = _state.value.copy(status = AutoLoginStatus.success)
            } catch (exception: Exception) {
                _state.value = _state.value.copy(status = AutoLoginStatus.error)
            }
        }
    }
}

enum class AutoLoginStatus { initial, loading, success, error }
data class AutoLoginState(val status: AutoLoginStatus = AutoLoginStatus.initial)