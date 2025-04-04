package com.codelabs.marvelcompose.base.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
annotation class DarkLightPreviews

@Preview(widthDp = 1920, heightDp = 1080, name = "Landscape")
@Preview(widthDp = 1080, heightDp = 1920, name = "Portrait")
annotation class LandscapePortraitPreviews