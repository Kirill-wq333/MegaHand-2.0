package com.evothings.domain.feature.home.interactor

import com.evothings.domain.feature.card.repository.CardRepository
import com.evothings.domain.feature.home.repository.HomeRepository
import com.evothings.domain.feature.product.repository.ProductRepository
import com.evothings.domain.feature.profile.repository.ProfileRepository

class HomeInteractor(
    private val homeRepository: HomeRepository,
    private val profileRepository: ProfileRepository,
    private val productRepository: ProductRepository,
    private val cardRepository: CardRepository
) {

    suspend fun buildHomeState(): Result<HomeState> =
        runCatching {

            val profile = profileRepository.getProfileInfo(false).getOrNull()
            val card = profile?.let {
                    cardRepository.getCardInfo(
                        force = false,
                        offlineMode = false,
                        city = it.city
                    ).getOrNull()
                }

            HomeState(
                stories = getStories().getOrThrow(),
                brands = homeRepository.getBrands().getOrThrow(),
                card = card,
                profile = profile,
                newProducts = getNewProducts().getOrThrow()
            )
        }

    suspend fun getNewProducts() =
        productRepository.getNewProducts()

    suspend fun getStories() =
        homeRepository.getStories()


    suspend fun submitSurvey(index: Int) =
        homeRepository.submitUserSurvey(index)

    suspend fun transliterateCityName(city: String) =
        homeRepository.transliterateCityName(city)

}