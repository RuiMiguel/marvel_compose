package com.codelabs.marvelcompose.login.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.base.ui.BaseButton
import com.codelabs.marvelcompose.base.ui.BaseTextInput
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.ui.theme.MainTheme
import com.codelabs.marvelcompose.ui.theme.Red
import com.codelabs.marvelcompose.ui.theme.White

@Composable
fun AuthenticatedLoginForm(
    privateKey: String,
    publicKey: String,
    onSave: () -> Unit,
    onLogout: () -> Unit,
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
        AuthenticatedDescription()
        Spacer(modifier = Modifier.size(20.dp))
        BaseTextInput(
            value = privateKey,
            label = stringResource(R.string.private_key),
            onValueChange = onPrivateKeyChange
        )
        Spacer(modifier = Modifier.size(30.dp))
        BaseTextInput(
            value = publicKey,
            label = stringResource(R.string.public_key),
            onValueChange = onPublicKeyChange
        )
        Spacer(modifier = Modifier.size(80.dp))
        AuthenticatedButtons(
            onSave = onSave,
            onLogout = onLogout,
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.size(80.dp))
        if (isLoading)
            CircularProgressIndicator(color = Red)
    }
}

@Composable
fun AuthenticatedDescription() {
    Text(
        stringResource(R.string.your_current_credentials),
        style = MaterialTheme.typography.bodyLarge,
        color = White,
    )
}

@Composable
fun AuthenticatedButtons(
    onSave: () -> Unit,
    onLogout: () -> Unit,
    enabled: Boolean
) {
    Row(horizontalArrangement = Arrangement.spacedBy(30.dp)) {
        BaseButton(
            modifier = Modifier.weight(1f),
            onClick = onSave,
            enabled = enabled,
            label = stringResource(R.string.save),
        )
        BaseButton(
            modifier = Modifier.weight(1f),
            onClick = onLogout,
            enabled = enabled,
            label = stringResource(R.string.logout),
        )
    }
}

@DarkLightPreviews
@Composable
fun AuthenticatedLoginFormPreview() {
    MainTheme {
        Surface {
            AuthenticatedLoginForm(
                privateKey = "",
                publicKey = "",
                onSave = {},
                onLogout = {},
                onPrivateKeyChange = {},
                onPublicKeyChange = {},
                isLoading = false
            )
        }
    }
}

@DarkLightPreviews
@Composable
fun AuthenticatedLoadingLoginFormPreview() {
    MainTheme {
        Surface {
            AuthenticatedLoginForm(
                privateKey = "",
                publicKey = "",
                onSave = {},
                onLogout = {},
                onPrivateKeyChange = {},
                onPublicKeyChange = {},
                isLoading = true
            )
        }
    }
}