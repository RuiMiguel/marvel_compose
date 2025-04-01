package com.codelabs.marvelcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherModule::class]
)
object TestDispatcherModule {
    @Provides
    @DefaultDispatcher
    fun provideTestDefaultDispatcher(): CoroutineDispatcher = StandardTestDispatcher()

    @Provides
    @IODispatcher
    fun provideTestIODispatcher(): CoroutineDispatcher = StandardTestDispatcher()
}