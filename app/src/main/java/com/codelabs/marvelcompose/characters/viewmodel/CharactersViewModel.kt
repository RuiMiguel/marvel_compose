package com.codelabs.marvelcompose.characters.viewmodel

import androidx.lifecycle.viewModelScope
import com.codelabs.character_repository.repository.CharacterRepository
import com.codelabs.domain.model.DomainCharacter
import com.codelabs.marvelcompose.base.viewmodel.BaseViewModel
import com.codelabs.marvelcompose.di.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) : BaseViewModel(dispatcher) {

    private val _state = MutableStateFlow(CharactersState())
    val state: StateFlow<CharactersState> = _state

    private val limit = 50

    fun loadCharacters() {
        _state.value = _state.value.copy(status = CharactersStatus.Loading)

        viewModelScope.launch(coroutineContext) {
            try {
                val result = characterRepository.getCharactersResult(
                    limit = limit,
                    offset = 0,
                )
                _state.value = _state.value.copyWith(
                    status = CharactersStatus.Success,
                    characters = result.data.results,
                    count = result.data.count,
                    total = result.data.total,
                    offset = result.data.offset,
                    legal = result.attributionText,
                )
            } catch (exception: Exception) {
                _state.value = _state.value.copy(status = CharactersStatus.Error(message = exception.message))
            }
        }
    }

    fun loadMoreCharacters() {
        _state.value = _state.value.copy(status = CharactersStatus.Loading)

        val offset = _state.value.offset + limit

        viewModelScope.launch(coroutineContext) {
            try {
                val result = characterRepository.getCharactersResult(
                    limit = limit,
                    offset = offset,
                )
                _state.value = _state.value.copyWith(
                    status = CharactersStatus.Success,
                    characters = _state.value.characters + result.data.results,
                    count = result.data.count + result.data.offset,
                    total = result.data.total,
                    offset = result.data.offset,
                    legal = result.attributionText,
                )
            } catch (exception: Exception) {
                _state.value = _state.value.copy(status = CharactersStatus.Error(message = exception.message))
            }
        }
    }
}

sealed class CharactersStatus {
    data object Initial : CharactersStatus()
    data object Loading : CharactersStatus()
    data object Success : CharactersStatus()
    data class Error(val message: String?) : CharactersStatus()
}

data class CharactersState(
    val status: CharactersStatus = CharactersStatus.Initial,
    val characters: List<DomainCharacter> = emptyList(),
    val count: Int = 0,
    val total: Int = 0,
    val offset: Int = 0,
    val legal: String = ""
) {
    fun copyWith(
        status: CharactersStatus? = null,
        characters: List<DomainCharacter>? = null,
        count: Int? = null,
        total: Int? = null,
        offset: Int? = null,
        legal: String? = null
    ) = copy(
        status = status ?: this.status,
        characters = characters ?: this.characters,
        count = count ?: this.count,
        total = total ?: this.total,
        offset = offset ?: this.offset,
        legal = legal ?: this.legal
    )
}