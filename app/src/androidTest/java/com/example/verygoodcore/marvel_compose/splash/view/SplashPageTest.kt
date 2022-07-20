package com.example.verygoodcore.marvel_compose.splash.view

import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.example.verygoodcore.marvel_compose.ComposableHiltTest
import com.example.verygoodcore.marvel_compose.splash.viewmodel.AutoLoginViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(SplashPageTest::class, SplashViewTest::class)
class SplashPageTestSuite

@HiltAndroidTest
class SplashPageTest : ComposableHiltTest() {
    @Test
    fun rendersCorrectly() {
        composeTestRule.setContent {
            SplashPage()
        }

        composeTestRule.onNodeWithTag("SplashView").assertExists()
    }
}

@HiltAndroidTest
class SplashViewTest : ComposableHiltTest() {
    lateinit var autoLoginViewModel: AutoLoginViewModel

    @Test
    fun rendersCorrectly() {
        composeTestRule.setContent {
            autoLoginViewModel = mockk(relaxed = true)
            SplashView(autoLoginViewModel = autoLoginViewModel)
        }

        composeTestRule.onNodeWithTag("SplashScaffold").assertExists()
        composeTestRule.onNodeWithContentDescription("Marvel_Compose").assertExists()
    }

    @Ignore
    @Test
    fun whileLoadingStateShowCircularProgressIndicator() {
        composeTestRule.setContent {
            autoLoginViewModel = mockk(relaxed = true)
            SplashView(autoLoginViewModel = autoLoginViewModel)
        }

        // TODO(ruimiguel): mock AutoLoginViewModel.state to be Loading
        composeTestRule.onNodeWithTag("SplashViewCircularProgressIndicator").assertExists()
    }

    @Ignore
    @Test
    fun onErrorStateShowSnackbarAndNavigateToLogin() {

        // TODO(ruimiguel): mock AutoLoginViewModel.state to be Error
        composeTestRule.onNodeWithTag("SplashViewCircularProgressIndicator").assertDoesNotExist()
    }

    @Ignore
    @Test
    fun onSuccessStateNavigateToHome() {

        // TODO(ruimiguel): mock AutoLoginViewModel.state to be Success
        composeTestRule.onNodeWithTag("SplashViewCircularProgressIndicator").assertDoesNotExist()
    }
}
