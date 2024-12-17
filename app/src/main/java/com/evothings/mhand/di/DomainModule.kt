package com.evothings.mhand.di

import com.evothings.domain.feature.address.interactor.AddressInteractor
import com.evothings.domain.feature.address.repository.AddressRepository
import com.evothings.domain.feature.settings.interactor.AppSettingsInteractor
import com.evothings.domain.feature.auth.interactor.AuthInteractor
import com.evothings.domain.feature.captcha.CaptchaInteractor
import com.evothings.domain.feature.card.interactor.CardInteractor
import com.evothings.domain.feature.header.HeaderInteractor
import com.evothings.domain.feature.home.interactor.HomeInteractor
import com.evothings.domain.feature.notification.interactor.NotificationInteractor
import com.evothings.domain.feature.news.interactor.NewsInteractor
import com.evothings.domain.feature.profile.interactor.ProfileInteractor
import com.evothings.domain.feature.shops.interactor.ShopsInteractor
import com.evothings.domain.feature.splash.SplashInteractor
import com.evothings.domain.feature.settings.repository.AppSettingsRepository
import com.evothings.domain.feature.auth.repository.AuthRepository
import com.evothings.domain.feature.captcha.repository.CaptchaRepository
import com.evothings.domain.feature.card.repository.CardRepository
import com.evothings.domain.feature.cart.interactor.CartInteractor
import com.evothings.domain.feature.cart.repository.CartRepository
import com.evothings.domain.feature.catalog.interactor.CatalogInteractor
import com.evothings.domain.feature.catalog.repository.CatalogRepository
import com.evothings.domain.feature.checkout.interactor.CheckoutInteractor
import com.evothings.domain.feature.checkout.repository.CheckoutRepository
import com.evothings.domain.feature.coupon.interactor.CouponInteractor
import com.evothings.domain.feature.coupon.repository.CouponRepository
import com.evothings.domain.feature.favourites.interactor.FavouritesInteractor
import com.evothings.domain.feature.favourites.repository.FavouritesRepository
import com.evothings.domain.feature.header.repository.HeaderRepository
import com.evothings.domain.feature.home.repository.HomeRepository
import com.evothings.domain.feature.notification.repository.NotificationRepository
import com.evothings.domain.feature.news.repository.NewsRepository
import com.evothings.domain.feature.onboarding.interactor.OnboardingInteractor
import com.evothings.domain.feature.product.interactor.ProductInteractor
import com.evothings.domain.feature.product.repository.ProductRepository
import com.evothings.domain.feature.profile.repository.ProfileRepository
import com.evothings.domain.feature.shops.repository.ShopsRepository
import com.evothings.domain.feature.splash.SplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class DomainModule {

    @Provides
    fun provideMainInteractor(
        homeRepository: HomeRepository,
        profileRepository: ProfileRepository,
        cardRepository: CardRepository,
        productRepository: ProductRepository
    ): HomeInteractor =
        HomeInteractor(homeRepository, profileRepository, productRepository, cardRepository)

    @Provides
    fun provideOtherInteractor(newsRepository: NewsRepository): NewsInteractor =
        NewsInteractor(newsRepository)

    @Provides
    fun provideShopsInteractor(shopsRepository: ShopsRepository): ShopsInteractor =
        ShopsInteractor(shopsRepository)

    @Provides
    fun provideAuthInteractor(authRepository: AuthRepository): AuthInteractor =
        AuthInteractor(authRepository)

    @Provides
    fun provideHeaderInteractor(headerRepository: HeaderRepository): HeaderInteractor =
        HeaderInteractor(headerRepository)

    @Provides
    fun provideProfileInteractor(
        profileRepository: ProfileRepository,
        addressRepository: AddressRepository
    ): ProfileInteractor =
        ProfileInteractor(profileRepository, addressRepository)

    @Provides
    fun provideCardInteractor(cardRepository: CardRepository): CardInteractor =
        CardInteractor(cardRepository)

    @Provides
    fun provideNotificationInteractor(notificationRepository: NotificationRepository): NotificationInteractor =
        NotificationInteractor(notificationRepository)

    @Provides
    fun provideSplashInteractor(splashRepository: SplashRepository): SplashInteractor =
        SplashInteractor(splashRepository)

    @Provides
    fun provideCaptchaInteractor(captchaRepository: CaptchaRepository): CaptchaInteractor =
        CaptchaInteractor(captchaRepository)

    @Provides
    fun provideAppSettingsInteractor(appSettingsRepository: AppSettingsRepository): AppSettingsInteractor =
        AppSettingsInteractor(appSettingsRepository)

    @Provides
    fun provideCouponInteractor(couponRepository: CouponRepository): CouponInteractor =
        CouponInteractor(couponRepository)

    @Provides
    fun provideOnboardingInteractor(appSettingsRepository: AppSettingsRepository): OnboardingInteractor =
        OnboardingInteractor(appSettingsRepository)

    @Provides
    fun provideFavouritesInteractor(favouritesRepository: FavouritesRepository): FavouritesInteractor =
        FavouritesInteractor(favouritesRepository)

    @Provides
    fun provideProductInteractor(
        productRepository: ProductRepository,
        favouritesRepository: FavouritesRepository,
        cartRepository: CartRepository
    ): ProductInteractor {
        return ProductInteractor(productRepository, cartRepository, favouritesRepository)
    }

    @Provides
    fun provideAddressInteractor(addressRepository: AddressRepository): AddressInteractor =
        AddressInteractor(addressRepository)

    @Provides
    fun provideCartInteractor(cartRepository: CartRepository): CartInteractor =
        CartInteractor(cartRepository)

    @Provides
    fun provideCheckoutInteractor(checkoutRepository: CheckoutRepository): CheckoutInteractor {
        return CheckoutInteractor(checkoutRepository)
    }

    @Provides
    fun provideCatalogInteractor(
        catalogRepository: CatalogRepository,
        productRepository: ProductRepository
    ): CatalogInteractor {
        return CatalogInteractor(catalogRepository, productRepository)
    }

}