package com.plcoding.pitestdemo.calculator

import kotlin.math.pow

/**
 * Recursive-descent parser that evaluates the tokens while parsing.
 *
 * Grammar:
 * ```
 * expression := term (('+' | '-') term)*
 * term       := unary (('*' | '/' | '%') unary)*
 * unary      := ('+' | '-') unary | power
 * power      := primary ('^' unary)?
 * primary    := NUMBER | '(' expression ')'
 * ```
 */
internal class Parser(private val tokens: List<Token>) {

    private var current = 0

    fun parse(): Double {
        if (peek().type == TokenType.END) {
            throw CalculatorException.EmptyExpression()
        }
        val result = parseExpression()
        val trailing = peek()
        when (trailing.type) {
            TokenType.END -> return result
            TokenType.RIGHT_PAREN -> throw CalculatorException.MismatchedParenthesis(trailing.position)
            else -> throw CalculatorException.UnexpectedToken(trailing.text, trailing.position)
        }
    }

    private fun parseExpression(): Double {
        var result = parseTerm()
        while (true) {
            result = when (peek().type) {
                TokenType.PLUS -> {
                    advance()
                    result + parseTerm()
                }
                TokenType.MINUS -> {
                    advance()
                    result - parseTerm()
                }
                else -> return result
            }
        }
    }

    private fun parseTerm(): Double {
        var result = parseUnary()
        while (true) {
            val operator = peek()
            result = when (operator.type) {
                TokenType.STAR -> {
                    advance()
                    result * parseUnary()
                }
                TokenType.SLASH -> {
                    advance()
                    result / nonZeroDivisor(parseUnary(), operator)
                }
                TokenType.PERCENT -> {
                    advance()
                    val divisor = nonZeroDivisor(parseUnary(), operator)
                    val remainder = result % divisor
                    if (remainder < 0.0) remainder + divisor else remainder
                }
                else -> return result
            }
        }
    }

    private fun parseUnary(): Double {
        return when (peek().type) {
            TokenType.MINUS -> {
                advance()
                -parseUnary()
            }
            TokenType.PLUS -> {
                advance()
                parseUnary()
            }
            else -> parsePower()
        }
    }

    private fun parsePower(): Double {
        val base = parsePrimary()
        if (peek().type != TokenType.CARET) {
            return base
        }
        advance()
        return base.pow(parseUnary())
    }

    private fun parsePrimary(): Double {
        val token = advance()
        return when (token.type) {
            TokenType.NUMBER -> token.text.toDouble()
            TokenType.LEFT_PAREN -> {
                val value = parseExpression()
                val closing = advance()
                when (closing.type) {
                    TokenType.RIGHT_PAREN -> value
                    TokenType.END -> throw CalculatorException.MismatchedParenthesis(token.position)
                    else -> throw CalculatorException.UnexpectedToken(closing.text, closing.position)
                }
            }
            TokenType.END -> throw CalculatorException.UnexpectedEnd()
            else -> throw CalculatorException.UnexpectedToken(token.text, token.position)
        }
    }

    private fun nonZeroDivisor(divisor: Double, operator: Token): Double {
        if (divisor == 0.0) {
            throw CalculatorException.DivisionByZero(operator.position)
        }
        return divisor
    }

    private fun peek(): Token = tokens[current]

    private fun advance(): Token {
        val token = tokens[current]
        if (token.type != TokenType.END) {
            current++
        }
        return token
    }
}
