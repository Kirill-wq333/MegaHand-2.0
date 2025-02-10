package com.evothings.mhand.presentation.feature.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.evothings.mhand.presentation.feature.aboutService.AboutServiceScreen
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.auth.viewmodel.AuthViewModel
import com.evothings.mhand.presentation.feature.shared.screen.confirmCode.viewmodel.ConfirmCodeViewModel
import com.evothings.mhand.presentation.feature.auth.viewmodel.securecode.SecureCodeViewModel
import com.evothings.mhand.presentation.feature.auth.ui.AuthScreen
import com.evothings.mhand.presentation.feature.card.viewmodel.CardViewModel
import com.evothings.mhand.presentation.feature.card.ui.CardScreen
import com.evothings.mhand.presentation.feature.home.viewmodel.HomeViewModel
import com.evothings.mhand.presentation.feature.home.viewmodel.story.StoriesViewModel
import com.evothings.mhand.presentation.feature.home.ui.HomeScreen
import com.evothings.mhand.presentation.feature.home.ui.story.StoriesScreen
import com.evothings.mhand.presentation.feature.navigation.animation.NavAnimations
import com.evothings.mhand.presentation.feature.onboarding.viewmodel.IntroductionViewModel
import com.evothings.mhand.presentation.feature.address.ui.screen.AddressMapScreen
import com.evothings.mhand.presentation.feature.address.viewmodel.map.AddressMapViewModel
import com.evothings.mhand.presentation.feature.address.viewmodel.registry.AddressNavigationRegistry
import com.evothings.mhand.presentation.feature.auth.ui.securecode.CreateSecureCodeScreen
import com.evothings.mhand.presentation.feature.auth.ui.securecode.EnterSecureCodeScreen
import com.evothings.mhand.presentation.feature.cart.ui.CartScreen
import com.evothings.mhand.presentation.feature.cart.viewmodel.CartViewModel
import com.evothings.mhand.presentation.feature.catalog.ui.CatalogScreen
import com.evothings.mhand.presentation.feature.catalog.viewmodel.CatalogViewModel
import com.evothings.mhand.presentation.feature.checkout.ui.MakingAnOrderScreen
import com.evothings.mhand.presentation.feature.checkout.viewmodel.CheckoutViewModel
import com.evothings.mhand.presentation.feature.favourites.ui.FavouritesScreen
import com.evothings.mhand.presentation.feature.favourites.viewmodel.FavouritesViewModel
import com.evothings.mhand.presentation.feature.news.ui.ArticleScreen
import com.evothings.mhand.presentation.feature.news.viewmodel.NewsViewModel
import com.evothings.mhand.presentation.feature.news.viewmodel.article.ArticleViewModel
import com.evothings.mhand.presentation.feature.news.ui.NewsScreen
import com.evothings.mhand.presentation.feature.onboarding.ui.screen.WelcomeOnboarding
import com.evothings.mhand.presentation.feature.product.ui.ProductInfoScreen
import com.evothings.mhand.presentation.feature.product.viewmodel.ProductViewModel
import com.evothings.mhand.presentation.feature.profile.ui.ProfileScreen
import com.evothings.mhand.presentation.feature.profile.viewmodel.ProfileViewModel
import com.evothings.mhand.presentation.feature.shared.screen.ImageViewScreen
import com.evothings.mhand.presentation.feature.shared.screen.LoadingTechnicalServiceScreen
import com.evothings.mhand.presentation.feature.shared.screen.confirmCode.ui.ConfirmCodeScreen
import com.evothings.mhand.presentation.feature.shared.screen.confirmCode.viewmodel.model.ConfirmCodeUseCase
import com.evothings.mhand.presentation.feature.shops.ui.ShopsScreen
import com.evothings.mhand.presentation.feature.shops.viewmodel.ShopsViewModel
import com.evothings.mhand.presentation.feature.splash.viewmodel.SplashViewModel
import com.evothings.mhand.presentation.feature.splash.ui.SplashScreen

val LocalNavController = compositionLocalOf<NavHostController> {
    error("LocalNavController not provided")
}

