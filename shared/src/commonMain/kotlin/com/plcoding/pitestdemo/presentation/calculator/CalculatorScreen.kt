package com.plcoding.pitestdemo.presentation.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.pitestdemo.calculator.Calculator
import com.plcoding.pitestdemo.presentation.UiText
import com.plcoding.pitestdemo.presentation.calculator.components.CalculatorDisplay
import com.plcoding.pitestdemo.presentation.calculator.components.CalculatorKey
import com.plcoding.pitestdemo.presentation.calculator.components.CalculatorKeyType
import com.plcoding.pitestdemo.presentation.theme.CalculatorTheme
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import pitestdemo.shared.generated.resources.Res
import pitestdemo.shared.generated.resources.cd_clear
import pitestdemo.shared.generated.resources.cd_delete
import pitestdemo.shared.generated.resources.error_division_by_zero

private data class KeypadKey(
    val label: String,
    val type: CalculatorKeyType,
    val action: CalculatorAction,
    val weight: Float = 1f,
    val contentDescription: StringResource? = null,
)

private fun digit(label: String) = KeypadKey(label, CalculatorKeyType.Digit, CalculatorAction.OnInput(label))

private fun operator(label: String, input: String = label) =
    KeypadKey(label, CalculatorKeyType.Operator, CalculatorAction.OnInput(input))

private val KEYPAD_ROWS = listOf(
    listOf(
        KeypadKey("C", CalculatorKeyType.Function, CalculatorAction.OnClearClick, contentDescription = Res.string.cd_clear),
        operator("("),
        operator(")"),
        KeypadKey("⌫", CalculatorKeyType.Function, CalculatorAction.OnDeleteClick, contentDescription = Res.string.cd_delete),
    ),
    listOf(digit("7"), digit("8"), digit("9"), operator("÷")),
    listOf(digit("4"), digit("5"), digit("6"), operator("×")),
    // The tokenizer only accepts ASCII '-', so the "−" key types a plain hyphen.
    listOf(digit("1"), digit("2"), digit("3"), operator("−", input = "-")),
    listOf(digit("0"), digit("."), operator("%"), operator("+")),
    listOf(
        operator("^"),
        KeypadKey("=", CalculatorKeyType.Equals, CalculatorAction.OnEqualsClick, weight = 3f),
    ),
)

@Composable
fun CalculatorRoot(
    viewModel: CalculatorViewModel = viewModel { CalculatorViewModel(Calculator()) },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CalculatorScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun CalculatorScreen(
    state: CalculatorState,
    onAction: (CalculatorAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .safeDrawingPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CalculatorDisplay(
            expression = state.expression,
            preview = state.preview,
            error = state.error?.asString(),
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth()
                .weight(1f),
        )
        Column(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth()
                .weight(2f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            KEYPAD_ROWS.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    row.forEach { key ->
                        CalculatorKey(
                            label = key.label,
                            type = key.type,
                            onClick = { onAction(key.action) },
                            contentDescription = key.contentDescription?.let { stringResource(it) },
                            modifier = Modifier
                                .weight(key.weight)
                                .fillMaxHeight(),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CalculatorScreenEmptyPreview() {
    CalculatorTheme(darkTheme = false) {
        CalculatorScreen(state = CalculatorState(), onAction = {})
    }
}

@Preview
@Composable
private fun CalculatorScreenTypingPreview() {
    CalculatorTheme(darkTheme = false) {
        CalculatorScreen(
            state = CalculatorState(expression = "12×(3+4)", preview = "84"),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun CalculatorScreenResultPreview() {
    CalculatorTheme(darkTheme = false) {
        CalculatorScreen(
            state = CalculatorState(expression = "84", isResult = true),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun CalculatorScreenErrorPreview() {
    CalculatorTheme(darkTheme = false) {
        CalculatorScreen(
            state = CalculatorState(
                expression = "5÷0",
                error = UiText.StringResource(Res.string.error_division_by_zero),
            ),
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun CalculatorScreenDarkPreview() {
    CalculatorTheme(darkTheme = true) {
        CalculatorScreen(
            state = CalculatorState(expression = "12×(3+4)", preview = "84"),
            onAction = {},
        )
    }
}

@Preview(widthDp = 840, heightDp = 600)
@Composable
private fun CalculatorScreenWidePreview() {
    CalculatorTheme(darkTheme = false) {
        CalculatorScreen(
            state = CalculatorState(expression = "12×(3+4)", preview = "84"),
            onAction = {},
        )
    }
}
