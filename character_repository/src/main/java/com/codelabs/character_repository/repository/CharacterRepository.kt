package com.codelabs.character_repository.repository

import com.codelabs.api_client.service.CharacterService
import com.codelabs.character_repository.mapper.toDomainResult
import com.codelabs.domain.model.DomainCharacter
import com.codelabs.domain.model.DomainResult


class CharacterRepository(private val characterService: CharacterService) {
    suspend fun getCharactersResult(limit: Int, offset: Int): DomainResult<DomainCharacter> {
        try {
            val response = characterService.getCharactersResult(limit = limit, offset = offset)
            return response.toDomainResult()
        } catch (error: Exception) {
            throw Exception(error.message)
        }
    }
}