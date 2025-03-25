package com.codelabs.comic_repository.repository

import com.codelabs.api_client.service.ComicService
import com.codelabs.comic_repository.mapper.toDomainResult
import com.codelabs.domain.model.DomainComic
import com.codelabs.domain.model.DomainResult

class ComicRepository(private val comicService: ComicService) {
    suspend fun getComicsResult(limit: Int, offset: Int): DomainResult<DomainComic> {
        try {
            val response = comicService.getComicsResult(limit = limit, offset = offset)
            return response.toDomainResult()
        } catch (error: Exception) {
            throw Exception(error.message)
        }
    }
}