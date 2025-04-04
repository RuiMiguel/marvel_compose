package com.codelabs.marvelcompose.base.ui

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow

@Composable
fun InfiniteGridHandler(listSize: Int,
    gridState: LazyGridState,
    onLoadMore: () -> Unit) {
    val lastItemVisible = remember { mutableStateOf(false) }

    LaunchedEffect(listSize) {
        lastItemVisible.value = false
    }

    if (lastItemVisible.value) {
        onLoadMore()
    }

    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo }
            .collect { layoutInfo ->
                val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                if (
                    lastVisibleItem != null &&
                    lastVisibleItem.index == layoutInfo.totalItemsCount - 1
                ) {
                    onLoadMore()
                }
            }
    }
}