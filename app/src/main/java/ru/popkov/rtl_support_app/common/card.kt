package ru.popkov.rtl_support_app.common

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.popkov.rtl_support_app.R
import ru.popkov.rtl_support_app.models.SubscriptionModel
import ru.popkov.rtl_support_app.models.SubscriptionType
import ru.popkov.rtl_support_app.models.interactor.RTLRepository
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextBold20
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextMedium14
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextRegular12
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextRegular20
import ru.popkov.rtl_support_app.ui.theme.RTLSupportAppTheme
import ru.popkov.rtl_support_app.utils.getSubscriptionAmountByType
import java.text.NumberFormat
import java.util.Locale

@Composable
fun RTLSubscriptionCard(
    modifier: Modifier = Modifier,
    subscriptionData: SubscriptionModel,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val subscriptionAmount = getSubscriptionAmountByType(subscriptionData.subscriptionType)
    val textColor = MaterialTheme.colorScheme.onSecondaryContainer

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(size = 2.dp)
            )
            .border(
                width = 2.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(size = 2.dp)
            )
            .padding(horizontal = 20.dp)
            .padding(bottom = 16.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick.invoke()
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Row {
                Text(
                    text = "${
                        stringResource(
                            id = R.string.subscription_price,
                            subscriptionData.subscriptionPrice
                        )
                    } ",
                    style = GeometriaTextBold20,
                    color = textColor,
                )
                Text(
                    text = "/ ",
                    style = GeometriaTextRegular20,
                    color = textColor,
                )
                Text(
                    text = pluralStringResource(
                        id = R.plurals.month,
                        count = subscriptionAmount,
                        NumberFormat.getInstance(Locale.getDefault()).format(subscriptionAmount),
                    ).trim(),
                    style = GeometriaTextRegular20,
                    color = textColor,
                )
            }
            Text(
                text = stringResource(id = R.string.popular_label),
                style = GeometriaTextRegular12,
                modifier = Modifier
                    .alpha(if (subscriptionData.subscriptionType == SubscriptionType.MONTH) 100f else 0f)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(vertical = 10.dp, horizontal = 15.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }

        Text(
            modifier = modifier.padding(vertical = 12.dp),
            text = pluralStringResource(
                id = R.plurals.money_withdraw,
                count = subscriptionAmount,
                NumberFormat.getInstance(Locale.getDefault()).format(subscriptionAmount),
            ).trim(),
            style = GeometriaTextMedium14,
            color = MaterialTheme.colorScheme.secondary
        )

        val bulletSpanList = listOf(
            stringResource(id = R.string.subscription_description_first_point),
            stringResource(id = R.string.subscription_description_second_point),
            stringResource(id = R.string.subscription_description_third_point),
        )

        bulletSpanList.forEach {
            val isSingleLine = it.length < 42 // чтобы bullet рисовался по середине первой строки
            Row(
                modifier = modifier.padding(start = 8.dp, end = 20.dp),
                verticalAlignment = if (!isSingleLine) Alignment.Top else Alignment.CenterVertically,
            ) {
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .padding(top = if (!isSingleLine) 6.dp else 0.dp)
                        .size(4.dp)
                ) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        drawCircle(color = textColor)
                    }
                }
                Text(
                    text = it,
                    style = GeometriaTextMedium14,
                    color = textColor,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RTLSubscriptionCardPreview() {
    RTLSupportAppTheme {
        Surface {
            RTLSubscriptionCard(
                subscriptionData = RTLRepository().loadRTLSubscriptions().first(),
            )
        }
    }
}