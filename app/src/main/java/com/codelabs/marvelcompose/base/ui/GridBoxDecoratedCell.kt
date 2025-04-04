package com.codelabs.marvelcompose.comics.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codelabs.marvelcompose.ui.theme.Red

@Composable
fun GridBoxDecoratedCell(
    index: Int,
    gridViewCrossAxisCount: Int,
    color: Color? = null,
    content: @Composable () -> Unit
) {
    val borderColor = color ?: Red

    Box(
        modifier = Modifier
            .aspectRatio(3f/4f)
            .drawBehind {
                val borderWidth = 1.5f

                // Draw left border if not in first column
                if (index % gridViewCrossAxisCount != 0) {
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = borderWidth
                    )
                }

                // Draw top border if not in first row
                if (index >= gridViewCrossAxisCount) {
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = borderWidth
                    )
                }
            }
            .padding(start = 1.5.dp, top = 1.5.dp), // Matches Flutter padding
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
