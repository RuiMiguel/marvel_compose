package com.codelabs.marvelcompose.characters.widgets

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.domain.model.DomainCharacter
import com.codelabs.domain.model.DomainThumbnail
import com.codelabs.domain.model.DomainUrl
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.comics.widgets.GridBoxDecoratedCell

@Composable
fun CharactersGridView(characters: List<DomainCharacter>, onCharacterClick: (DomainCharacter) -> Unit = {}) {
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels.dp
    val crossAxisCount = remember { mutableIntStateOf(2) }

    LaunchedEffect(screenWidth) {
        val widthInDp = screenWidth.value
        crossAxisCount.intValue = when {
            widthInDp > 2400 -> 15
            widthInDp <= 2400 && widthInDp > 1920 -> 10
            widthInDp <= 1920 && widthInDp > 800 -> 5
            else -> 3
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(crossAxisCount.intValue),
    ) {
        itemsIndexed(characters) { index, character ->
            GridBoxDecoratedCell(index = index, gridViewCrossAxisCount = crossAxisCount.intValue) {
                CharacterElement(character = character, onCharacterClick = onCharacterClick,)
            }
        }
    }
}

@DarkLightPreviews
@Preview(widthDp = 2500, heightDp = 1000, name = "Large screen")
@Preview(widthDp = 1920, heightDp = 1000, name = "Medium screen")
@Preview(widthDp = 800, heightDp = 1000 , name = "Small screen")
@Preview(widthDp = 640, heightDp = 1000 , name = "Extra small screen")
@Composable
fun CharactersGridViewPreview() {
    val characters = List(20) { index ->
        DomainCharacter(
            id = index,
            name = "Iron Man",
            description = "A billionaire superhero",
            modified = "2024-01-01T00:00:00Z",
            resourceURI = "http://example.com",
            urls = listOf(DomainUrl("wiki", "http://example.com/wiki")),
            thumbnail = DomainThumbnail("http://example.com/image", "jpg")
        )
    }

    CharactersGridView(characters = characters) {}
}
