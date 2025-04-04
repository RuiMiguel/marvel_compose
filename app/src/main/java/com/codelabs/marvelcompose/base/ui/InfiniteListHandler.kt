package com.codelabs.marvelcompose.comics.widgets

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow

@Composable
fun InfiniteListHandler(listSize: Int, onLoadMore: () -> Unit) {
    val lastItemVisible = remember { mutableStateOf(false) }

    LaunchedEffect(listSize) {
        lastItemVisible.value = false
    }

    if (lastItemVisible.value) {
        onLoadMore()
    }

    LaunchedEffect(Unit) {
        snapshotFlow { lastItemVisible.value }.collect {}
    }

    val listState = rememberLazyGridState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { index ->
                if (index != null && index == listSize - 1) {
                    lastItemVisible.value = true
                }
            }
    }
}