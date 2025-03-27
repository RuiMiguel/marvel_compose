package com.codelabs.marvelcompose.di

import com.codelabs.api_client.service.ComicService
import com.codelabs.authentication_repository.AuthenticationRepository
import com.codelabs.comic_repository.repository.ComicRepository
import com.codelabs.secure_storage.SecureStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideComicRepository(
        comicService: ComicService
    ): ComicRepository {
        return ComicRepository(comicService = comicService)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object SingletonRepositoryModule {
    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        secureStorage: SecureStorage
    ): AuthenticationRepository {
        return AuthenticationRepository(secureStorage = secureStorage)
    }
}
