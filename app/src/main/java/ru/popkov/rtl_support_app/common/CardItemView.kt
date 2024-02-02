package ru.popkov.rtl_support_app.common

import android.content.Context
import android.text.SpannableString
import android.text.style.BulletSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
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
        val typedValue = TypedValue()
        val theme = context.theme
        val colorPrimaryResId = com.google.android.material.R.attr.colorOnSecondaryContainer
        theme.resolveAttribute(colorPrimaryResId, typedValue, true)
        val colorOnSecondaryContainer = ContextCompat.getColor(context, typedValue.resourceId)
        return SpannableString(joinToString("\n")).apply {
            this@toBulletedList.foldIndexed(0) { index, acc, span ->
                val end = acc + span.length + if (index != this@toBulletedList.size - 1) 1 else 0
                setSpan(BulletSpan(10.dp, colorOnSecondaryContainer, 2.dp), acc, end, 0)
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
        val monthLabel = findViewById<TextView>(R.id.monthLabel)
        val amountDescription = findViewById<TextView>(R.id.subscriptionSubtitle)
        val popularLabel = findViewById<TextView>(R.id.subscriptionPopularLabel)
        val subscriptionDescription = findViewById<TextView>(R.id.subscriptionDescription)
        val monthAmount = getSubscriptionAmountByType(subscription.subscriptionType)

        priceLabel.text = "${
            context.getString(
                R.string.subscription_price,
                subscription.subscriptionPrice.toString(),
            )
        } "

        monthLabel.text = "/ ${
            context.resources.getQuantityString(
                R.plurals.month,
                monthAmount,
                monthAmount,
            ).trim()
        }"

        amountDescription.text = context.resources.getQuantityString(
            R.plurals.money_withdraw,
            monthAmount,
            monthAmount,
        ).trim()

        val bulletSpanList = listOf(
            context.getString(R.string.subscription_description_first_point),
            context.getString(R.string.subscription_description_second_point),
            context.getString(R.string.subscription_description_third_point)
        ).toBulletedList()

        subscriptionDescription.text = bulletSpanList
        popularLabel.isInvisible = subscription.subscriptionType != SubscriptionType.MONTH
        card.isSelected = isSelected

        card.setOnClickListener {
            onClickListener.invoke()
        }
    }
}