package com.evothings.mhand.presentation.feature.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.evothings.mhand.presentation.feature.aboutService.AboutServiceScreen
import com.evothings.mhand.presentation.feature.auth.ui.AuthScreen
import com.evothings.mhand.presentation.feature.auth.viewmodal.AuthViewModel
import com.evothings.mhand.presentation.feature.card.ui.CardScreen
import com.evothings.mhand.presentation.feature.card.viewmodel.CardViewModel
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogScreen
import com.evothings.mhand.presentation.feature.catalog.viewmodel.CatalogViewModel
import com.evothings.mhand.presentation.feature.home.ui.HomeScreen
import com.evothings.mhand.presentation.feature.home.ui.stories.StoriesScreen
import com.evothings.mhand.presentation.feature.home.ui.stories.StoryContents
import com.evothings.mhand.presentation.feature.home.viewmodel.HomeViewModel
import com.evothings.mhand.presentation.feature.home.viewmodel.story.StoriesViewModel
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.news.ui.ArticleScreen
import com.evothings.mhand.presentation.feature.news.ui.NewsScreen
import com.evothings.mhand.presentation.feature.news.viewmodel.NewsViewModel
import com.evothings.mhand.presentation.feature.news.viewmodel.article.ArticleViewModel
import com.evothings.mhand.presentation.feature.onboarding.ui.screen.WelcomeOnboarding
import com.evothings.mhand.presentation.feature.onboarding.viewmodel.IntroductionViewModel
import com.evothings.mhand.presentation.feature.product.ui.ProductInfoScreen
import com.evothings.mhand.presentation.feature.shared.screen.ImageViewScreen
import com.evothings.mhand.presentation.feature.shared.screen.confirmCode.viewmodel.model.ConfirmCodeUseCase
import com.evothings.mhand.presentation.feature.shops.ui.ShopsScreen
import com.evothings.mhand.presentation.feature.shops.viewmodel.ShopsViewModel
import com.evothings.mhand.presentation.feature.splash.ui.LoadingTechnicalServiceScreen
import com.evothings.mhand.presentation.feature.splash.ui.SplashScreen
import com.evothings.mhand.presentation.feature.splash.viewmodel.SplashViewModel

val localNavController = compositionLocalOf<NavHostController>{
    error("LocalNavController not provided")
}

fun NavGraphBuilder.buildNavigation(
    navController: NavHostController
){
    // StartScreen
    composable<NavGraph.StartScreens.Splash>(
        exitTransition = { fadeOut(tween(500)) }
    ){
        val splashViewModel = hiltViewModel<SplashViewModel>()
        SplashScreen(
            vm = splashViewModel,
            openMainScreen = { navController.navigate(NavGraph.BottomNav.Home) },
            openOnboardingIntro = { navController.navigate(NavGraph.StartScreens.OnboardingIntro) },
        )
    }

    composable<NavGraph.StartScreens.OnboardingIntro>(
        exitTransition = { fadeOut(tween(500)) }
    ){
        val introductionViewModel = hiltViewModel<IntroductionViewModel>()

        WelcomeOnboarding(
            openMainScreen = { navController.navigate(NavGraph.BottomNav.Home)},
            vm = introductionViewModel
        )
    }

    // Auth
    composable<NavGraph.Auth.SecureCode> {
    }

    composable<NavGraph.Auth.SecureCode.Enter> {

    }

    composable<NavGraph.Auth.SecureCode.Create> {

    }

    composable<NavGraph.Auth.AuthenticationScreen> {
        val authViewModel = hiltViewModel<AuthViewModel>()

        AuthScreen(
            vm = authViewModel,
            openMainScreen = { navController.navigate(NavGraph.BottomNav.Home) },
            openConfirmationCode = { phone ->
                navController.navigate(
                    NavGraph.ConfirmationCode(phone, ConfirmCodeUseCase.AUTH.ordinal)
                )
            },
            openEnterSecureCode = { phone -> navController.navigate(NavGraph.Auth.SecureCode.Enter(phone)) }
        )
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

    composable<NavGraph.BottomNav.Home.Story>(
    enterTransition = { scaleIn(tween(300)) + fadeIn(tween(300)) },
    exitTransition = {
        slideOutVertically(
            animationSpec = tween(300),
            targetOffsetY = { it }
        )
    }
    ){
        val args = it.toRoute<NavGraph.BottomNav.Home.Story>()
        val storiesViewModel = hiltViewModel<StoriesViewModel>()

        StoriesScreen(
            vm = storiesViewModel,
            storyIndex = args.storyIndex,
            openMainScreen = { navController.navigate(NavGraph.BottomNav.Home) }
        )
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
        val newsVm = hiltViewModel<NewsViewModel>()

        NewsScreen(
            vm = newsVm,
            openArticle = { id -> navController.navigate(NavGraph.Other.NewsArticle(id)) },
            openMainScreen = { navController.navigate(NavGraph.BottomNav.Home) }
        )
    }

    composable<NavGraph.Other.Shops>(
        enterTransition = { fadeIn(tween(200)) }
    ) {
        val shops = hiltViewModel<ShopsViewModel>()
        ShopsScreen(vm = shops)
    }

    composable<NavGraph.Other.Favourites> {

    }

    composable<NavGraph.Other.AboutService> {
        AboutServiceScreen()
    }


    composable<NavGraph.Other.NewsArticle> {
        val articleId = it.toRoute<NavGraph.Other.NewsArticle>().articleId
        val articleVm = hiltViewModel<ArticleViewModel>()
        ArticleScreen(
            vm = articleVm,
            id = articleId,
            onBack = { navController.popBackStack() },
            openAnotherArticle = { id -> navController.navigate(NavGraph.Other.NewsArticle(id)) }
        )
    }

    // ProductInfo
    composable<NavGraph.ProductInfo> {
        ProductInfoScreen()
    }

    // AddressMap
    composable<NavGraph.AddressMap> {

    }

    // ConfirmationCode
    composable<NavGraph.ConfirmationCode> {

    }

    // ImageView
    composable<NavGraph.ImageView> {
        val args = it.toRoute<NavGraph.ImageView>()

        ImageViewScreen(
            imageLink = args.link,
            onBack = { navController.popBackStack() }
        )
    }
}