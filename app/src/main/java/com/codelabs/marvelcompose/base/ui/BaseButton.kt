package com.codelabs.marvelcompose.base.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.marvelcompose.DarkLightPreviews
import com.codelabs.marvelcompose.ui.theme.Grey
import com.codelabs.marvelcompose.ui.theme.LightGrey
import com.codelabs.marvelcompose.ui.theme.MainTheme
import com.codelabs.marvelcompose.ui.theme.Red
import com.codelabs.marvelcompose.ui.theme.White

@Composable
fun BaseButton(
    label: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.size(width = 200.dp, height = 65.dp),
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Red,
            contentColor = White,
            disabledContainerColor = Grey,
            disabledContentColor = LightGrey
        ),
        contentPadding = PaddingValues(horizontal = 30.dp, vertical = 15.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(
            label,
            fontSize = 24.sp,
            style = MaterialTheme.typography.bodyMedium,
            color = White
        )
    }
}

@DarkLightPreviews
@Composable
private fun EnabledBaseButtonPreview() {
    MainTheme {
        Surface {
            BaseButton(
                label = "button",
                onClick = {},
                enabled = true
            )
        }
    }
}


@DarkLightPreviews
@Composable
private fun DisabledBaseButtonPreview() {
    MainTheme {
        Surface {
            BaseButton(
                label = "button",
                onClick = {},
                enabled = false
            )
        }
    }
}