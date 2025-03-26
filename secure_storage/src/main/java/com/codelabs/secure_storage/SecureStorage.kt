package com.codelabs.secure_storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.codelabs.secure_storage.exception.StorageException.ReadException
import com.codelabs.secure_storage.exception.StorageException.WriteException
import kotlinx.coroutines.flow.first

class SecureStorage(private val dataStore: DataStore<Preferences>) {
    suspend fun privateKey(): String {
        try {
            val value = dataStore.data.first().toPreferences()[PRIVATE_KEY]
            if (value.isNullOrEmpty()) {
                throw ReadException("Empty privateKey")
            }
            return value
        } catch (exception: Exception) {
            throw ReadException(exception.message)
        }
    }

    suspend fun publicKey(): String {
        try {
            val value = dataStore.data.first().toPreferences()[PUBLIC_KEY]
            if (value.isNullOrEmpty()) {
                throw ReadException("Empty publicKey")
            }
            return value
        } catch (exception: Exception) {
            throw ReadException(exception.message)
        }
    }

    suspend fun saveCredentials(privateKey: String, publicKey: String) {
        try {
            dataStore.edit { preferences ->
                preferences[PRIVATE_KEY] = privateKey
                preferences[PUBLIC_KEY] = publicKey
            }
        } catch (exception: Exception) {
            throw WriteException(exception.message)
        }
    }

    suspend fun clearCredentials() {
        try {
            dataStore.edit { preferences ->
                preferences[PRIVATE_KEY] = ""
                preferences[PUBLIC_KEY] = ""
            }
        } catch (exception: Exception) {
            throw WriteException(exception.message)
        }
    }

    companion object CredentialsKeys {
        val PRIVATE_KEY = stringPreferencesKey("__privateKeyField__")
        val PUBLIC_KEY = stringPreferencesKey("__publicKeyField__")
    }
}