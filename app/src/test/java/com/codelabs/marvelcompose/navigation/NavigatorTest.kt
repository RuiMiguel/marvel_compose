package com.codelabs.marvelcompose.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.amshove.kluent.internal.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class NavigatorTest {
    private lateinit var mockNavController: NavHostController
    private lateinit var navigator: Navigator

    @BeforeEach
    fun setup() {
        mockNavController = mockk {
            every { popBackStack() } returns true
            every { navigate(any<String>()) } returns Unit
            every { navigate(any<String>(), any<NavOptionsBuilder.() -> Unit>()) } returns Unit
            every { previousBackStackEntry } returns null
        }
        navigator = Navigator(mockNavController)
    }

    @Test
    fun `canPop returns false when there is no previous back stack entry`() {
        assert(!navigator.canPop)
    }

    @Test
    fun `canPop returns true when there is a previous back stack entry`() {
        mockNavController = mockk {
            every { popBackStack() } returns true
            every { previousBackStackEntry } returns mockk<NavBackStackEntry>()
        }
        navigator = Navigator(mockNavController)

        assert(navigator.canPop)
    }

    @Test
    fun `goBack calls popBackStack on navController`() {
        navigator.goBack()
        verify { mockNavController.popBackStack() }
    }

    @Test
    fun `fromSplashToHome navigates to Home and pops up to Splash`() {
        val expectedOptions: (NavOptionsBuilder.() -> Unit) = {
            popUpTo(PageRoute.Splash.route) { inclusive = true }
        }

        navigator.fromSplashToHome()

        val optionsSlot = slot<NavOptionsBuilder.() -> Unit>()

        verify {
            mockNavController.navigate(
                PageRoute.Home.route,
                capture(optionsSlot)
            )
        }

        val actualOptionsBuilder = NavOptionsBuilder().apply(optionsSlot.captured)
        val expectedOptionsBuilder = NavOptionsBuilder().apply(expectedOptions)
        assertEquals(expectedOptionsBuilder.popUpToRoute, actualOptionsBuilder.popUpToRoute)
    }

    @Test
    fun `fromSplashToLogin navigates to Login and pops up to Splash`() {
        val expectedOptions: (NavOptionsBuilder.() -> Unit) = {
            popUpTo(PageRoute.Splash.route) { inclusive = true }
        }

        navigator.fromSplashToLogin()

        val optionsSlot = slot<NavOptionsBuilder.() -> Unit>()
        verify {
            mockNavController.navigate(
                PageRoute.Login.route,
                capture(optionsSlot)
            )
        }

        val actualOptionsBuilder = NavOptionsBuilder().apply(optionsSlot.captured)
        val expectedOptionsBuilder = NavOptionsBuilder().apply(expectedOptions)
        assertEquals(expectedOptionsBuilder.popUpToRoute, actualOptionsBuilder.popUpToRoute)
    }

    @Test
    fun `toLogin navigates to Login`() {
        navigator.toLogin()
        verify { mockNavController.navigate(PageRoute.Login.route) }
    }

    @Test
    fun `toHome navigates to Home and pops up to Login`() {
        val expectedOptions: (NavOptionsBuilder.() -> Unit) = {
            popUpTo(PageRoute.Login.route) { inclusive = true }
        }

        navigator.toHome()

        val optionsSlot = slot<NavOptionsBuilder.() -> Unit>()
        verify {
            mockNavController.navigate(
                PageRoute.Home.route,
                capture(optionsSlot)
            )
        }

        val actualOptionsBuilder = NavOptionsBuilder().apply(optionsSlot.captured)
        val expectedOptionsBuilder = NavOptionsBuilder().apply(expectedOptions)
        assertEquals(expectedOptionsBuilder.popUpToRoute, actualOptionsBuilder.popUpToRoute)
    }
}