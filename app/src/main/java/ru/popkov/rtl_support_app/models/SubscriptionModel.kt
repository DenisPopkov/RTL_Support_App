package ru.popkov.rtl_support_app.models

enum class SubscriptionType {
    MONTH,
    ANNUAL,
    ;
}

data class SubscriptionModel(
    val subscriptionPrice: Int,
    val subscriptionRenewal: String,
    val subscriptionType: SubscriptionType,
    val subscriptionHint: String,
    val subscriptionDescription: String,
)