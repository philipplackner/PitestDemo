package com.plcoding.pitestdemo.calculator

import kotlin.test.Test
import kotlin.test.assertEquals

class TokenizerTest {

    @Test
    fun emptyInputProducesOnlyEndToken() {
        val tokens = Tokenizer("").tokenize()

        assertEquals(listOf(Token(TokenType.END, "", 0)), tokens)
    }

    @Test
    fun allOperatorsAreRecognized() {
        val types = Tokenizer("+-*/%^()").tokenize().map { it.type }

        assertEquals(
            listOf(
                TokenType.PLUS,
                TokenType.MINUS,
                TokenType.STAR,
                TokenType.SLASH,
                TokenType.PERCENT,
                TokenType.CARET,
                TokenType.LEFT_PAREN,
                TokenType.RIGHT_PAREN,
                TokenType.END,
            ),
            types,
        )
    }

    @Test
    fun unicodeOperatorsMapToTheirAsciiEquivalents() {
        val types = Tokenizer("×÷").tokenize().map { it.type }

        assertEquals(listOf(TokenType.STAR, TokenType.SLASH, TokenType.END), types)
    }

    @Test
    fun tokensRecordTheirPositionInTheInput() {
        val tokens = Tokenizer("12 + 3.5").tokenize()

        assertEquals(
            listOf(
                Token(TokenType.NUMBER, "12", 0),
                Token(TokenType.PLUS, "+", 3),
                Token(TokenType.NUMBER, "3.5", 5),
                Token(TokenType.END, "", 8),
            ),
            tokens,
        )
    }

    @Test
    fun numberDirectlyFollowedByParenthesisIsSplit() {
        val tokens = Tokenizer("2(").tokenize()

        assertEquals(
            listOf(
                Token(TokenType.NUMBER, "2", 0),
                Token(TokenType.LEFT_PAREN, "(", 1),
                Token(TokenType.END, "", 2),
            ),
            tokens,
        )
    }
}
