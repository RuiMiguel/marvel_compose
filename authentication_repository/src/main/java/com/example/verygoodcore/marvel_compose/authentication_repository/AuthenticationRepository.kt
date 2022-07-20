package com.example.verygoodcore.marvel_compose.authentication_repository

import com.example.verygoodcore.marvel_compose.authentication_repository.model.PrivateKey
import com.example.verygoodcore.marvel_compose.authentication_repository.model.PublicKey
import com.example.verygoodcore.marvel_compose.authentication_repository.model.User
import com.example.verygoodcore.marvel_compose.secure_storage.SecureStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthenticationRepository(private val secureStorage: SecureStorage) {
    private var _user = MutableStateFlow(User.anonymous())
    val user: StateFlow<User> = _user

    suspend fun privateKey() = secureStorage.privateKey()

    suspend fun publicKey() = secureStorage.publicKey()

    suspend fun login(privateKey: PrivateKey, publicKey: PublicKey) {
        try {
            secureStorage.saveCredentials(privateKey = privateKey.key, publicKey = publicKey.key)
            delay(3000)
            _user.emit(User(
                privateKey = PrivateKey("privateKey"),
                publicKey = PublicKey("publicKey")
            ))
        } catch (exception: Exception) {
            _user.emit(User.anonymous())
            throw exception
        }
    }

    suspend fun logout() {
        secureStorage.clearCredentials()
        delay(2000)
        _user.emit(User.anonymous())
    }
}