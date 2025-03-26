package com.codelabs.marvelcompose.di

import com.codelabs.api_client.client.ApiClient
import com.codelabs.api_client.service.CharacterService
import com.codelabs.api_client.service.ComicService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ServiceModule {

    @Provides
    @ViewModelScoped
    fun provideComicService(
        apiClient: ApiClient
    ): ComicService {
        return ComicService(apiClient = apiClient)
    }

    @Provides
    @ViewModelScoped
    fun provideCharacterService(
        apiClient: ApiClient
    ): CharacterService {
        return CharacterService(apiClient = apiClient)
    }
}