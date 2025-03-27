package com.codelabs.authentication_repository

import com.codelabs.authentication_repository.model.PrivateKey
import com.codelabs.authentication_repository.model.PublicKey
import com.codelabs.authentication_repository.model.User
import com.codelabs.secure_storage.SecureStorage
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
            delay(3000)
            secureStorage.saveCredentials(privateKey = privateKey.key, publicKey = publicKey.key)
            _user.emit(
                User(
                    privateKey = PrivateKey(privateKey.key),
                    publicKey = PublicKey(publicKey.key)
                )
            )
        } catch (exception: Exception) {
            _user.emit(User.anonymous())
            throw exception
        }
    }

    suspend fun logout() {
        delay(2000)
        secureStorage.clearCredentials()
        _user.emit(User.anonymous())
    }
}