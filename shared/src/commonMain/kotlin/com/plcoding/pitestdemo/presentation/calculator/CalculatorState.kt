package com.plcoding.pitestdemo.presentation.calculator

import com.plcoding.pitestdemo.presentation.UiText

data class CalculatorState(
    val expression: String = "",
    val preview: String? = null,
    val error: UiText? = null,
    val isResult: Boolean = false,
)
