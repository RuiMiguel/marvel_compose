package com.codelabs.marvelcompose.di

import com.codelabs.authentication_repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherModule::class]
)
object TestModule {

    @Provides
    @Singleton
    fun provideTestDispatcher(): CoroutineDispatcher = StandardTestDispatcher()

    @Provides
    @Singleton
    fun provideAuthenticationRepository(): AuthenticationRepository = mockk(relaxed = true)
}