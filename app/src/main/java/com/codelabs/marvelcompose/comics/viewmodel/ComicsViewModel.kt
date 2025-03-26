package com.codelabs.marvelcompose.comics.viewmodel

import androidx.lifecycle.viewModelScope
import com.codelabs.comic_repository.repository.ComicRepository
import com.codelabs.domain.model.DomainComic
import com.codelabs.marvelcompose.base.BaseViewModel
import com.codelabs.marvelcompose.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(
    private val comicRepository: ComicRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel(dispatcher) {

    private val _state = MutableStateFlow(ComicsState())
    val state: StateFlow<ComicsState> = _state

    fun loadComics() {
        _state.value = _state.value.copy(status = ComicsStatus.Loading)

        viewModelScope.launch(coroutineContext) {
            try {
                val result = comicRepository.getComicsResult(
                    limit = 50,
                    offset = 0,
                )
                _state.value = _state.value.copyWith(
                    status = ComicsStatus.Success,
                    comics = result.data.results,
                    count = result.data.count,
                    total = result.data.total,
                    offset = result.data.offset,
                    legal = result.attributionText,
                )
            } catch (exception: Exception) {
                _state.value = _state.value.copy(status = ComicsStatus.Error)
            }
        }
    }

    fun loadMoreComics() {
        _state.value = _state.value.copy(status = ComicsStatus.Loading)

        val offset = _state.value.offset + 50

        viewModelScope.launch(coroutineContext) {
            try {
                val result = comicRepository.getComicsResult(
                    limit = 50,
                    offset = offset,
                )
                _state.value = _state.value.copyWith(
                    status = ComicsStatus.Success,
                    comics = _state.value.comics + result.data.results,
                    count = result.data.count + result.data.offset,
                    total = result.data.total,
                    offset = result.data.offset,
                    legal = result.attributionText,
                )
            } catch (exception: Exception) {
                _state.value = _state.value.copy(status = ComicsStatus.Error)
            }
        }
    }
}

sealed class ComicsStatus {
    data object Initial : ComicsStatus()
    data object Loading : ComicsStatus()
    data object Success : ComicsStatus()
    data object Error : ComicsStatus()
}

data class ComicsState(
    val status: ComicsStatus = ComicsStatus.Initial,
    val comics: List<DomainComic> = emptyList(),
    val count: Int = 0,
    val total: Int = 0,
    val offset: Int = 0,
    val legal: String = ""
) {
    fun copyWith(
        status: ComicsStatus? = null,
        comics: List<DomainComic>? = null,
        count: Int? = null,
        total: Int? = null,
        offset: Int? = null,
        legal: String? = null
    ) = copy(
        status = status ?: this.status,
        comics = comics ?: this.comics,
        count = count ?: this.count,
        total = total ?: this.total,
        offset = offset ?: this.offset,
        legal = legal ?: this.legal
    )
}