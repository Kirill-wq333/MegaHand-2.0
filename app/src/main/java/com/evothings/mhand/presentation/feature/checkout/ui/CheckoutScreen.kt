package com.evothings.mhand.presentation.feature.checkout.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.evothings.domain.feature.checkout.model.CheckoutResult
import com.evothings.mhand.presentation.feature.checkout.ui.components.CheckoutMakingAnOrder
import com.evothings.mhand.presentation.feature.checkout.ui.components.PersonalData
import com.evothings.mhand.presentation.feature.checkout.ui.components.ReceiptMethodAndAddress
import com.evothings.mhand.presentation.feature.checkout.ui.components.SystemLoyality
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Information
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

private interface CheckoutCallback {
    fun onBack()
    fun refreshScreen()
    fun openPrivacyPolicy()
    fun onCheckout(result: CheckoutResult)
    fun onClickProduct(id: Int)
    fun onChangePickupCity(city: String)
    fun openAddressMap(city: String)
    fun updateAddressesList()
}

@Preview
@Composable
private fun MakingAndOrderScreenPreview() {
    MegahandTheme(true) {
        MakingAnOrderScreen()
    }
}

@Composable
fun MakingAnOrderScreen(
    modifier: Modifier = Modifier,

    ) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorScheme.onSecondary)
    ) {
        Content()
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,

    ) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        item {
            PersonalData()
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))
        }

        item {
            ReceiptMethodAndAddress()
        }

        item {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))

        }

        item {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorScheme.secondary)
            )
        }

        item {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        }

        item {
            SystemLoyality()
        }

        item {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        }

        item {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorScheme.secondary)
            )
        }

        item {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        }

        item{
            Column(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.paddings.extraGiant),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraLarge)
            ) {
                CProduct()
                CProduct()
                CProduct()
            }
        }
        item {
            CheckoutMakingAnOrder(
                productsCount = 0,
                total = 0.0,
                cashbackPoints = 0.0,
                discount = 0.0,
                deliveryMethodHeading = 0.0,
                deductionOfPoints = 0.0,
                summary = 0.0
            )
        }

    }

}

@Composable
fun CProduct(
    modifier: Modifier = Modifier
) {

   Row {
       Box(
           modifier = Modifier
               .size(100.dp)
       ){
           AsyncImage(
               model = "",
               contentDescription = null,
               modifier = Modifier
                   .matchParentSize()
                   .clip(MaterialTheme.shapes.medium)
           )
       }
       Spacer(modifier = Modifier.width(MaterialTheme.spacers.extraMedium))
       Information(
           price = 1111.0,
           cashback = 2222.0,
           discount = 3333.0,
           keepOldPrice = true,
           discountPercent = 4.0,
           isDiscountPercent = true,
           title = "тестик",
           condition = "тестик",
           size = "тестик",
           showSizeAndCondition = true
       )
   }

}
