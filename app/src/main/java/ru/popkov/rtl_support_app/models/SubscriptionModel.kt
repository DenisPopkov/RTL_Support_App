package ru.popkov.rtl_support_app.models

enum class SubscriptionType {
    MONTH,
    ANNUAL,
    ;
}

data class SubscriptionModel(
    val subscriptionPrice: Int,
    val subscriptionType: SubscriptionType,
)