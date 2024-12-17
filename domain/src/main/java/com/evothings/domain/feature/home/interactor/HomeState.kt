package com.evothings.domain.feature.home.interactor

import com.evothings.domain.feature.card.model.Card
import com.evothings.domain.feature.home.model.Brand
import com.evothings.domain.feature.home.model.Story
import com.evothings.domain.feature.profile.model.Profile
import com.evothings.domain.feature.product.model.Product

data class HomeState(
    val stories: List<Story>,
    val newProducts: List<Product>,
    val brands: List<Brand>,
    val profile: Profile?,
    val card: Card?
)
