package com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.bottomsheet

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.Category.components.categories.LazyVerticalGrid
import com.evothings.mhand.presentation.feature.coupon.ui.Coupon
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.model.WebPageScreen
import com.evothings.mhand.presentation.feature.navigation.bottomBar.viewmodel.OtherViewModel
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.navigation.graph.Screen
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Preview
@Composable
fun OtherBottomSheetPreview(){
    MegahandTheme {
        Content(
            darkMode = false,
            enableCouponButton = true,
            couponAmount = 300,
            openAppScreen = {},
            openVacanciesPage = { },
            openHelpPage = {},
            onChangeTheme = {},
            openCouponPhoneConfirmationScreen = {}
        )
    }
}


@Composable
fun OtherBottomSheet(
    vm: OtherViewModel = hiltViewModel(),
    onDismissBottomSheet: () -> Unit,
    openAppScreen: (Screen) -> Unit,
    openWebPageScreen: (WebPageScreen) -> Unit,
    openPhoneConfirmationScreen: (String) -> Unit,
){
    val isDarkThemeEnabled by vm.isDarkThemeEnabled.collectAsState(initial = false)
    val couponAmount by vm.couponAmount.collectAsState()
    val enableCouponButton by vm.isCouponGatheringAvailable.collectAsState()

    MhandModalBottomSheet(
        onDismissRequest = onDismissBottomSheet
    ) { hide ->
        Content(
            darkMode = isDarkThemeEnabled,
            enableCouponButton = enableCouponButton,
            couponAmount = couponAmount,
            openAppScreen = { screen ->
                openAppScreen(screen); hide()
            },
            openHelpPage = { openWebPageScreen(WebPageScreen.HELP) },
            openVacanciesPage = { openWebPageScreen(WebPageScreen.VACANCIES) },
            openCouponPhoneConfirmationScreen = openPhoneConfirmationScreen,
            onChangeTheme = { vm.changeTheme() }
        )
    }
}

@Composable
private fun Content(
    darkMode: Boolean,
    enableCouponButton: Boolean,
    couponAmount: Int,
    openAppScreen: (Screen) -> Unit,
    openVacanciesPage: () -> Unit,
    openHelpPage: () -> Unit,
    onChangeTheme: () -> Unit,
    openCouponPhoneConfirmationScreen: (String) -> Unit,
) {
    val context = LocalContext.current

    var couponBottomSheetVisible by remember { mutableStateOf(false) }

    val items: Map<String, Screen> = remember {
        mapOf(
            context.getString(R.string.favourites_screen_title) to NavGraph.Other.Favourites,
            context.getString(R.string.shops) to NavGraph.Other.Shops,
            context.getString(R.string.news_screen_title) to NavGraph.Other.News,
            context.getString(R.string.about_service) to NavGraph.Other.AboutService,
            context.getString(R.string.vacancies) to NavGraph.Other.Vacancies,
            context.getString(R.string.help) to NavGraph.Other.Help,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorScheme.onSecondary,
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp
                )
                )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = MaterialTheme.paddings.extraGiant,
                    start = MaterialTheme.paddings.extraGiant,
                    bottom = MaterialTheme.paddings.extraLarge,
                    end = MaterialTheme.paddings.extraGiant
                )
        ) {
            Text(
                text = stringResource(R.string.other),
                textAlign = TextAlign.Start,
                color = colorScheme.secondary,
                style = MegahandTypography.headlineMedium,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(items.entries.toList()) { item ->
                    Button(
                        text = item.key,
                        backgroundColor = colorScheme.secondary.copy(alpha = .05f),
                        onClick = {
                            when (item.value) {
                                is NavGraph.Other.Help -> openHelpPage()
                                is NavGraph.Other.Vacancies -> openVacanciesPage()
                                else -> openAppScreen(item.value)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
            BottonFillMaxSizeItem(
                text = stringResource(R.string.gather_coupon_title, couponAmount),
                backgroundColor = colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
            ThemeSwitch(
                context = context,
                isDarkThemeEnabled = darkMode,
                onClick = onChangeTheme
            )
        }
    }

}


@Composable
private fun ThemeSwitch(
    context: Context,
    isDarkThemeEnabled: Boolean,
    onClick: () -> Unit
) {

    val themeName = remember(isDarkThemeEnabled) {
        if (isDarkThemeEnabled) {
            context.getString(R.string.light_theme_name)
        } else {
            context.getString(R.string.dark_theme_name)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = stringResource(R.string.theme_switch, themeName),
            style = typography.headlineSmall
        )
    }

}

@Composable
private fun BottonFillMaxSizeItem(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color
){

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = MegahandShapes.medium
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            color = colorScheme.secondary,
            style = MegahandTypography.labelLarge,
            modifier = Modifier
                .padding(vertical = MaterialTheme.paddings.extraLarge)
        )
    }

}
