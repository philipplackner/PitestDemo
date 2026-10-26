package com.plcoding.pitestdemo.presentation.calculator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.plcoding.pitestdemo.presentation.theme.CalculatorTheme

internal enum class CalculatorKeyType {
    Digit,
    Operator,
    Function,
    Equals,
}

@Composable
internal fun CalculatorKey(
    label: String,
    type: CalculatorKeyType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    val colorScheme = MaterialTheme.colorScheme
    val colors = when (type) {
        CalculatorKeyType.Digit -> ButtonDefaults.buttonColors(
            containerColor = colorScheme.surfaceContainerHighest,
            contentColor = colorScheme.onSurface,
        )
        CalculatorKeyType.Operator -> ButtonDefaults.buttonColors(
            containerColor = colorScheme.secondaryContainer,
            contentColor = colorScheme.onSecondaryContainer,
        )
        CalculatorKeyType.Function -> ButtonDefaults.buttonColors(
            containerColor = colorScheme.tertiaryContainer,
            contentColor = colorScheme.onTertiaryContainer,
        )
        CalculatorKeyType.Equals -> ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary,
        )
    }
    Button(
        onClick = onClick,
        modifier = modifier.then(
            if (contentDescription != null) {
                Modifier.semantics { this.contentDescription = contentDescription }
            } else {
                Modifier
            }
        ),
        shape = MaterialTheme.shapes.extraLarge,
        colors = colors,
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Composable
private fun CalculatorKeyPreviewContent() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CalculatorKeyType.entries.forEach { type ->
            CalculatorKey(
                label = when (type) {
                    CalculatorKeyType.Digit -> "7"
                    CalculatorKeyType.Operator -> "×"
                    CalculatorKeyType.Function -> "C"
                    CalculatorKeyType.Equals -> "="
                },
                type = type,
                onClick = {},
                modifier = Modifier.weight(1f).aspectRatio(1f),
            )
        }
    }
}

@Preview
@Composable
private fun CalculatorKeyLightPreview() {
    CalculatorTheme(darkTheme = false) {
        CalculatorKeyPreviewContent()
    }
}

@Preview
@Composable
private fun CalculatorKeyDarkPreview() {
    CalculatorTheme(darkTheme = true) {
        CalculatorKeyPreviewContent()
    }
}
