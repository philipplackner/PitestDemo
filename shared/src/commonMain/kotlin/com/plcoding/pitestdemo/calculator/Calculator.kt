package com.plcoding.pitestdemo.calculator

/**
 * Evaluates arithmetic expressions such as `3 + 4 * (2 - 1) ^ 2`.
 *
 * Supported syntax:
 * - Decimal numbers (`42`, `3.14`, `.5`, `5.`)
 * - Binary operators `+`, `-`, `*` (or `×`), `/` (or `÷`), `%` and `^`
 * - Unary `+` and `-`
 * - Parentheses
 *
 * `%` is the remainder of truncated division and keeps the sign of the dividend, so `-7 % 3` is `-1`.
 *
 * Precedence from lowest to highest: `+ -`, `* / %`, unary `+ -`, `^`.
 * `^` is right-associative, so `2 ^ 3 ^ 2` is `2 ^ 9`, and `-2 ^ 2` is `-(2 ^ 2)`.
 *
 * @throws CalculatorException when the expression is invalid or the result is not a finite number.
 */
class Calculator {

    fun evaluate(expression: String): Double {
        val tokens = Tokenizer(expression).tokenize()
        val result = Parser(tokens).parse()
        return when {
            result.isNaN() -> throw CalculatorException.UndefinedResult()
            result.isInfinite() -> throw CalculatorException.Overflow()
            // Adding 0.0 turns -0.0 into 0.0 so "-0" never shows up as negative zero.
            else -> result + 0.0
        }
    }
}
