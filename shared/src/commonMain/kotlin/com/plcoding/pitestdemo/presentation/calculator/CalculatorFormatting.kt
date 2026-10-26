package com.plcoding.pitestdemo.presentation.calculator

import com.plcoding.pitestdemo.calculator.CalculatorException
import com.plcoding.pitestdemo.presentation.UiText
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.round
import pitestdemo.shared.generated.resources.Res
import pitestdemo.shared.generated.resources.error_division_by_zero
import pitestdemo.shared.generated.resources.error_empty_expression
import pitestdemo.shared.generated.resources.error_invalid_number
import pitestdemo.shared.generated.resources.error_mismatched_parenthesis
import pitestdemo.shared.generated.resources.error_overflow
import pitestdemo.shared.generated.resources.error_undefined_result
import pitestdemo.shared.generated.resources.error_unexpected_character
import pitestdemo.shared.generated.resources.error_unexpected_end
import pitestdemo.shared.generated.resources.error_unexpected_token

private const val SIGNIFICANT_DIGITS = 12
private const val MAX_PLAIN_INTEGER = 1e15

/**
 * Formats a result so it can be shown and typed into again, e.g. `5.0` becomes `5`.
 * Rounds to [SIGNIFICANT_DIGITS] so float noise like `0.30000000000000004` shows as `0.3`.
 */
internal fun Double.toDisplayString(): String {
    val value = roundToSignificantDigits()
    return if (value == floor(value) && abs(value) < MAX_PLAIN_INTEGER) {
        value.toLong().toString()
    } else {
        value.toString()
    }
}

private fun Double.roundToSignificantDigits(): Double {
    if (this == 0.0) return 0.0
    val scale = 10.0.pow(SIGNIFICANT_DIGITS - 1 - floor(log10(abs(this))).toInt())
    val rounded = round(this * scale) / scale
    return if (rounded.isFinite()) rounded else this
}

internal fun CalculatorException.toUiText(): UiText = when (this) {
    is CalculatorException.EmptyExpression -> UiText.StringResource(Res.string.error_empty_expression)
    is CalculatorException.UnexpectedCharacter ->
        UiText.StringResource(Res.string.error_unexpected_character, arrayOf(char.toString()))
    is CalculatorException.InvalidNumber -> UiText.StringResource(Res.string.error_invalid_number, arrayOf(text))
    is CalculatorException.UnexpectedToken -> UiText.StringResource(Res.string.error_unexpected_token, arrayOf(text))
    is CalculatorException.UnexpectedEnd -> UiText.StringResource(Res.string.error_unexpected_end)
    is CalculatorException.MismatchedParenthesis -> UiText.StringResource(Res.string.error_mismatched_parenthesis)
    is CalculatorException.DivisionByZero -> UiText.StringResource(Res.string.error_division_by_zero)
    is CalculatorException.Overflow -> UiText.StringResource(Res.string.error_overflow)
    is CalculatorException.UndefinedResult -> UiText.StringResource(Res.string.error_undefined_result)
}
