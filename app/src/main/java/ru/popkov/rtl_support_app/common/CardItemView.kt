package ru.popkov.rtl_support_app.common

import android.content.Context
import android.text.SpannableString
import android.text.style.BulletSpan
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.view.isVisible
import ru.popkov.rtl_support_app.R
import ru.popkov.rtl_support_app.models.SubscriptionModel
import ru.popkov.rtl_support_app.models.SubscriptionType
import ru.popkov.rtl_support_app.utils.dp
import ru.popkov.rtl_support_app.utils.getSubscriptionAmountByType

class CardItemView(
    context: Context,
    attrs: AttributeSet?,
) : ConstraintLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.item_card, this).setPadding(0, 0, 0, 12.dp)
    }

    private fun List<String>.toBulletedList(): CharSequence {
        return SpannableString(this.joinToString("\n")).apply {
            this@toBulletedList.foldIndexed(0) { index, acc, span ->
                val end = acc + span.length + if (index != this@toBulletedList.size - 1) 1 else 0
                this.setSpan(BulletSpan(16), acc, end, 0)
                end
            }
        }
    }

    fun bindData(
        subscription: SubscriptionModel,
        isSelected: Boolean,
        onClickListener: () -> Unit
    ) {
        val card = findViewById<ConstraintLayout>(R.id.root)
        val priceLabel = findViewById<TextView>(R.id.priceLabel)
        val amountDescription = findViewById<TextView>(R.id.subscriptionSubtitle)
        val popularLabel = findViewById<TextView>(R.id.subscriptionPopularLabel)
        val subscriptionDescription = findViewById<TextView>(R.id.subscriptionDescription)
        val monthAmount = getSubscriptionAmountByType(subscription.subscriptionType)
        val amount = if (monthAmount <= 1) "" else monthAmount

        priceLabel.text = buildSpannedString {
            color(context.getColor(R.color.black)) {
                bold {
                    append(
                        context.getString(
                            R.string.subscription_price,
                            subscription.subscriptionPrice.toString(),
                        )
                    )
                }
                append(" ")
            }
            color(context.getColor(R.color.black)) {
                append(
                    context.resources.getQuantityString(
                        R.plurals.month,
                        monthAmount,
                        amount,
                    ).trim()
                )
            }
        }

        amountDescription.text = context.resources.getQuantityString(
            R.plurals.money_withdraw,
            monthAmount,
            amount
        ).trim()

        val bulletSpanList = listOf(
            context.getString(R.string.subscription_description_first_point),
            context.getString(R.string.subscription_description_second_point),
            context.getString(R.string.subscription_description_third_point)
        ).toBulletedList()

        subscriptionDescription.text = bulletSpanList
        popularLabel.isVisible = subscription.subscriptionType == SubscriptionType.MONTH
        card.isSelected = isSelected

        card.setOnClickListener {
            onClickListener.invoke()
        }
    }
}