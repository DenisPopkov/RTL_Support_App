package ru.popkov.rtl_support_app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.popkov.rtl_support_app.R
import ru.popkov.rtl_support_app.common.CommonButton
import ru.popkov.rtl_support_app.screens.navigation.Screens

@Composable
fun MainScreen(
    navController: NavController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CommonButton(buttonText = stringResource(id = R.string.xml)) {
            navController.navigate(Screens.XML.route)
        }
        Spacer(modifier = Modifier.height(40.dp))
        CommonButton(buttonText = stringResource(id = R.string.compose)) {
            navController.navigate(Screens.Compose.route)
        }
    }
}