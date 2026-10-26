package com.plcoding.pitestdemo.presentation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

sealed interface UiText {
    data class DynamicString(val value: String) : UiText

    class StringResource(
        val id: org.jetbrains.compose.resources.StringResource,
        val args: Array<Any> = emptyArray(),
    ) : UiText

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringResource -> stringResource(id, *args)
    }
}
