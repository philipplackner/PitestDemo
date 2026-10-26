package com.plcoding.pitestdemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.pitestdemo.presentation.calculator.CalculatorRoot
import com.plcoding.pitestdemo.presentation.theme.CalculatorTheme

@Composable
@Preview
fun App() {
    CalculatorTheme {
        CalculatorRoot()
    }
}
