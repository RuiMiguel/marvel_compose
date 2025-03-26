package com.codelabs.marvelcompose.login.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codelabs.marvelcompose.DarkLightPreviews
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.ui.theme.Grey
import com.codelabs.marvelcompose.ui.theme.LightGrey
import com.codelabs.marvelcompose.ui.theme.MainTheme
import com.codelabs.marvelcompose.ui.theme.Red
import com.codelabs.marvelcompose.ui.theme.White

@Composable
fun UnauthenticatedLoginForm(
    privateKey: String,
    publicKey: String,
    onLogin: () -> Unit,
    onPrivateKeyChange: (String) -> Unit,
    onPublicKeyChange: (String) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UnauthenticatedDescription()
        Spacer(modifier = Modifier.size(20.dp))
        LoginTextInput(
            value = privateKey,
            label = stringResource(R.string.private_key),
            onValueChange = onPrivateKeyChange
        )
        Spacer(modifier = Modifier.size(30.dp))
        LoginTextInput(
            value = publicKey,
            label = stringResource(R.string.public_key),
            onValueChange = onPublicKeyChange
        )
        Spacer(modifier = Modifier.size(80.dp))
        UnauthenticatedButtons(
            onLogin = onLogin,
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.size(80.dp))
        if (isLoading)
            CircularProgressIndicator(color = Red)
    }
}

@Composable
fun UnauthenticatedDescription() {
    val annotatedString = buildAnnotatedString {
        append(stringResource(R.string.add_your_developer_credentials_to_login))
        pushStringAnnotation(tag = "link", annotation = "https://developer.marvel.com/account")
    }

    Text(
        annotatedString,
        style = MaterialTheme.typography.bodyLarge,
        color = White
    )
}

@Composable
fun UnauthenticatedButtons(
    onLogin: () -> Unit,
    enabled: Boolean
) {
    Button(
        onClick = onLogin,
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
            stringResource(R.string.login),
            fontSize = 24.sp,
            style = MaterialTheme.typography.bodyMedium,
            color = White
        )
    }
}

@DarkLightPreviews
@Composable
fun UnauthenticatedLoginFormPreview() {
    MainTheme {
        Surface {
            UnauthenticatedLoginForm(
                privateKey = "",
                publicKey = "",
                onLogin = {},
                onPrivateKeyChange = {},
                onPublicKeyChange = {},
                isLoading = false
            )
        }
    }
}

@DarkLightPreviews
@Composable
fun UnauthenticatedLoadingLoginFormPreview() {
    MainTheme {
        Surface {
            UnauthenticatedLoginForm(
                privateKey = "",
                publicKey = "",
                onLogin = {},
                onPrivateKeyChange = {},
                onPublicKeyChange = {},
                isLoading = true
            )
        }
    }
}

