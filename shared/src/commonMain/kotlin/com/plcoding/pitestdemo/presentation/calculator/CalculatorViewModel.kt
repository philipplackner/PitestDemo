package com.plcoding.pitestdemo.presentation.calculator

import androidx.lifecycle.ViewModel
import com.plcoding.pitestdemo.calculator.Calculator
import com.plcoding.pitestdemo.calculator.CalculatorException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel(
    private val calculator: Calculator,
) : ViewModel() {

    private val _state = MutableStateFlow(CalculatorState())
    val state = _state.asStateFlow()

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.OnInput -> append(action.input)
            CalculatorAction.OnClearClick -> _state.update { CalculatorState() }
            CalculatorAction.OnDeleteClick -> updateExpression(state.value.expression.dropLast(1))
            CalculatorAction.OnEqualsClick -> evaluate()
        }
    }

    private fun append(input: String) {
        val current = state.value
        val startsNewExpression = current.isResult && input.first().let { it.isDigit() || it == '.' || it == '(' }
        val base = if (startsNewExpression) "" else current.expression
        updateExpression(base + input)
    }

    private fun updateExpression(expression: String) {
        _state.update {
            it.copy(
                expression = expression,
                preview = previewFor(expression),
                error = null,
                isResult = false,
            )
        }
    }

    private fun previewFor(expression: String): String? {
        val result = try {
            calculator.evaluate(expression).toDisplayString()
        } catch (e: CalculatorException) {
            return null
        }
        return result.takeIf { it != expression }
    }

    private fun evaluate() {
        val expression = state.value.expression
        try {
            val result = calculator.evaluate(expression).toDisplayString()
            _state.update { it.copy(expression = result, preview = null, error = null, isResult = true) }
        } catch (e: CalculatorException) {
            _state.update { it.copy(preview = null, error = e.toUiText()) }
        }
    }
}
