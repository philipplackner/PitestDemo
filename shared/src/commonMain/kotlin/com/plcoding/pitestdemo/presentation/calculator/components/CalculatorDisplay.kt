package com.plcoding.pitestdemo.presentation.calculator.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.pitestdemo.presentation.theme.CalculatorTheme

@Composable
internal fun CalculatorDisplay(
    expression: String,
    preview: String?,
    error: String?,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.End,
    ) {
        BasicText(
            text = expression.ifEmpty { "0" },
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.displayLarge.copy(
                color = if (expression.isEmpty()) colorScheme.onSurfaceVariant else colorScheme.onSurface,
                textAlign = TextAlign.End,
            ),
            maxLines = 2,
            overflow = TextOverflow.StartEllipsis,
            autoSize = TextAutoSize.StepBased(minFontSize = 28.sp, maxFontSize = 64.sp),
        )
        Text(
            text = error ?: preview.orEmpty(),
            style = MaterialTheme.typography.headlineSmall,
            color = if (error != null) colorScheme.error else colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun CalculatorDisplayPreviewContent(
    expression: String,
    preview: String? = null,
    error: String? = null,
) {
    Surface {
        CalculatorDisplay(
            expression = expression,
            preview = preview,
            error = error,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        )
    }
}

@Preview
@Composable
private fun CalculatorDisplayEmptyPreview() {
    CalculatorTheme(darkTheme = false) {
        CalculatorDisplayPreviewContent(expression = "")
    }
}

@Preview
@Composable
private fun CalculatorDisplayWithPreviewPreview() {
    CalculatorTheme(darkTheme = false) {
        CalculatorDisplayPreviewContent(expression = "12×(3+4)", preview = "84")
    }
}

@Preview
@Composable
private fun CalculatorDisplayErrorPreview() {
    CalculatorTheme(darkTheme = false) {
        CalculatorDisplayPreviewContent(expression = "5÷0", error = "Cannot divide by zero")
    }
}

@Preview
@Composable
private fun CalculatorDisplayLongExpressionPreview() {
    CalculatorTheme(darkTheme = false) {
        CalculatorDisplayPreviewContent(
            expression = "3+4×2÷(1-5)^2^3+1234567×89-(12.5%4)",
            preview = "109876466.000122",
        )
    }
}

@Preview
@Composable
private fun CalculatorDisplayDarkPreview() {
    CalculatorTheme(darkTheme = true) {
        CalculatorDisplayPreviewContent(expression = "12×(3+4)", preview = "84")
    }
}
