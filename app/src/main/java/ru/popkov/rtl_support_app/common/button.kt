package ru.popkov.rtl_support_app.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.popkov.rtl_support_app.R
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextRegular16

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onClick: () -> Unit = {},
) {
    TextButton(
        onClick = { onClick.invoke() },
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 18.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        shape = RoundedCornerShape(size = 2.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = buttonText,
            style = GeometriaTextRegular16,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview
@Composable
private fun CommonButtonPreview() {
    CommonButton(buttonText = stringResource(id = R.string.subscribe_button))
}