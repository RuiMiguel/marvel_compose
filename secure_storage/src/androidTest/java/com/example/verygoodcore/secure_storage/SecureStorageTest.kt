package com.example.verygoodcore.secure_storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.verygoodcore.secure_storage.exception.StorageException.ReadException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SecureStorageTest() {
    @get:Rule
    val testCoroutinesRule = TestCoroutinesRule()

    private val testContext: Context = ApplicationProvider.getApplicationContext()

    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testCoroutinesRule.testScope,
            produceFile = { testContext.preferencesDataStoreFile("credentials") }
        )

    private val secureStorage = SecureStorage(testContext)

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
        checkNotNull(SecureStorage(testContext))
    }

    @Test
    fun succeeded(): Unit = runTest {
        testDataStore.edit { preferences ->
            preferences[SecureStorage.CredentialsKeys.PUBLIC_KEY] = "publicKey"
        }

        val pref = testDataStore.data.first().toPreferences()
        assertEquals(secureStorage.publicKey.first(), "publicKey")
    }

    @Test
    fun throwsReadExceptionWhenNull(): Unit = runTest {
        try {
            testDataStore.edit { preferences ->
                preferences[stringPreferencesKey("other")] = "value"
            }

            secureStorage.publicKey.collect {
                assertEquals("publicKey", it)
                fail()
            }
        } catch (exception: ReadException) {
            assertEquals("Empty publicKey", exception.message)
        }
    }

    @Test
    fun throwsReadExceptionWhenStorageFails(): Unit = runTest {
        try {
            testDataStore.edit { preferences ->
                preferences[booleanPreferencesKey("publicKey")] = true
            }

            secureStorage.publicKey.collect {
                assertEquals("publicKey", it)
                fail()
            }
        } catch (exception: ReadException) {
        }
    }
}