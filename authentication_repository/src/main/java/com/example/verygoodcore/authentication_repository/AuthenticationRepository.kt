package com.example.verygoodcore.authentication_repository

import com.example.verygoodcore.authentication_repository.model.PrivateKey
import com.example.verygoodcore.authentication_repository.model.PublicKey
import com.example.verygoodcore.authentication_repository.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class AuthenticationRepository(private val secureStorage: SecureStorage) {
    private var _user = MutableStateFlow(User.anonymous())
    val user: StateFlow<User> = _user

    suspend fun login(privateKey: PrivateKey, publicKey: PublicKey)  = flow<User> {
        // TODO(ruimiguel): save private and public into secureStorage
        delay(3000)
        emit(User(
            privateKey = PrivateKey("privateKey"),
            publicKey = PublicKey("publicKey")
        ))
        // TODO(ruimiguel): if exception, emit User.anonymous
    }

    suspend fun logout() = flow<User>{
        // TODO(ruimiguel): clear private and public from secureStorage
        delay(2000)
        emit(User.anonymous())
        // TODO(ruimiguel): if exception throw it
    }
}