package com.codelabs.marvelcompose.login.widgets

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.codelabs.marvelcompose.DarkLightPreviews
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.base.ui.BaseButton
import com.codelabs.marvelcompose.base.ui.BaseTextInput
import com.codelabs.marvelcompose.ui.theme.MainTheme
import com.codelabs.marvelcompose.ui.theme.Red

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
    val linkTag = "URL"
    val uriHandler = LocalUriHandler.current
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    val annotatedString = buildAnnotatedString {
        append(stringResource(R.string.add_your_developer_credentials_to_login))
        append("\n")

        val url = stringResource(R.string.add_your_developer_credentials_link)
        val start = length
        append(url)
        val end = length

        addStringAnnotation(tag = linkTag, annotation = url, start = start, end = end)

        addStyle(
            style = SpanStyle(
                color = Red,
                textDecoration = TextDecoration.Underline
            ),
            start = start,
            end = end
        )
    }

    BasicText(
        text = annotatedString,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures { tapOffset ->
                textLayoutResult?.let { layoutResult ->
                    val position = layoutResult.getOffsetForPosition(tapOffset)
                    // Get the link at the tapped position with the link tag
                    annotatedString.getStringAnnotations(linkTag, position, position)
                        .firstOrNull()?.let { annotation ->
                            uriHandler.openUri(annotation.item)
                        }
                }
            }
        },
        onTextLayout = { textLayoutResult = it }
    )
}

@Composable
fun UnauthenticatedButtons(
    onLogin: () -> Unit,
    enabled: Boolean
) {
    BaseButton(
        onClick = onLogin,
        enabled = enabled,
        label = stringResource(R.string.login),
    )
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

