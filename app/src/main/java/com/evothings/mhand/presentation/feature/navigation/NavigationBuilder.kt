package com.evothings.mhand.presentation.feature.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.evothings.mhand.presentation.feature.home.ui.HomeScreen
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.ChooseCityScreen
import com.evothings.mhand.presentation.feature.shared.screen.chooseCity.viewmodel.ChooseCityViewModel
import com.evothings.mhand.presentation.feature.splash.LoadingTechnicalServiceScreen
import com.evothings.mhand.presentation.feature.splash.SplashScreen

val localNavController = compositionLocalOf<NavHostController>{
    error("LocalNavController not provided")
}

fun NavGraphBuilder.buildNavigation(
    navController: NavHostController
){
    // StartScreen
    composable<NavGraph.StartScreens.Splash>{
        SplashScreen(long = "")
    }

    composable<NavGraph.StartScreens.OnboardingIntro>{}

    // AppStatus
    composable<NavGraph.AppStatus.TechnicalWorks>{
        LoadingTechnicalServiceScreen()
    }

    // BottomNav
    composable<NavGraph.BottomNav.Home> {
        HomeScreen()
    }

    composable<NavGraph.BottomNav.Home.Story> {

    }

}