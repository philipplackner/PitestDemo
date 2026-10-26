package com.plcoding.pitestdemo.calculator

sealed class CalculatorException(message: String) : IllegalArgumentException(message) {

    class EmptyExpression : CalculatorException("Expression is empty")

    class UnexpectedCharacter(val char: Char, val position: Int) :
        CalculatorException("Unexpected character '$char' at position $position")

    class InvalidNumber(val text: String, val position: Int) :
        CalculatorException("Invalid number '$text' at position $position")

    class UnexpectedToken(val text: String, val position: Int) :
        CalculatorException("Unexpected '$text' at position $position")

    class UnexpectedEnd : CalculatorException("Expression ended unexpectedly")

    class MismatchedParenthesis(val position: Int) :
        CalculatorException("Mismatched parenthesis at position $position")

    class DivisionByZero(val position: Int) :
        CalculatorException("Division by zero at position $position")

    class Overflow : CalculatorException("Result is too large")

    class UndefinedResult : CalculatorException("Result is undefined")
}
