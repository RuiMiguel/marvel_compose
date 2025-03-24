package com.codelabs.marvelcompose

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
annotation class DarkLightPreviews