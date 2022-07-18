package com.example.verygoodcore.marvel_compose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.verygoodcore.marvel_compose.home.view.HomePage
import com.example.verygoodcore.marvel_compose.splash.view.SplashPage
import com.example.verygoodcore.marvel_compose.ui.theme.MainTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme() {
                SplashPage()
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun DefaultDarkPreview() {
    MainTheme() {
        SplashPage()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun DefaultLightPreview() {
    MainTheme() {
        SplashPage()
    }
}