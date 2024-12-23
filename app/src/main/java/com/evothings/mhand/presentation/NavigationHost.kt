package com.evothings.mhand.presentation

import android.content.Context
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.evothings.mhand.presentation.feature.navigation.DestinationResolver
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.BottomBarNavigation
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.navigation.buildNavigation
import com.evothings.mhand.presentation.feature.navigation.graph.shouldShowNavBar

@Composable
fun NavigationHost(
//    snackbarItem: SnackbarItem?,
    navController: NavHostController?
) {

    if (navController == null) return

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = remember(currentBackStackEntry) {
        DestinationResolver.resolveClassByRoute(currentBackStackEntry?.destination?.route)
    }

    HostScaffold(
//        snackbarItem = snackbarItem,
        bottomBar = {
            val context = LocalContext.current
            val showNavBar = remember(currentRoute) { currentRoute.shouldShowNavBar() }

            if (showNavBar) {
                BottomBarNavigation(
                    currentRoute = currentRoute,
//                    openScreen = { route -> navController.navigate(route) },
//                    openWebPageScreen = { openWebPageScreen(context, it) },
//                    openPhoneConfirmationScreen = { phone ->
//                        navController.navigate(
//                            NavGraph.ConfirmationCode(phone, ConfirmCodeUseCase.COUPON.ordinal)
//                        )
//                    }
                )
            }
        }
    ) { scaffoldPadding ->
        NavHost(
            modifier = Modifier.padding(scaffoldPadding),
            navController = navController,
            startDestination = NavGraph.StartScreens.Splash,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            this.buildNavigation(navController)
        }
    }

}

@Composable
private fun HostScaffold(
//    snackbarItem: SnackbarItem?,
    bottomBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }

//    LaunchedEffect(snackbarItem) {
//        if (snackbarItem != null) {
//            snackbarHostState.showSnackbar(
//                message = "${snackbarItem.title}\n${snackbarItem.subtitle}"
//            )
//        }
//    }

    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                contentAlignment = Alignment.TopCenter
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { snackbarData ->
                        val messageParts = snackbarData.visuals.message.split("\n")
//                        if (messageParts.size == 2) {
////                            Snackbar(
////                                messageTitle = messageParts[0],
////                                messageBody = messageParts[1],
////                            )
//                        }
                    }
                )
            }
        },
        bottomBar = bottomBar,
        content = content,
    )

}

//private fun openWebPageScreen(context: Context, screen: WebPageScreen) {
//    val host = StringBuilder("https://mhand.ru/")
//    val path = if (screen == WebPageScreen.VACANCIES) "vacancy/" else "faq/"
//    val url = host.append(path).toString()
//
//    tryOpenWebPage(context, url)
//}