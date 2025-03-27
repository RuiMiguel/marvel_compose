package com.codelabs.marvelcompose.di

import com.codelabs.api_client.client.ApiClient
import com.codelabs.api_client.service.CharacterService
import com.codelabs.api_client.service.ComicService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideComicService(
        apiClient: ApiClient
    ): ComicService {
        return ComicService(apiClient = apiClient)
    }

    @Provides
    @Singleton
    fun provideCharacterService(
        apiClient: ApiClient
    ): CharacterService {
        return CharacterService(apiClient = apiClient)
    }
}