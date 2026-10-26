package com.plcoding.pitestdemo.calculator

internal class Tokenizer(private val input: String) {

    private var position = 0

    fun tokenize(): List<Token> {
        val tokens = mutableListOf<Token>()
        while (position < input.length) {
            val char = input[position]
            when {
                char.isWhitespace() -> position++
                char.isAsciiDigit() || char == '.' -> tokens += readNumber()
                else -> tokens += readSymbol(char)
            }
        }
        tokens += Token(TokenType.END, "", position)
        return tokens
    }

    private fun readNumber(): Token {
        val start = position
        while (position < input.length && (input[position].isAsciiDigit() || input[position] == '.')) {
            position++
        }
        val text = input.substring(start, position)
        if (text.count { it == '.' } > 1 || text.none { it.isAsciiDigit() }) {
            throw CalculatorException.InvalidNumber(text, start)
        }
        return Token(TokenType.NUMBER, text, start)
    }

    private fun readSymbol(char: Char): Token {
        val type = SYMBOLS[char] ?: throw CalculatorException.UnexpectedCharacter(char, position)
        val token = Token(type, char.toString(), position)
        position++
        return token
    }

    // Char.isDigit() also accepts non-ASCII digits like '٣', which toDouble() can't parse.
    private fun Char.isAsciiDigit(): Boolean = this in '0'..'9'

    private companion object {
        val SYMBOLS = mapOf(
            '+' to TokenType.PLUS,
            '-' to TokenType.MINUS,
            '*' to TokenType.STAR,
            '×' to TokenType.STAR,
            '/' to TokenType.SLASH,
            '÷' to TokenType.SLASH,
            '%' to TokenType.PERCENT,
            '^' to TokenType.CARET,
            '(' to TokenType.LEFT_PAREN,
            ')' to TokenType.RIGHT_PAREN,
        )
    }
}
