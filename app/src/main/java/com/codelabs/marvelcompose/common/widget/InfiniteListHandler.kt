package com.codelabs.marvelcompose.common.widget

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow

@Composable
fun InfiniteListHandler(listSize: Int,
    listState: LazyListState,
    onLoadMore: () -> Unit) {
    val lastItemVisible = remember { mutableStateOf(false) }

    LaunchedEffect(listSize) {
        lastItemVisible.value = false
    }

    if (lastItemVisible.value) {
        onLoadMore()
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
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