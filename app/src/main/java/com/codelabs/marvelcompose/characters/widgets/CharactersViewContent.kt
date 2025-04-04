package com.codelabs.marvelcompose.characters.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import com.codelabs.domain.model.DomainCharacter
import com.codelabs.domain.model.DomainThumbnail
import com.codelabs.domain.model.DomainUrl
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.base.ui.LandscapePortraitPreviews

@Composable
fun CharactersViewContent(characters: List<DomainCharacter>, onCharacterClick: (DomainCharacter) -> Unit = {}) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        CharactersGridView(characters = characters)
    } else {
        CharactersListView(characters = characters)
    }
}

@LandscapePortraitPreviews
@Composable
fun CharactersViewContentPreview() {
    val characters = List(20) { index ->
        DomainCharacter(
            id = index,
            name = "Captain America",
            description = "The hero of America",
            modified = "2024-01-01T00:00:00Z",
            resourceURI = "http://example.com",
            urls = listOf(DomainUrl("wiki", "http://example.com/wiki")),
            thumbnail = DomainThumbnail("http://example.com/image", "jpg")
        )
    }

    CharactersViewContent(characters = characters) {}
}
