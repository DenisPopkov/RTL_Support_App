package ru.popkov.rtl_support_app.models.interactor

import ru.popkov.rtl_support_app.models.SubscriptionModel
import ru.popkov.rtl_support_app.models.SubscriptionType

class RTLRepository {
    fun loadRTLSubscriptions(): List<SubscriptionModel> {
        return gameDetailMock
    }
}

val gameDetailMock = listOf(
    SubscriptionModel(
        subscriptionPrice = 189,
        subscriptionType = SubscriptionType.MONTH,
    ),
    SubscriptionModel(
        subscriptionPrice = 1890,
        subscriptionType = SubscriptionType.ANNUAL,
    )
)