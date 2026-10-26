package com.plcoding.pitestdemo.calculator

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculatorTest {

    private lateinit var calculator: Calculator

    @BeforeTest
    fun setUp() {
        calculator = Calculator()
    }

    @Test
    fun singleIntegerEvaluatesToItself() {
        assertEquals(42.0, calculator.evaluate("42"))
    }

    @Test
    fun decimalNumberIsParsed() {
        assertEquals(3.14, calculator.evaluate("3.14"))
    }

    @Test
    fun numberWithLeadingDecimalPointIsParsed() {
        assertEquals(0.5, calculator.evaluate(".5"))
    }

    @Test
    fun numberWithTrailingDecimalPointIsParsed() {
        assertEquals(5.0, calculator.evaluate("5."))
    }

    @Test
    fun addition() {
        assertEquals(5.0, calculator.evaluate("2 + 3"))
    }

    @Test
    fun subtraction() {
        assertEquals(6.0, calculator.evaluate("10 - 4"))
    }

    @Test
    fun subtractionWithNegativeResult() {
        assertEquals(-6.0, calculator.evaluate("4 - 10"))
    }

    @Test
    fun multiplication() {
        assertEquals(42.0, calculator.evaluate("6 * 7"))
    }

    @Test
    fun divisionWithFractionalResult() {
        assertEquals(3.75, calculator.evaluate("15 / 4"))
    }

    @Test
    fun modulo() {
        assertEquals(1.0, calculator.evaluate("10 % 3"))
    }

    @Test
    fun power() {
        assertEquals(1024.0, calculator.evaluate("2 ^ 10"))
    }

    @Test
    fun powerWithZeroExponentIsOne() {
        assertEquals(1.0, calculator.evaluate("7 ^ 0"))
    }

    @Test
    fun unicodeMultiplicationAndDivisionSigns() {
        assertEquals(9.0, calculator.evaluate("6 × 3 ÷ 2"))
    }

    @Test
    fun multiplicationBindsTighterThanAddition() {
        assertEquals(14.0, calculator.evaluate("2 + 3 * 4"))
    }

    @Test
    fun divisionBindsTighterThanSubtraction() {
        assertEquals(15.0, calculator.evaluate("20 - 10 / 2"))
    }

    @Test
    fun powerBindsTighterThanMultiplication() {
        assertEquals(12.0, calculator.evaluate("3 * 2 ^ 2"))
    }

    @Test
    fun subtractionIsLeftAssociative() {
        assertEquals(3.0, calculator.evaluate("10 - 4 - 3"))
    }

    @Test
    fun divisionIsLeftAssociative() {
        assertEquals(5.0, calculator.evaluate("100 / 10 / 2"))
    }

    @Test
    fun powerIsRightAssociative() {
        assertEquals(512.0, calculator.evaluate("2 ^ 3 ^ 2"))
    }

    @Test
    fun parenthesesOverridePrecedence() {
        assertEquals(20.0, calculator.evaluate("(2 + 3) * 4"))
    }

    @Test
    fun nestedParentheses() {
        assertEquals(3.0, calculator.evaluate("((1 + 2) * (3 + 4)) / 7"))
    }

    @Test
    fun redundantParenthesesAreAllowed() {
        assertEquals(5.0, calculator.evaluate("((((5))))"))
    }

    @Test
    fun unaryMinus() {
        assertEquals(-5.0, calculator.evaluate("-5"))
    }

    @Test
    fun unaryPlus() {
        assertEquals(5.0, calculator.evaluate("+5"))
    }

    @Test
    fun unaryMinusBeforeParentheses() {
        assertEquals(-5.0, calculator.evaluate("-(2 + 3)"))
    }

    @Test
    fun doubleNegationCancelsOut() {
        assertEquals(5.0, calculator.evaluate("--5"))
    }

    @Test
    fun subtractingNegativeNumber() {
        assertEquals(5.0, calculator.evaluate("3 - -2"))
    }

    @Test
    fun unaryMinusBindsLooserThanPower() {
        assertEquals(-4.0, calculator.evaluate("-2 ^ 2"))
    }

    @Test
    fun negativeBaseInParenthesesIsRaisedToPower() {
        assertEquals(4.0, calculator.evaluate("(-2) ^ 2"))
    }

    @Test
    fun negativeExponent() {
        assertEquals(0.5, calculator.evaluate("2 ^ -1"))
    }

    @Test
    fun whitespaceIsIgnored() {
        assertEquals(7.0, calculator.evaluate("  1+\t2 *\n3 "))
    }

    @Test
    fun floatingPointAdditionIsCloseToExpectedValue() {
        assertEquals(0.3, calculator.evaluate("0.1 + 0.2"), absoluteTolerance = 1e-9)
    }

    @Test
    fun largeIntermediateValues() {
        assertEquals(1e12, calculator.evaluate("1000000 * 1000000"))
    }

    @Test
    fun complexExpression() {
        assertEquals(3.0001220703125, calculator.evaluate("3 + 4 * 2 / (1 - 5) ^ 2 ^ 3"))
    }
}
