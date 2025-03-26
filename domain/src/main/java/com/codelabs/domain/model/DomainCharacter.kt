package com.codelabs.domain.model

class DomainCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val resourceURI: String,
    val urls: List<DomainUrl>,
    val thumbnail: DomainThumbnail
)