package com.example.verygoodcore.secure_storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.verygoodcore.secure_storage.exception.StorageException.ReadException
import com.example.verygoodcore.secure_storage.exception.StorageException.WriteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SecureStorage @Inject constructor(private val context: Context) {
    private val CREDENTIALS_NAME = "credentials"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = CREDENTIALS_NAME)

    val privateKey: Flow<String> = flow<String> {
        try {
            context.dataStore.data.map { preferences ->
                preferences[PRIVATE_KEY] ?: throw ReadException("Empty privateKey")
            }
        } catch (exception: IOException) {
            throw ReadException(exception.message)
        }
    }

    val publicKey: Flow<String> = flow<String> {
        try {
            context.dataStore.data.map { preferences ->
                preferences[PUBLIC_KEY] ?: throw ReadException("Empty publicKey")
            }
        } catch (exception: IOException) {
            throw ReadException(exception.message)
        }
    }

    suspend fun saveCredentials(privateKey: String, publicKey: String) {
        try {
            context.dataStore.edit { preferences ->
                preferences[PRIVATE_KEY] = privateKey
                preferences[PUBLIC_KEY] = publicKey
            }
        } catch (exception: IOException) {
            throw WriteException(exception.message)
        }
    }

    suspend fun clearCredentials() {
        try {
            context.dataStore.edit { preferences ->
                preferences[PRIVATE_KEY] = ""
                preferences[PUBLIC_KEY] = ""
            }
        } catch (exception: IOException) {
            throw WriteException(exception.message)
        }
    }

    companion object CredentialsKeys {
        val PRIVATE_KEY = stringPreferencesKey("__privateKeyField__")
        val PUBLIC_KEY = stringPreferencesKey("__publicKeyField__")
    }
}