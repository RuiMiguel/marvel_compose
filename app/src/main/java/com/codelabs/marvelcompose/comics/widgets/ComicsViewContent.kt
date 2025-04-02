package com.codelabs.marvelcompose.comics.widgets

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.codelabs.domain.model.DomainComic

@Composable
fun ComicsViewContent(comics: List<DomainComic>, onComicClick: (DomainComic) -> Unit = {}) {
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
        columns = GridCells.Fixed(crossAxisCount.intValue),
    ) {
        itemsIndexed(comics) { index, comic ->
            GridBoxDecoratedCell(index = index, gridViewCrossAxisCount = crossAxisCount.intValue) {
                ComicElement(comic = comic, onComicClick = onComicClick)
            }
        }
    }
}
