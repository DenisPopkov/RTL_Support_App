package ru.popkov.rtl_support_app.models

data class SubscriptionModelState(
    val subscriptionModel: List<SubscriptionModel> = emptyList(),
    val selectedCardIndex: Int = 0,
    val isLoading: Boolean = false,
)