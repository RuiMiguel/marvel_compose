package com.codelabs.marvelcompose.login.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
    Row {
        Button(
            onClick = onSave,
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
                stringResource(R.string.save),
                fontSize = 24.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = White
            )
        }
        Spacer(modifier = Modifier.size(30.dp))
        Button(
            onClick = onLogout,
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
                stringResource(R.string.logout),
                fontSize = 24.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = White
            )
        }
    }
}


@DarkLightPreviews
@Composable
fun AuthenticatedLoginFormPreview() {
    MainTheme {
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

@DarkLightPreviews
@Composable
fun AuthenticatedLoadingLoginFormPreview() {
    MainTheme {
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