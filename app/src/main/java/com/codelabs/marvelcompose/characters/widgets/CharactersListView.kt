package com.codelabs.marvelcompose.characters.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codelabs.domain.model.DomainCharacter
import com.codelabs.domain.model.DomainThumbnail
import com.codelabs.domain.model.DomainUrl
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.ui.theme.Red

@Composable
fun CharactersListView(characters: List<DomainCharacter>, onCharacterClick: (DomainCharacter) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(characters) { index, character ->
            CharacterElement(modifier = Modifier.height(150.dp), character = character, onCharacterClick = onCharacterClick)

            if (index < characters.lastIndex) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.5.dp)
                        .background(Red)
                )
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun CharactersListViewPreview() {
    val characters = List(20) { index ->
        DomainCharacter(
            id = 1,
            name = "Spider-Man",
            description = "A superhero",
            modified = "2024-01-01T00:00:00Z",
            resourceURI = "http://example.com",
            urls = listOf(DomainUrl("detail", "http://example.com/detail")),
            thumbnail = DomainThumbnail("http://example.com/image", "jpg")
        )
    }

    CharactersListView(characters = characters) {}
}

