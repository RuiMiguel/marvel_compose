package com.codelabs.marvelcompose.di


import com.codelabs.authentication_repository.AuthenticationRepository
import com.codelabs.secure_storage.SecureStorage
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