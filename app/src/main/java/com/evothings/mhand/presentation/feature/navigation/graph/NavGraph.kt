package com.evothings.mhand.presentation.feature.navigation.graph

import androidx.annotation.DrawableRes
import com.evothings.mhand.R
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

object NavGraph {

    object StartScreens {

        @Serializable
        data object Splash : Screen

        @Serializable
        data object OnboardingIntro : Screen

    }

    object Auth {

        @Serializable
        data object AuthenticationScreen : Screen

        object SecureCode {

            @Serializable
            data class Create(val phone: String) : Screen

            @Serializable
            data class Enter(val phone: String) : Screen

        }

    }

    object AppStatus {

        @Serializable
        data object TechnicalWorks : Screen
    }

    @Serializable
    data class ProductInfo(val id: Int) : Screen

    @Serializable
    data class AddressMap(val city: String) : Screen

    @Serializable
    data class ConfirmationCode(
        val phone: String,
        val useCase: Int
    ) : Screen

    @Serializable
    data class ImageView(val link: String) : Screen


    object Other {

        @Serializable
        data object News : Screen

        @Serializable
        data object Favourites : Screen

        @Serializable
        data object Shops : Screen

        @Serializable
        data class NewsArticle(val articleId: String) : Screen

        @Serializable
        data object AboutService : Screen

        @Serializable
        data object Vacancies : Screen

        @Serializable
        data object Help : Screen

    }

    @Serializable
    sealed class BottomNav(
        @DrawableRes
        val iconResId: Int
    ) : Screen {

        @Serializable
        data object Home : BottomNav(
            iconResId = R.drawable.ic_home
        ) {

            @Serializable
            data class Story(val storyIndex: Int) : Screen

        }

        @Serializable
        data object Catalog : BottomNav(
            iconResId = R.drawable.ic_catalog
        )

        @Serializable
        data object Card : BottomNav(
            iconResId = R.drawable.ic_card
        )

        @Serializable
        data object ShoppingCart : BottomNav(
            iconResId = R.drawable.ic_shop
        ) {

            @Serializable
            data class Checkout(val orderId: String) : Screen

        }

        @Serializable
        data object Profile : BottomNav(
            iconResId = R.drawable.ic_profile
        )

        @Serializable
        data object Other : BottomNav(
            iconResId = R.drawable.ic_other
        )

        companion object {
            val bottomNavigationEntries = setOf(Home, Catalog, Card, ShoppingCart, Profile, Other)
        }

    }

    val otherDestinations = setOf(
        Other.Favourites,
        Other.Shops,
        Other.News,
        Other.AboutService
    )

}

fun KClass<*>.shouldShowNavBar(): Boolean {
    val mergedList: MutableList<Screen> =
        NavGraph.BottomNav.bottomNavigationEntries.toMutableList()
    mergedList.addAll(NavGraph.otherDestinations)

    return mergedList.any { it::class == this }
}