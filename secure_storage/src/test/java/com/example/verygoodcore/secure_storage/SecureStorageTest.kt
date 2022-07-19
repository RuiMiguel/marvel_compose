package com.example.verygoodcore.secure_storage

import android.content.Context
import com.example.verygoodcore.secure_storage.SecureStorage
import com.example.verygoodcore.secure_storage.exception.StorageException.ReadException
import com.example.verygoodcore.secure_storage.exception.StorageException.WriteException
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class SecureStorageTest {
    private val context: Context = mock()
    private val secureStorage: SecureStorage = SecureStorage(context)

    @Test
    fun `can be instantiated`() {
        assertThat(SecureStorage(context)).isNotNull
    }

    @Nested
    inner class PublicKey {
        @Test
        fun `returns value when read from storage succeeded`(): Unit = runTest {
            whenever(secureStorage.saveCredentials(privateKey = "privateKey", publicKey = "publicKey")).thenReturn(Unit)
            assertThat(secureStorage.publicKey).isEqualTo("publicKey")
        }

        @Test
        fun `throws ReadException when read from storage is null`(): Unit = runTest {

        }

        @Test
        fun `throws ReadException when read from storage fails`(): Unit = runTest {

        }
    }

    @Nested
    inner class PrivateKey {
        @Test
        fun `returns value when read from storage succeeded`(): Unit = runTest {
            whenever(secureStorage.clearCredentials()).thenReturn(Unit)
            assertThat(secureStorage.privateKey).isEqualTo("privateKey")
        }

        @Test
        fun `throws ReadException when read from storage is null`(): Unit = runTest {

        }

        @Test
        fun `throws ReadException when read from storage fails`(): Unit = runTest {

        }
    }

}