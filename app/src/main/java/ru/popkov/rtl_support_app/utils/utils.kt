package ru.popkov.rtl_support_app.utils

import ru.popkov.rtl_support_app.models.SubscriptionType

fun getSubscriptionAmountByType(subscriptionType: SubscriptionType) = when (subscriptionType) {
    SubscriptionType.MONTH -> 1
    else -> 12
}