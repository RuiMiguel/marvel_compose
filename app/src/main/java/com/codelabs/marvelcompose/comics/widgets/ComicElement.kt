package com.codelabs.marvelcompose.comics.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.codelabs.domain.model.DomainComic
import com.codelabs.domain.model.DomainComicImage
import com.codelabs.domain.model.DomainPrice
import com.codelabs.domain.model.DomainThumbnail
import com.codelabs.domain.model.DomainUrl
import com.codelabs.domain.model.getHomePreviewUrl
import com.codelabs.marvelcompose.R
import com.codelabs.marvelcompose.base.ui.DarkLightPreviews
import com.codelabs.marvelcompose.ui.theme.Blue

@Composable
fun ComicElement(comic: DomainComic, onComicClick: (DomainComic) -> Unit) {
    Surface(modifier = Modifier.clickable { onComicClick(comic) }) {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = comic.thumbnail.getHomePreviewUrl(),
                        placeholder = painterResource(R.drawable.placeholder),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = comic.title,
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Blue.copy(alpha = 0.4f))
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text = comic.title,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun ComicElementPreview() {
    val comic = DomainComic(
        id = 1,
        title = "Avengers #1",
        description = "Description",
        modified = "2023-01-01",
        issueNumber = 1.0,
        variantDescription = "First Issue",
        digitalId = 1,
        format = "Comic",
        pageCount = 1,
        isbn = "123456789",
        upc = "789456123",
        diamondCode = "DIAMOND123",
        ean = "EAN12345",
        issn = "ISSN6789",
        textObjects = emptyList(),
        resourceURI = "http://example.com/comic",
        urls = listOf(DomainUrl(type = "detail", url = "http://example.com/comic")),
        prices = listOf(DomainPrice(type = "$", price = "9.99")),
        thumbnail = DomainThumbnail(path = "http://example.com/comic/image", extension = "jpg"),
        images = listOf(
            DomainComicImage(
                path = "http://example.com/comic/extra",
                extension = "jpg"
            )
        ),
    )
    ComicElement(comic = comic) {}
}