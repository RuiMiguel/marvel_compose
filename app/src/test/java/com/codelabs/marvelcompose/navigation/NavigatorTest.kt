package com.codelabs.marvelcompose.navigation

import androidx.navigation.NavHostController
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class NavigatorTest {
    private lateinit var navController: NavHostController
    private lateinit var navigator: Navigator

    @BeforeEach
    fun setUp() {
        navController = mockk {
            every { previousBackStackEntry } returns mockk()
        }
        navigator = Navigator(navController)
    }

    @Test
    fun `canPop returns true when there is a previous back stack entry`() {
        assert(navigator.canPop)
    }

    @Test
    fun `canPop returns false when there is no previous back stack entry`() {
        navController = mockk {
            every { previousBackStackEntry } returns null
        }
        navigator = Navigator(navController)

        assert(!navigator.canPop)
    }

    @Test
    fun `goBack emits GoBack event`() = runTest {
        navigator.goBack()
        val emittedEvent = navigator.sharedFlow.replayCache.lastOrNull()
        assertEquals(PageRoute.GoBack, emittedEvent)
    }

    @Test
    fun `fromSplashToHome emits Home event`() {
        navigator.fromSplashToHome()
        val emittedEvent = navigator.sharedFlow.replayCache.lastOrNull()
        assertEquals(PageRoute.Home, emittedEvent)
    }

    @Test
    fun `fromSplashToLogin emits Login event`() {
        navigator.fromSplashToLogin()
        val emittedEvent = navigator.sharedFlow.replayCache.lastOrNull()
        assertEquals(PageRoute.Login, emittedEvent)
    }

    @Test
    fun `toLogin emits Login event`() {
        navigator.toLogin()
        val emittedEvent = navigator.sharedFlow.replayCache.lastOrNull()
        assertEquals(PageRoute.Login, emittedEvent)
    }

    @Test
    fun `toHome emits Home event`() {
        navigator.toHome()
        val emittedEvent = navigator.sharedFlow.replayCache.lastOrNull()
        assertEquals(PageRoute.Home, emittedEvent)
    }
}