package ru.popkov.rtl_support_app.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.popkov.rtl_support_app.R
import ru.popkov.rtl_support_app.models.SubscriptionModel
import ru.popkov.rtl_support_app.models.SubscriptionType
import ru.popkov.rtl_support_app.models.interactor.RTLRepository
import ru.popkov.rtl_support_app.ui.theme.BackgroundCarbColor
import ru.popkov.rtl_support_app.ui.theme.BlackColor
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextBold20
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextMedium14
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextRegular12
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextRegular20
import ru.popkov.rtl_support_app.ui.theme.GrayColor
import ru.popkov.rtl_support_app.ui.theme.OrangeColor
import ru.popkov.rtl_support_app.utils.getSubscriptionAmountByType

@Composable
fun RTLSubscriptionCard(
    modifier: Modifier = Modifier,
    subscriptionData: SubscriptionModel,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val subscriptionAmount = getSubscriptionAmountByType(subscriptionData.subscriptionType)
    val amount = if (subscriptionAmount <= 1) "" else subscriptionAmount
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BackgroundCarbColor, shape = RoundedCornerShape(size = 4.dp))
            .border(
                width = 2.dp,
                color = if (isSelected) OrangeColor else BackgroundCarbColor,
                shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(all = 20.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick.invoke()
            },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(
                    text = "${
                        stringResource(
                            id = R.string.subscription_price,
                            subscriptionData.subscriptionPrice
                        )
                    } ", style = GeometriaTextBold20
                )
                Text(
                    text = pluralStringResource(
                        id = R.plurals.month,
                        count = subscriptionAmount,
                        amount,
                    ), style = GeometriaTextRegular20
                )
            }
            AnimatedVisibility(visible = subscriptionData.subscriptionType == SubscriptionType.MONTH) {
                Text(
                    text = stringResource(id = R.string.popular_label),
                    style = GeometriaTextRegular12,
                    modifier = Modifier
                        .background(color = OrangeColor)
                        .padding(vertical = 10.dp, horizontal = 15.dp),
                    color = BlackColor
                )
            }
        }

        Text(
            modifier = modifier.padding(vertical = 12.dp),
            text = pluralStringResource(
                id = R.plurals.money_withdraw,
                count = subscriptionAmount,
                amount
            ),
            style = GeometriaTextMedium14,
            color = GrayColor
        )

        val bulletSpanList = listOf(
            stringResource(id = R.string.subscription_description_first_point),
            stringResource(id = R.string.subscription_description_second_point),
            stringResource(id = R.string.subscription_description_third_point),
        )

        bulletSpanList.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(size = 4.dp)
                ) {
                    drawCircle(BlackColor)
                }
                Text(text = it, style = GeometriaTextMedium14)
            }
        }
    }
}

@Preview
@Composable
private fun RTLSubscriptionCardPreview() {
    RTLSubscriptionCard(
        modifier = Modifier,
        subscriptionData = RTLRepository().loadRTLSubscriptions().first(),
    )
}