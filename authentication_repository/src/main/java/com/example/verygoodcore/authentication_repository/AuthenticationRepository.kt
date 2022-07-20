package com.example.verygoodcore.authentication_repository

import com.example.verygoodcore.authentication_repository.model.PrivateKey
import com.example.verygoodcore.authentication_repository.model.PublicKey
import com.example.verygoodcore.authentication_repository.model.User
import com.example.verygoodcore.secure_storage.SecureStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val secureStorage: SecureStorage) {
    private var _user = MutableStateFlow(User.anonymous())
    val user: StateFlow<User> = _user

    suspend fun privateKey() = secureStorage.privateKey.first()

    suspend fun publicKey() = secureStorage.publicKey.first()

    suspend fun login(privateKey: PrivateKey, publicKey: PublicKey) {
        secureStorage.saveCredentials(privateKey = privateKey.key, publicKey = publicKey.key)
        delay(3000)
        _user.emit(User(
            privateKey = PrivateKey("privateKey"),
            publicKey = PublicKey("publicKey")
        ))
    }

    suspend fun logout() {
        secureStorage.clearCredentials()
        delay(2000)
        _user.emit(User.anonymous())
    }
}