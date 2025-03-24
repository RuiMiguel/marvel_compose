package com.codelabs.authentication_repository

import com.codelabs.authentication_repository.model.PrivateKey
import com.codelabs.authentication_repository.model.PublicKey
import com.codelabs.authentication_repository.model.User
import com.codelabs.secure_storage.SecureStorage
import com.codelabs.secure_storage.exception.StorageException.ReadException
import com.codelabs.secure_storage.exception.StorageException.WriteException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows

internal class AuthenticationRepositoryTest {
    private val secureStorage: SecureStorage = mockk()
    private val authenticationRepository: AuthenticationRepository =
        AuthenticationRepository(secureStorage)

    @Test
    fun `can be instantiated`() {
        assertNotNull(AuthenticationRepository(secureStorage))
    }

    @Test
    fun `returns privateKey from storage`(): Unit = runTest {
        coEvery { secureStorage.privateKey() } returns "privateKey"
        assertEquals(authenticationRepository.privateKey(), "privateKey")
    }

    @Test
    fun `throws ReadException when secureStorage privateKey fails`(): Unit = runTest {
        coEvery { secureStorage.privateKey() } throws ReadException("error")
        assertThrows<ReadException> {
            authenticationRepository.privateKey()
        }
    }

    @Test
    fun `returns publicKey from storage`(): Unit = runTest {
        coEvery { secureStorage.publicKey() } returns "publicKey"
        assertEquals(authenticationRepository.publicKey(), "publicKey")
    }

    @Test
    fun `throws ReadException when secureStorage publicKey fails`() = runTest {
        coEvery { secureStorage.publicKey() } throws ReadException("error")
        assertThrows<ReadException> {
            authenticationRepository.publicKey()
        }
    }

    @Nested
    inner class Login {
        @Test
        fun `completes when secureStorage saveCredentials succeeded and emit User`(): Unit =
            runTest {
                coEvery {
                    secureStorage.saveCredentials(
                        privateKey = "privateKey",
                        publicKey = "publicKey"
                    )
                } returns Unit

                assertEquals(
                    authenticationRepository.login(
                        privateKey = PrivateKey("privateKey"),
                        publicKey = PublicKey("publicKey")
                    ),
                    Unit,
                )

                assertEquals(
                    authenticationRepository.user.first(),
                    User(
                        privateKey = PrivateKey("privateKey"),
                        publicKey = PublicKey("publicKey"),
                    )
                )
            }

        @Test
        fun `throws WriteException when secureStorage saveCredentials fails and emit anonymous`() =
            runTest {
                coEvery {
                    secureStorage.saveCredentials(
                        privateKey = any(),
                        publicKey = any()
                    )
                } throws WriteException("error")
                assertThrows<WriteException> {
                    authenticationRepository.login(
                        privateKey = PrivateKey("privateKey"),
                        publicKey = PublicKey("publicKey")
                    )
                    assertEquals(authenticationRepository.user.first(), User.anonymous())
                }
            }
    }

    @Nested
    inner class Logout {
        @Test
        fun `completes when secureStorage clearCredentials succeeded and emit anonymous`(): Unit =
            runTest {
                coEvery { secureStorage.clearCredentials() } returns Unit
                assertEquals(authenticationRepository.logout(), Unit)
                assertEquals(authenticationRepository.user.first(), User.anonymous())
            }

        @Test
        fun `throws WriteException when secureStorage clearCredentials fails`() = runTest {
            coEvery { secureStorage.clearCredentials() } throws WriteException("error")
            assertThrows<WriteException> {
                authenticationRepository.logout()
            }
        }
    }
}