fun NavGraphBuilder.buildNavigation(
    navController: NavHostController
) {

    composable<NavGraph.StartScreens.Splash>(
        exitTransition = { fadeOut(tween(500)) }
    ) {
        val splashViewModel = hiltViewModel<SplashViewModel>()

        SplashScreen(
            vm = splashViewModel,
            openMainScreen = { navController.navigate(NavGraph.BottomNav.Home) },
            openOnboardingIntro = { navController.navigate(NavGraph.StartScreens.OnboardingIntro) },
        )
    }

    composable<NavGraph.StartScreens.OnboardingIntro>(
        exitTransition = { fadeOut(tween(500)) }
    ) {
        val introductionViewModel = hiltViewModel<IntroductionViewModel>()

        WelcomeOnboarding(
            vm = introductionViewModel,
            openMainScreen = { navController.navigate(NavGraph.BottomNav.Home) }
        )
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

    composable<NavGraph.Auth.SecureCode.Create>(
        enterTransition = { NavAnimations.slideIn(this) },
        exitTransition = { fadeOut() }
    ) {
        val args = it.toRoute<NavGraph.Auth.SecureCode.Create>()
        val secureCodeViewModel = hiltViewModel<SecureCodeViewModel>()

        CreateSecureCodeScreen(
            vm = secureCodeViewModel,
            phone = args.phone,
            openProfile = { navController.navigate(NavGraph.BottomNav.Profile) },
            onBack = { navController.popBackStack() }
        )

    }

    composable<NavGraph.Auth.SecureCode.Enter>(
        enterTransition = { NavAnimations.slideIn(this) },
        exitTransition = { fadeOut() }
    ) {
        val args = it.toRoute<NavGraph.Auth.SecureCode.Enter>()
        val secureCodeViewModel = hiltViewModel<SecureCodeViewModel>()
        EnterSecureCodeScreen(
            vm = secureCodeViewModel,
            phone = args.phone,
            openProfile = {navController.navigate(NavGraph.BottomNav.Profile)},
            openResetCode = {phone -> navController.navigate(NavGraph.ConfirmationCode(phone, ConfirmCodeUseCase.AUTH.ordinal))  },
            onBack = { navController.popBackStack() },
            )
    }

    composable<NavGraph.AppStatus.TechnicalWorks> {
        LoadingTechnicalServiceScreen()
    }

    composable<NavGraph.ImageView> {
        val args = it.toRoute<NavGraph.ImageView>()

        ImageViewScreen(
            imageLink = args.link,
            onBack = { navController.popBackStack() }
        )
    }

    composable<NavGraph.ConfirmationCode>(
        enterTransition = { NavAnimations.slideIn(this) },
        exitTransition = { fadeOut() }
    ) {
        val args = it.toRoute<NavGraph.ConfirmationCode>()
        val confirmCodeViewModel = hiltViewModel<ConfirmCodeViewModel>()

        ConfirmCodeScreen(
            vm = confirmCodeViewModel,
            phone = args.phone,
            useCase = ConfirmCodeUseCase.entries[args.useCase],
            openCreateSecureCode = {phone ->  navController.navigate(NavGraph.Auth.SecureCode.Create(phone))},
            onBack = {navController.popBackStack()}
        )
    }

    composable<NavGraph.BottomNav.Home>(
        enterTransition = { fadeIn(tween(350)) },
    ) {
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
            },
            openMainScreen = {
                navController.navigate(NavGraph.BottomNav.Home)
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
    ) {
        val args = it.toRoute<NavGraph.BottomNav.Home.Story>()
        val storiesViewModel = hiltViewModel<StoriesViewModel>()

        StoriesScreen(
            vm = storiesViewModel,
            storyIndex = args.storyIndex,
            openMainScreen = { navController.navigate(NavGraph.BottomNav.Home) }
        )
    }

    composable<NavGraph.BottomNav.Card>(
        enterTransition = { fadeIn(tween(500)) },
    ) {
        val cardViewModel = hiltViewModel<CardViewModel>()

        CardScreen(
            vm = cardViewModel,
            openProfile = { navController.navigate(NavGraph.BottomNav.Profile) }
        )
    }

    composable<NavGraph.BottomNav.Catalog> {
        val catalogVm = hiltViewModel<CatalogViewModel>()

        CatalogScreen(
            vm = catalogVm,
        )
    }

    composable<NavGraph.ProductInfo>(
        enterTransition = { NavAnimations.slideIn(this) },
        popExitTransition = { NavAnimations.slideOut(this) }
    ) {
        val arguments = it.toRoute<NavGraph.ProductInfo>()
        val productVm = hiltViewModel<ProductViewModel>()

        ProductInfoScreen(
            id = arguments.id,
            vm = productVm,
            onBack = { navController.popBackStack() }
        )
    }

    composable<NavGraph.AddressMap> {
        val args = it.toRoute<NavGraph.AddressMap>()
        val mapViewModel = hiltViewModel<AddressMapViewModel>()

        AddressMapScreen(
            vm = mapViewModel,
            city = args.city,
            onSelect = { result ->
                val currentValue = AddressNavigationRegistry.get() ?: return@AddressMapScreen
                val withResult = currentValue.copy(
                    postalCode = result.postalCode,
                    city = result.city,
                    street = result.street,
                    house = result.house
                )
                AddressNavigationRegistry.set(withResult)
            },
            onBack = { navController.popBackStack() }
        )
    }

    composable<NavGraph.BottomNav.ShoppingCart> {
        val cartVm = hiltViewModel<CartViewModel>()

        CartScreen(
            vm = cartVm,
            openProductInfoScreen = { id -> navController.navigate(NavGraph.ProductInfo(id)) },
            openCatalogScreen = { navController.navigate(NavGraph.BottomNav.Catalog) },
            openCheckoutScreen = { navController.navigate(NavGraph.BottomNav.ShoppingCart.Checkout(it)) },
            openAuthScreen = { navController.navigate(NavGraph.Auth.AuthenticationScreen) }
        )
    }

    composable<NavGraph.BottomNav.ShoppingCart.Checkout>(
        enterTransition = { NavAnimations.slideIn(this) },
        exitTransition = { NavAnimations.slideOut(this) }
    ) {
        val args = it.toRoute<NavGraph.BottomNav.ShoppingCart.Checkout>()
        val checkoutVm = hiltViewModel<CheckoutViewModel>()

        CompositionLocalProvider(LocalNavController provides navController) {
            MakingAnOrderScreen(
                vm = checkoutVm,
                orderId = args.orderId,
                onBack = { navController.popBackStack() },
                openProductInfoScreen = { id -> navController.navigate(NavGraph.ProductInfo(id)) },
                openAddressMap = { city -> navController.navigate(NavGraph.AddressMap(city)) },
                openProfile = { navController.navigate(NavGraph.BottomNav.Profile) },
                openCheckoutScreen = { orderId -> navController.navigate(NavGraph.BottomNav.ShoppingCart.Checkout(orderId))}
            )
        }
    }

    composable<NavGraph.BottomNav.Profile>(
        enterTransition = { fadeIn(tween(200)) },
    ) {
        val profileVm = hiltViewModel<ProfileViewModel>()

        CompositionLocalProvider(LocalNavController provides navController) {
            ProfileScreen(
                vm = profileVm,
                openAuth = { navController.navigate(NavGraph.Auth.AuthenticationScreen) },
                openProductInfoScreen = { id -> navController.navigate(NavGraph.ProductInfo(id)) },
                openCatalog = { navController.navigate(NavGraph.BottomNav.Catalog) },
                openAddressMap = { city -> navController.navigate(NavGraph.AddressMap(city)) },
                openCheckoutScreen = { orderId ->
                    navController.navigate(NavGraph.BottomNav.ShoppingCart.Checkout(orderId))
                },
                openConfirmPhoneScreen = { phone ->
                    navController.navigate(
                        NavGraph.ConfirmationCode(phone, ConfirmCodeUseCase.PROFILE.ordinal)
                    )
                },
            )
        }
    }

    composable<NavGraph.Other.AboutService> {
        AboutServiceScreen(
            onBack = { navController.popBackStack() }
        )
    }

    composable<NavGraph.Other.Shops>(
        enterTransition = { fadeIn(tween(200)) },
    ) {
        val shopsViewModel = hiltViewModel<ShopsViewModel>()

        ShopsScreen(
            vm = shopsViewModel
        )
    }

    composable<NavGraph.Other.Favourites> {
        val favouritesVm = hiltViewModel<FavouritesViewModel>()

        FavouritesScreen(
            vm = favouritesVm,
            openCatalog = { navController.navigate(NavGraph.BottomNav.Catalog)},
            openProductInfoScreen = {id -> navController.navigate(NavGraph.ProductInfo(id))}
        )
    }

    composable<NavGraph.Other.News> {
        val newsVm = hiltViewModel<NewsViewModel>()

        NewsScreen(
            vm = newsVm,
            openMainScreen = { navController.navigate(NavGraph.BottomNav.Home) },
            openArticle = { id -> navController.navigate(NavGraph.Other.NewsArticle(id)) }
        )
    }

    composable<NavGraph.Other.NewsArticle> {
        val articleId = it.toRoute<NavGraph.Other.NewsArticle>().articleId
        val articleVm = hiltViewModel<ArticleViewModel>()

        ArticleScreen(
            vm = articleVm,
            id = articleId,
            openAnotherArticle = { id -> navController.navigate(NavGraph.Other.NewsArticle(id)) },
            onBack = { navController.popBackStack() }
        )
    }

}