package com.example.verygoodcore.secure_storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.verygoodcore.secure_storage.exception.StorageException
import com.example.verygoodcore.secure_storage.exception.StorageException.ReadException
import com.example.verygoodcore.secure_storage.exception.StorageException.WriteException
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SecureStorageTest() {
    @get:Rule
    val testCoroutinesRule = TestCoroutinesRule()

    private val testContext: Context = ApplicationProvider.getApplicationContext()

    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testCoroutinesRule.testScope,
            produceFile = { testContext.preferencesDataStoreFile("test_credentials") }
        )

    private val secureStorage = SecureStorage(testDataStore)

    @Before
    fun init() {
    }

    @After
    fun cleanUp() {
        testCoroutinesRule.testScope.runTest {
            testDataStore.edit { it.clear() }
        }
    }

    @Test
    fun canBeInstantiated() {
        checkNotNull(SecureStorage(testDataStore))
    }

    @Test
    fun readPrivateKeySucceeded(): Unit = runTest {
        testDataStore.edit { preferences ->
            preferences[SecureStorage.CredentialsKeys.PRIVATE_KEY] = "privateKey"
        }

        assertEquals("privateKey", secureStorage.privateKey())
    }

    @Test
    fun readPrivateKeyThrowsReadExceptionWhenNull(): Unit = runTest {
        try {
            testDataStore.edit { preferences ->
                preferences[SecureStorage.CredentialsKeys.PRIVATE_KEY] = ""
            }

            secureStorage.privateKey()
            fail("Expecting exception but none was thrown.");
        } catch (exception: ReadException) {
            assertEquals("Empty privateKey", exception.message)
        }
    }

    @Test
    fun readPrivateKeyThrowsReadExceptionWhenStorageFails(): Unit = runTest {
        try {
            testDataStore.edit { preferences ->
                preferences[booleanPreferencesKey(SecureStorage.CredentialsKeys.PRIVATE_KEY.name)] = true
            }

            secureStorage.privateKey()
            fail("Expecting exception but none was thrown.");
        } catch (exception: ReadException) {
            assert(exception is ReadException)
        }
    }

    @Test
    fun readPublicKeySucceeded(): Unit = runTest {
        testDataStore.edit { preferences ->
            preferences[SecureStorage.CredentialsKeys.PUBLIC_KEY] = "publicKey"
        }

        assertEquals("publicKey", secureStorage.publicKey())
    }

    @Test
    fun readPublicKeyThrowsReadExceptionWhenNull(): Unit = runTest {
        try {
            testDataStore.edit { preferences ->
                preferences[SecureStorage.CredentialsKeys.PUBLIC_KEY] = ""
            }

            secureStorage.publicKey()
            fail("Expecting exception but none was thrown.");
        } catch (exception: StorageException) {
            assert(exception is ReadException)
            assertEquals("Empty publicKey", exception.message)
        }
    }

    @Test
    fun readPublicKeyThrowsReadExceptionWhenStorageFails(): Unit = runTest {
        try {
            testDataStore.edit { preferences ->
                preferences[booleanPreferencesKey(SecureStorage.CredentialsKeys.PUBLIC_KEY.name)] = true
            }

            secureStorage.publicKey()
            fail("Expecting exception but none was thrown.");
        } catch (exception: StorageException) {
            assert(exception is ReadException)
        }
    }

    @Test
    fun saveCredentialsSucceeded(): Unit = runTest {
        secureStorage.saveCredentials(privateKey = "privateKey", publicKey = "publicKey")

        val privateKey = testDataStore.data.first().toPreferences()[SecureStorage.CredentialsKeys.PRIVATE_KEY]
        val publicKey = testDataStore.data.first().toPreferences()[SecureStorage.CredentialsKeys.PUBLIC_KEY]

        assertEquals("privateKey", privateKey)
        assertEquals("publicKey", publicKey)
    }

    @Test
    fun saveCredentialsThrowsWriteExceptionWhenStorageFails(): Unit = runTest {
        try {
            val testDataStore = mockk<DataStore<Preferences>>()
            coEvery { testDataStore.edit {} } throws WriteException("")

            val secureStorage = SecureStorage(testDataStore)

            secureStorage.saveCredentials(privateKey = "privateKey", publicKey = "publicKey")
            fail("Expecting exception but none was thrown.");
        } catch (exception: StorageException) {
            assert(exception is WriteException)
        }
    }

    @Test
    fun clearCredentialsSucceeded(): Unit = runTest {
        testDataStore.edit { preferences ->
            preferences[SecureStorage.CredentialsKeys.PRIVATE_KEY] = "privateKey"
            preferences[SecureStorage.CredentialsKeys.PUBLIC_KEY] = "publicKey"
        }

        secureStorage.clearCredentials()

        val privateKey = testDataStore.data.first().toPreferences()[SecureStorage.CredentialsKeys.PRIVATE_KEY]
        val publicKey = testDataStore.data.first().toPreferences()[SecureStorage.CredentialsKeys.PUBLIC_KEY]

        assertEquals("", privateKey)
        assertEquals("", publicKey)
    }

    @Test
    fun clearCredentialsThrowsWriteExceptionWhenStorageFails(): Unit = runTest {
        try {
            val testDataStore = mockk<DataStore<Preferences>>()
            coEvery { testDataStore.edit {} } throws WriteException("")

            val secureStorage = SecureStorage(testDataStore)

            secureStorage.clearCredentials()
            fail("Expecting exception but none was thrown.");
        } catch (exception: StorageException) {
            assert(exception is WriteException)
        }
    }
}