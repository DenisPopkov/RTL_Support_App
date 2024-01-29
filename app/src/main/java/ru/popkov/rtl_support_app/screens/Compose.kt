package ru.popkov.rtl_support_app.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.popkov.rtl_support_app.R
import ru.popkov.rtl_support_app.common.CommonButton
import ru.popkov.rtl_support_app.common.RTLSubscriptionCard
import ru.popkov.rtl_support_app.models.interactor.RTLRepository
import ru.popkov.rtl_support_app.ui.theme.BackgroundColor
import ru.popkov.rtl_support_app.ui.theme.BlackColor
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextBold28
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextRegular16
import ru.popkov.rtl_support_app.ui.theme.OrangeColor

@Composable
fun ComposeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = modifier
                    .padding(top = 16.dp, bottom = 36.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColor,
                    titleContentColor = BackgroundColor,
                ),
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "App bar logo"
                    )
                },
                navigationIcon = {
                    IconButton(modifier = modifier.offset(x = (-8).dp), onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_left_arrow),
                            contentDescription = "App bar nav icon"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->

        val subscriptions = RTLRepository().loadRTLSubscriptions()
        var selectedCardIndex by remember { mutableIntStateOf(value = 0) }

        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .background(color = BackgroundColor)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues = innerPadding),
        ) {

            Text(
                modifier = modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.label),
                style = GeometriaTextBold28,
                color = BlackColor,
            )
            Text(
                modifier = modifier.padding(top = 16.dp, bottom = 20.dp),
                text = stringResource(id = R.string.label_description),
                style = GeometriaTextRegular16,
                color = BlackColor,
            )

            subscriptions.forEachIndexed { index, data ->
                RTLSubscriptionCard(
                    subscriptionData = data,
                    isSelected = index == selectedCardIndex,
                    onClick = { selectedCardIndex = index }
                )
                Spacer(modifier = Modifier.padding(bottom = 12.dp))
            }

            SubscriptionOffer()

            Spacer(modifier = modifier.weight(1f))
            CommonButton(
                modifier = modifier.padding(bottom = 10.dp, top = 36.dp),
                buttonText = stringResource(id = R.string.subscribe_button)
            ) {}
        }
    }
}

@Composable
fun SubscriptionOffer() {
    val annotatedString = buildAnnotatedString {
        append(text = "${stringResource(id = R.string.decline_subscription)} ")

        pushStringAnnotation(tag = "policy", annotation = "https://google.com/policy")
        withStyle(style = SpanStyle(color = OrangeColor)) {
            append(text = stringResource(id = R.string.decline_subscription_description_first))
        }
        pop()

        append(text = " ${stringResource(id = R.string.decline_subscription_and)} ")

        pushStringAnnotation(tag = "terms", annotation = "https://google.com/terms")

        withStyle(style = SpanStyle(color = OrangeColor)) {
            append(text = stringResource(id = R.string.decline_subscription_description_second))
        }
        pop()
    }

    ClickableText(text = annotatedString, style = GeometriaTextRegular16, onClick = { offset ->
        annotatedString.getStringAnnotations(tag = "policy", start = offset, end = offset)
            .firstOrNull()?.let {
                Log.d("policy URL", it.item)
            }

        annotatedString.getStringAnnotations(tag = "terms", start = offset, end = offset)
            .firstOrNull()?.let {
                Log.d("terms URL", it.item)
            }
    })
}

@Preview(showBackground = true)
@Composable
private fun ComposeScreenPreview() {
    ComposeScreen(
        modifier = Modifier,
        navController = rememberNavController()
    )
}

