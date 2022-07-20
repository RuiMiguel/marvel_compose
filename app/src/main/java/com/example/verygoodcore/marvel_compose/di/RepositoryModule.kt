package com.example.verygoodcore.marvel_compose.di

import com.example.verygoodcore.marvel_compose.authentication_repository.AuthenticationRepository
import com.example.verygoodcore.marvel_compose.secure_storage.SecureStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideAuthenticationRepository(
        secureStorage: SecureStorage
    ): AuthenticationRepository {
        return AuthenticationRepository(secureStorage = secureStorage)
    }
}