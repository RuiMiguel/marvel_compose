package com.codelabs.marvelcompose.helpers

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher

fun hasContentDescriptionAndRole(
    contentDescription: String,
    role: Role
): SemanticsMatcher {
    val hasContentDescription =
        SemanticsMatcher.keyIsDefined(SemanticsProperties.ContentDescription).and(
            SemanticsMatcher.expectValue(
                SemanticsProperties.ContentDescription,
                listOf(contentDescription)
            )
        )
    val hasRole = SemanticsMatcher.expectValue(SemanticsProperties.Role, role)
    return hasContentDescription.and(hasRole)
}

fun hasProgressBar(): SemanticsMatcher =
    SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo)
