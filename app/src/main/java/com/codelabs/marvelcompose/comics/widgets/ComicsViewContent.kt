package com.codelabs.marvelcompose.comics.widgets

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.domain.model.DomainComic
import com.codelabs.domain.model.DomainComicImage
import com.codelabs.domain.model.DomainPrice
import com.codelabs.domain.model.DomainThumbnail
import com.codelabs.domain.model.DomainUrl
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.common.widget.GridBoxDecoratedCell

@Composable
fun ComicsViewContent(
    comics: List<DomainComic>,
    gridState: LazyGridState,
    onComicClick: (DomainComic) -> Unit = {}) {
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels.dp
    val crossAxisCount = remember { mutableIntStateOf(2) }

    LaunchedEffect(screenWidth) {
        val widthInDp = screenWidth.value
        crossAxisCount.intValue = when {
            widthInDp > 2400 -> 8
            widthInDp <= 2400 && widthInDp > 1920 -> 5
            widthInDp <= 1920 && widthInDp > 800 -> 3
            else -> 2
        }
    }

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(crossAxisCount.intValue),
    ) {
        itemsIndexed(comics) { index, comic ->
            GridBoxDecoratedCell(index = index, gridViewCrossAxisCount = crossAxisCount.intValue) {
                ComicElement(comic = comic, onComicClick = onComicClick)
            }
        }
    }
}

@DarkLightPreviews
@Preview(widthDp = 2500, heightDp = 1000, name = "Large screen")
@Preview(widthDp = 1920, heightDp = 1000, name = "Medium screen")
@Preview(widthDp = 800, heightDp = 1000, name = "Small screen")
@Preview(widthDp = 640, heightDp = 1000, name = "Extra small screen")
@Composable
fun ComicsViewContentPreview() {
    val comics = List(20) { index ->
        DomainComic(
            id = index,
            title = "Avengers #$index",
            description = "Description",
            modified = "2023-01-01",
            issueNumber = 1.0,
            variantDescription = "First Issue",
            digitalId = index,
            format = "Comic",
            pageCount = 1,
            isbn = "123456789",
            upc = "789456123",
            diamondCode = "DIAMOND123",
            ean = "EAN12345",
            issn = "ISSN6789",
            textObjects = emptyList(),
            resourceURI = "http://example.com/comic",
            urls = listOf(DomainUrl(type = "detail", url = "http://example.com/comic")),
            prices = listOf(DomainPrice(type = "$", price = "9.99")),
            thumbnail = DomainThumbnail(path = "http://example.com/comic/image", extension = "jpg"),
            images = listOf(
                DomainComicImage(
                    path = "http://example.com/comic/extra",
                    extension = "jpg"
                )
            ),
        )
    }

    ComicsViewContent(comics = comics, gridState = rememberLazyGridState()) {}
}

