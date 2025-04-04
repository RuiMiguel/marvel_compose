package com.codelabs.marvelcompose.comics.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.codelabs.domain.model.DomainComic
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.common.widget.InfiniteGridHandler
import com.codelabs.marvelcompose.common.widget.InfoView
import com.codelabs.marvelcompose.comics.viewmodel.ComicsState
import com.codelabs.marvelcompose.comics.viewmodel.ComicsStatus
import com.codelabs.marvelcompose.comics.viewmodel.ComicsViewModel
import com.codelabs.marvelcompose.comics.widgets.ComicsViewContent
import com.codelabs.marvelcompose.common.widget.LoadingView
import com.codelabs.marvelcompose.navigation.Navigator

@Composable
fun ComicsPage(
    navigator: Navigator,
    comicsViewModel: ComicsViewModel = hiltViewModel(),
) {

    ComicsView(navigator = navigator, comicsViewModel = comicsViewModel)
}

@Composable
fun ComicsView(navigator: Navigator, comicsViewModel: ComicsViewModel) {

    val comicsState by comicsViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        comicsViewModel.loadComics()
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val defaultErrorMessage = stringResource(id = R.string.generic_error)

    LaunchedEffect(comicsState) {
        when (val status = comicsState.status) {
            is ComicsStatus.Error -> {
                val errorMessage =
                    (status as? ComicsStatus.Error)?.message ?: defaultErrorMessage
                snackbarHostState.showSnackbar(errorMessage, duration = SnackbarDuration.Short)
                navigator.fromSplashToLogin()
            }

            else -> Unit
        }
    }

    ComicsContainer(
        comicsState = comicsState,
        onLoadMore = { comicsViewModel.loadMoreComics() },
        onComicClick = { comic -> /*ComicDetailPage.page(comic)*/ },
    )
}

@Composable
fun ComicsContainer(
    comicsState: ComicsState,
    onLoadMore: () -> Unit = {},
    onComicClick: (DomainComic) -> Unit = {},
) {
    val gridState = rememberLazyGridState()

    val legal = comicsState.legal
    val count = comicsState.count
    val total = comicsState.total

    InfiniteGridHandler(
        listSize = comicsState.comics.size,
        gridState = gridState,
        onLoadMore = onLoadMore
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.weight(1f)) {
            ComicsViewContent(
                comics = comicsState.comics,
                gridState = gridState,
                onComicClick = onComicClick
            )
            if (comicsState.status == ComicsStatus.Loading) {
                LoadingView()
            }
        }

        InfoView(
            legal = legal,
            counter = stringResource(R.string.of_message, count, total),
        )
    }
}

@DarkLightPreviews
@Composable
fun ComicsContainerPreview() {
    val comicsState = ComicsState()

    MaterialTheme {
        ComicsContainer(
            comicsState = comicsState
        )
    }
}