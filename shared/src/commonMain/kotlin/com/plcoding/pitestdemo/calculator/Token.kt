package com.plcoding.pitestdemo.calculator

internal enum class TokenType {
    NUMBER,
    PLUS,
    MINUS,
    STAR,
    SLASH,
    PERCENT,
    CARET,
    LEFT_PAREN,
    RIGHT_PAREN,
    END,
}

internal data class Token(
    val type: TokenType,
    val text: String,
    val position: Int,
)
