package com.plcoding.pitestdemo.presentation.calculator

sealed interface CalculatorAction {
    data class OnInput(val input: String) : CalculatorAction
    data object OnClearClick : CalculatorAction
    data object OnDeleteClick : CalculatorAction
    data object OnEqualsClick : CalculatorAction
}
