package com.plcoding.pitestdemo.calculator

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CalculatorErrorTest {

    private lateinit var calculator: Calculator

    @BeforeTest
    fun setUp() {
        calculator = Calculator()
    }

    @Test
    fun emptyExpressionFails() {
        assertFailsWith<CalculatorException.EmptyExpression> {
            calculator.evaluate("")
        }
    }

    @Test
    fun blankExpressionFails() {
        assertFailsWith<CalculatorException.EmptyExpression> {
            calculator.evaluate("   ")
        }
    }

    @Test
    fun unknownCharacterReportsCharacterAndPosition() {
        val exception = assertFailsWith<CalculatorException.UnexpectedCharacter> {
            calculator.evaluate("2 & 3")
        }

        assertEquals('&', exception.char)
        assertEquals(2, exception.position)
    }

    @Test
    fun lettersAreRejected() {
        val exception = assertFailsWith<CalculatorException.UnexpectedCharacter> {
            calculator.evaluate("abc")
        }

        assertEquals('a', exception.char)
        assertEquals(0, exception.position)
    }

    @Test
    fun nonAsciiDigitsAreRejected() {
        assertFailsWith<CalculatorException.UnexpectedCharacter> {
            calculator.evaluate("٣ + 1")
        }
    }

    @Test
    fun numberWithMultipleDecimalPointsFails() {
        val exception = assertFailsWith<CalculatorException.InvalidNumber> {
            calculator.evaluate("1.2.3")
        }

        assertEquals("1.2.3", exception.text)
        assertEquals(0, exception.position)
    }

    @Test
    fun loneDecimalPointFails() {
        val exception = assertFailsWith<CalculatorException.InvalidNumber> {
            calculator.evaluate("1 + .")
        }

        assertEquals(".", exception.text)
        assertEquals(4, exception.position)
    }

    @Test
    fun missingClosingParenthesisReportsOpeningPosition() {
        val exception = assertFailsWith<CalculatorException.MismatchedParenthesis> {
            calculator.evaluate("(2 + 3")
        }

        assertEquals(0, exception.position)
    }

    @Test
    fun extraClosingParenthesisReportsItsPosition() {
        val exception = assertFailsWith<CalculatorException.MismatchedParenthesis> {
            calculator.evaluate("2 + 3)")
        }

        assertEquals(5, exception.position)
    }

    @Test
    fun emptyParenthesesFail() {
        val exception = assertFailsWith<CalculatorException.UnexpectedToken> {
            calculator.evaluate("()")
        }

        assertEquals(")", exception.text)
        assertEquals(1, exception.position)
    }

    @Test
    fun trailingOperatorFails() {
        assertFailsWith<CalculatorException.UnexpectedEnd> {
            calculator.evaluate("2 +")
        }
    }

    @Test
    fun leadingBinaryOperatorFails() {
        val exception = assertFailsWith<CalculatorException.UnexpectedToken> {
            calculator.evaluate("* 2")
        }

        assertEquals("*", exception.text)
        assertEquals(0, exception.position)
    }

    @Test
    fun consecutiveBinaryOperatorsFail() {
        val exception = assertFailsWith<CalculatorException.UnexpectedToken> {
            calculator.evaluate("2 * / 3")
        }

        assertEquals("/", exception.text)
        assertEquals(4, exception.position)
    }

    @Test
    fun twoNumbersWithoutOperatorFail() {
        val exception = assertFailsWith<CalculatorException.UnexpectedToken> {
            calculator.evaluate("2 3")
        }

        assertEquals("3", exception.text)
        assertEquals(2, exception.position)
    }

    @Test
    fun divisionByZeroReportsOperatorPosition() {
        val exception = assertFailsWith<CalculatorException.DivisionByZero> {
            calculator.evaluate("5 / 0")
        }

        assertEquals(2, exception.position)
    }

    @Test
    fun divisionByZeroFromSubexpressionFails() {
        assertFailsWith<CalculatorException.DivisionByZero> {
            calculator.evaluate("1 / (2 - 2)")
        }
    }

    @Test
    fun moduloByZeroFails() {
        assertFailsWith<CalculatorException.DivisionByZero> {
            calculator.evaluate("5 % 0")
        }
    }

    @Test
    fun resultTooLargeFails() {
        assertFailsWith<CalculatorException.Overflow> {
            calculator.evaluate("10 ^ 400")
        }
    }

    @Test
    fun evenRootOfNegativeNumberIsUndefined() {
        assertFailsWith<CalculatorException.UndefinedResult> {
            calculator.evaluate("(-8) ^ 0.5")
        }
    }
}
