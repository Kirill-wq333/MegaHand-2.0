package com.evothings.mhand.presentation.feature.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.evothings.mhand.presentation.feature.card.ui.CardScreen
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogScreen
import com.evothings.mhand.presentation.feature.home.ui.HomeScreen
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.splash.ui.LoadingTechnicalServiceScreen
import com.evothings.mhand.presentation.feature.splash.ui.SplashScreen

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

    composable<NavGraph.StartScreens.OnboardingIntro>{

    }

    // Auth
    composable<NavGraph.Auth.SecureCode> {

    }

    composable<NavGraph.Auth.AuthenticationScreen> {

    }

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

    composable<NavGraph.BottomNav.Card> {
        CardScreen()
    }

    composable<NavGraph.BottomNav.Other> {

    }

    composable<NavGraph.BottomNav.Profile> {

    }

    composable<NavGraph.BottomNav.ShoppingCart> {

    }

    composable<NavGraph.BottomNav.ShoppingCart.Checkout> {

    }

    composable<NavGraph.BottomNav.Catalog> {
        CatalogScreen()
    }

    // Other
    composable<NavGraph.Other.News> {

    }

    composable<NavGraph.Other.Shops> {

    }

    composable<NavGraph.Other.Favourites> {

    }

    composable<NavGraph.Other.AboutService> {

    }

    composable<NavGraph.Other.Help> {

    }

    composable<NavGraph.Other.Vacancies> {

    }

    composable<NavGraph.Other.NewsArticle> {

    }

    // ProductInfo
    composable<NavGraph.ProductInfo> {

    }

    // AddressMap
    composable<NavGraph.AddressMap> {

    }

    // ConfirmationCode
    composable<NavGraph.ConfirmationCode> {

    }

    // ImageView
    composable<NavGraph.ImageView> {

    }
}