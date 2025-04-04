package com.codelabs.marvelcompose.characters.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.codelabs.domain.model.DomainCharacter
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.base.ui.InfiniteListHandler
import com.codelabs.marvelcompose.base.ui.InfoView
import com.codelabs.marvelcompose.characters.viewmodel.CharactersState
import com.codelabs.marvelcompose.characters.viewmodel.CharactersStatus
import com.codelabs.marvelcompose.characters.viewmodel.CharactersViewModel
import com.codelabs.marvelcompose.characters.widgets.CharactersViewContent
import com.codelabs.marvelcompose.comics.widgets.LoadingView
import com.codelabs.marvelcompose.navigation.Navigator

@Composable
fun CharactersPage(
    navigator: Navigator,
    charactersViewModel: CharactersViewModel = hiltViewModel(),
) {

    CharactersView(navigator = navigator, charactersViewModel = charactersViewModel)
}

@Composable
fun CharactersView(navigator: Navigator, charactersViewModel: CharactersViewModel) {

    val charactersState by charactersViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        charactersViewModel.loadCharacters()
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val defaultErrorMessage = stringResource(id = R.string.generic_error)

    LaunchedEffect(charactersState) {
        when (val status = charactersState.status) {
            is CharactersStatus.Error -> {
                val errorMessage =
                    (status as? CharactersStatus.Error)?.message ?: defaultErrorMessage
                snackbarHostState.showSnackbar(errorMessage, duration = SnackbarDuration.Short)
                navigator.fromSplashToLogin()
            }

            else -> Unit
        }
    }

    CharactersContainer(
        charactersState = charactersState,
        onLoadMore = { charactersViewModel.loadMoreCharacters() },
        onCharacterClick = { character -> /*CharacterDetailPage.page(character)*/ },
    )
}

@Composable
fun CharactersContainer(
    charactersState: CharactersState,
    onLoadMore: () -> Unit = {},
    onCharacterClick: (DomainCharacter) -> Unit = {},
) {
    val legal = charactersState.legal
    val count = charactersState.count
    val total = charactersState.total

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.weight(1f)) {
            CharactersViewContent(
                characters = charactersState.characters,
                onCharacterClick = onCharacterClick,
                onLoadMore = onLoadMore
            )
            if (charactersState.status == CharactersStatus.Loading) {
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
fun CharactersContainerPreview() {
    val charactersState = CharactersState()

    MaterialTheme {
        CharactersContainer(
            charactersState = charactersState
        )
    }
}