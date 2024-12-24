package com.evothings.mhand.presentation.feature.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.evothings.mhand.presentation.feature.card.ui.CardScreen
import com.evothings.mhand.presentation.feature.card.viewmodel.CardViewModel
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogScreen
import com.evothings.mhand.presentation.feature.catalog.viewmodel.CatalogViewModel
import com.evothings.mhand.presentation.feature.home.ui.HomeScreen
import com.evothings.mhand.presentation.feature.home.viewmodel.HomeViewModel
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.shared.screen.confirmCode.viewmodel.model.ConfirmCodeUseCase
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

    composable<NavGraph.Auth.SecureCode.Enter> {

    }

    composable<NavGraph.Auth.SecureCode.Create> {

    }

    composable<NavGraph.Auth.AuthenticationScreen> {

    }

    // AppStatus
    composable<NavGraph.AppStatus.TechnicalWorks>{
        LoadingTechnicalServiceScreen()
    }

    // BottomNav
    composable<NavGraph.BottomNav.Home>(
        enterTransition = { fadeIn(tween(350)) },
    ){

        val homeViewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(
            vm = homeViewModel,
            openProfile = { navController.navigate(NavGraph.BottomNav.Profile) },
            openStoriesScreen = { i -> navController.navigate(NavGraph.BottomNav.Home.Story(i)) },
            openProductInfoScreen = { id -> navController.navigate(NavGraph.ProductInfo(id)) },
            openCouponPhoneConfirmationScreen = { phone ->
                navController.navigate(
                    NavGraph.ConfirmationCode(phone, ConfirmCodeUseCase.COUPON.ordinal)
                )
            }
        )
    }

    composable<NavGraph.BottomNav.Home.Story> {

    }

    composable<NavGraph.BottomNav.Card>(
        enterTransition = { fadeIn(tween(500)) }
    ) {
        val cardViewModel = hiltViewModel<CardViewModel>()

        CardScreen(
            vm = cardViewModel,
            openProfile = {navController.navigate(NavGraph.BottomNav.Profile)}
        )
    }

    composable<NavGraph.BottomNav.Profile> {

    }

    composable<NavGraph.BottomNav.ShoppingCart> {

    }

    composable<NavGraph.BottomNav.ShoppingCart.Checkout> {

    }

    composable<NavGraph.BottomNav.Catalog> {
        val catalogVm = hiltViewModel<CatalogViewModel>()

        CatalogScreen(
            vm = catalogVm
        )
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