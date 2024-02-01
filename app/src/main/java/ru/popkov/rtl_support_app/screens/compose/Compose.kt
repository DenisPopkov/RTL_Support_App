package ru.popkov.rtl_support_app.screens.compose

import android.content.res.Configuration
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.popkov.rtl_support_app.R
import ru.popkov.rtl_support_app.common.CommonButton
import ru.popkov.rtl_support_app.common.RTLSubscriptionCard
import ru.popkov.rtl_support_app.common.SubscriptionOffer
import ru.popkov.rtl_support_app.screens.xml.SubscriptionsViewModel
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextBold28
import ru.popkov.rtl_support_app.ui.theme.GeometriaTextRegular16
import ru.popkov.rtl_support_app.ui.theme.RTLSupportAppTheme

@Composable
fun ComposeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    subscriptionsViewModel: SubscriptionsViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(top = 16.dp, bottom = 36.dp),
            ) {
                IconButton(
                    modifier = modifier.align(Alignment.CenterStart),
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_left_arrow),
                        contentDescription = "App bar nav icon",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Image(
                    modifier = modifier.align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "App bar logo",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                )
            }
        },
    ) { innerPadding ->

        val subscriptions = subscriptionsViewModel.subscriptionsData.collectAsState()
        val subscriptionData = subscriptions.value
        var selectedCardIndex by remember { mutableIntStateOf(value = 0) }

        AnimatedVisibility(visible = subscriptionData.isLoading) {
            CircularProgressIndicator()
        }

        Column(
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues = innerPadding),
        ) {

            Text(
                modifier = modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.label),
                style = GeometriaTextBold28,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                modifier = modifier.padding(top = 16.dp, bottom = 20.dp),
                text = stringResource(id = R.string.label_description),
                style = GeometriaTextRegular16,
                color = MaterialTheme.colorScheme.onSurface,
            )

            subscriptionData.subscriptionModel.forEachIndexed { index, data ->
                RTLSubscriptionCard(
                    modifier = modifier.padding(bottom = 12.dp),
                    subscriptionData = data,
                    isSelected = index == selectedCardIndex,
                    onClick = { selectedCardIndex = index },
                )
            }

            SubscriptionOffer()

            Spacer(modifier = modifier.weight(1f))
            CommonButton(
                modifier = modifier.padding(bottom = 10.dp, top = 36.dp),
                buttonText = stringResource(id = R.string.subscribe_button),
            ) {}
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Preview(apiLevel = Build.VERSION_CODES.R)
@Preview(apiLevel = Build.VERSION_CODES.P, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(locale = "ar")
@Composable
private fun ComposeScreenPreview() {
    RTLSupportAppTheme {
        Surface {
            ComposeScreen(
                modifier = Modifier,
                navController = rememberNavController(),
            )
        }
    }
}