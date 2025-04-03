package xyz.rfsfernandes.tmdbdemo.presentation.ui.composables

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ThemedButton(
    modifier: Modifier = Modifier,
    text: String = "",
    color: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ),
    tertiary: Boolean = false,
    onCLick: () -> Unit
) {
    val buttonColors = if (tertiary) {
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        color
    }
    Button(
        onClick = onCLick,
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
        colors = buttonColors
    ) {
        Text(
            text = text
        )
    }
}
