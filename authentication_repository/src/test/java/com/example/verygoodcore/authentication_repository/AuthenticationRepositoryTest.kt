package com.example.verygoodcore.authentication_repository

import com.example.verygoodcore.authentication_repository.model.PrivateKey
import com.example.verygoodcore.authentication_repository.model.PublicKey
import com.example.verygoodcore.secure_storage.SecureStorage
import com.example.verygoodcore.secure_storage.exception.StorageException.ReadException
import com.example.verygoodcore.secure_storage.exception.StorageException.WriteException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AuthenticationRepositoryTest {
    private val secureStorage: SecureStorage = mock()
    private val authenticationRepository: AuthenticationRepository = AuthenticationRepository(secureStorage)

    @Test
    fun `can be instantiated`() {
        assertThat(AuthenticationRepository(secureStorage)).isNotNull
    }

    @Test
    fun `returns privateKey from storage`(): Unit = runTest {
        whenever(secureStorage.privateKey).thenReturn(flowOf("privateKey"))
        assertThat(authenticationRepository.privateKey()).isEqualTo("privateKey")
    }

    @Test
    fun `throws ReadException when secureStorage privateKey fails`(): Unit = runTest {
        whenever(secureStorage.privateKey).thenThrow(ReadException("error"))
        assertThrows<ReadException> {
            authenticationRepository.privateKey()
        }
    }

    @Test
    fun `returns publicKey from storage`(): Unit = runTest {
        whenever(secureStorage.publicKey).thenReturn(flowOf("publicKey"))
        assertThat(authenticationRepository.publicKey()).isEqualTo("publicKey")
    }

    @Test
    fun `throws ReadException when secureStorage publicKey fails`() = runTest {
        whenever(secureStorage.publicKey).thenThrow(ReadException("error"))
        assertThrows<ReadException> {
            authenticationRepository.publicKey()
        }
    }

    @Nested
    inner class Login {
        @Test
        fun `completes when secureStorage saveCredentials succeeded`(): Unit = runTest {
            whenever(secureStorage.saveCredentials(privateKey = "privateKey", publicKey = "publicKey")).thenReturn(Unit)
            assertThat(authenticationRepository.login(privateKey = PrivateKey("privateKey"), publicKey = PublicKey("publicKey"))).isEqualTo(Unit)
        }

        @Test
        fun `throws WriteException when secureStorage saveCredentials fails`() = runTest {
            whenever(secureStorage.saveCredentials(privateKey = any(), publicKey = any())).thenThrow(WriteException("error"))
            assertThrows<WriteException> {
                authenticationRepository.login(privateKey = PrivateKey("privateKey"), publicKey = PublicKey("publicKey"))
            }
        }
    }

    @Nested
    inner class Logout {
        @Test
        fun `completes when secureStorage clearCredentials succeeded`(): Unit = runTest {
            whenever(secureStorage.clearCredentials()).thenReturn(Unit)
            assertThat(authenticationRepository.logout()).isEqualTo(Unit)
        }

        @Test
        fun `throws WriteException when secureStorage clearCredentials fails`() = runTest {
            whenever(secureStorage.clearCredentials()).thenThrow(WriteException("error"))
            assertThrows<WriteException> {
                authenticationRepository.logout()
            }
        }
    }

}