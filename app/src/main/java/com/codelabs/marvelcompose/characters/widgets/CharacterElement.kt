package com.codelabs.marvelcompose.characters.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.codelabs.domain.model.DomainCharacter
import com.codelabs.domain.model.DomainThumbnail
import com.codelabs.domain.model.DomainUrl
import com.codelabs.domain.model.getCharacterHomePreview
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.ui.theme.Blue

@Composable
fun CharacterElement(character: DomainCharacter,
    onCharacterClick: (DomainCharacter) -> Unit,
    modifier: Modifier = Modifier) {
    Surface(modifier = modifier.clickable { onCharacterClick(character) }) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = character.thumbnail.getCharacterHomePreview(),
                        placeholder = painterResource(R.drawable.placeholder),
                        contentScale = ContentScale.Crop,
                        contentDescription = character.name,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Blue.copy(alpha = 0.4f))
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.Center),
                            text = character.name,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        )
                    }
                }
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun CharacterElementPreview() {
    val character = DomainCharacter(
        id = 1,
        name = "Thor",
        description = "The son of Odin",
        modified = "2024-01-01T00:00:00Z",
        resourceURI = "http://example.com",
        urls = listOf(DomainUrl("detail", "http://example.com/detail")),
        thumbnail = DomainThumbnail("http://example.com/image", "jpg")
    )
    CharacterElement(character = character, onCharacterClick = {})
}