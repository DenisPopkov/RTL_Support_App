package ru.popkov.rtl_support_app.utils

import android.content.res.Resources
import ru.popkov.rtl_support_app.models.SubscriptionType

fun getSubscriptionAmountByType(subscriptionType: SubscriptionType) = when (subscriptionType) {
    SubscriptionType.MONTH -> 1
    else -> 12
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()