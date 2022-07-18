package com.example.verygoodcore.marvel_compose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.verygoodcore.marvel_compose.R

val fonts = FontFamily(Font(R.font.oswald))

val Typography = Typography(
        defaultFontFamily = fonts,
        h1 = TextStyle(
                fontWeight = FontWeight.Bold,
                lineHeight = 1.2.sp,
                fontSize = 30.sp,
                textDecoration = TextDecoration.None,
        ),
        h2 = TextStyle(
                fontWeight = FontWeight.Bold,
                lineHeight = 1.2.sp,
                fontSize = 24.sp,
                textDecoration = TextDecoration.None,
        ),
        body1 = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                lineHeight = 1.4.sp,
                textDecoration = TextDecoration.None,
        ),
        body2 = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 1.4.sp,
                textDecoration = TextDecoration.None,
        ),
        subtitle1 = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                lineHeight = 1.4.sp,
                textDecoration = TextDecoration.None,
        ),
        button = TextStyle(
                color = white,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                lineHeight = 1.2.sp,
        ),
)