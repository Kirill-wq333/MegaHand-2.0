package com.evothings.mhand.presentation.feature.checkout.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.auth.ui.components.PrivacyPolicyText
import com.evothings.mhand.presentation.feature.cart.ui.components.Cashback
import com.evothings.mhand.presentation.feature.cart.ui.components.CheckoutItem
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.text.util.NumberSeparator
import com.evothings.mhand.presentation.feature.shared.text.util.splitHundreds
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun CheckoutMakingAnOrder(
    modifier: Modifier = Modifier,
    productsCount: Int,
    total: Double,
    discount: Double,
    deductionOfPoints: Double,
    deliveryMethodHeading: Double,
    cashbackPoints: Double,
    summary: Double
) {

     Box(
         modifier = modifier
             .fillMaxWidth()
     ){
         Column(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(
                     horizontal = MaterialTheme.paddings.extraGiant,
                     vertical = MaterialTheme.paddings.giant
                     )
         ){
             CheckoutItem(
                 text = stringResource(R.string.products_count, productsCount),
                 value = "${total.splitHundreds(NumberSeparator.SPACE)} ₽",
                 color = colorScheme.secondary
             )

             Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))

             CheckoutItem(
                 text = stringResource(R.string.discount, discount),
                 value = "${total.splitHundreds(NumberSeparator.SPACE)} ₽",
                 color = colorScheme.error
             )

             Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))

             CheckoutItem(
                 text = stringResource(R.string.deduction_of_points, deductionOfPoints),
                 value = "${total.splitHundreds(NumberSeparator.SPACE)} ₽",
                 color = colorScheme.secondary
             )

             Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))

             CheckoutItem(
                 text = stringResource(R.string.delivery_method_heading, deliveryMethodHeading),
                 value = "${total.splitHundreds(NumberSeparator.SPACE)} ₽",
                 color = colorScheme.secondary
             )

             Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))

             if (cashbackPoints > 0) {
                 Cashback(
                     text = stringResource(R.string.cashback),
                     selected = true,
                     money = "${cashbackPoints.splitHundreds(NumberSeparator.SPACE)} ₽"
                 )
             }

             Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))

             Row(
                 modifier = Modifier
                     .fillMaxWidth(),
                 horizontalArrangement = Arrangement.SpaceBetween
             ) {
                 Text(
                     text = stringResource(R.string.total),
                     color = colorScheme.secondary,
                     style = MegahandTypography.headlineMedium
                 )
                 Text(
                     text = "${summary.splitHundreds(NumberSeparator.SPACE)} ₽",
                     color = colorScheme.secondary,
                     style = MegahandTypography.headlineMedium
                 )
             }

             Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))

             Button(
                 onClick = {},
                 text = stringResource(R.string.make_order_button),
                 textColor = colorScheme.onSecondary
             )

             Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))

             PrivacyPolicyText(
                 buttonLabel = stringResource(R.string.make_order_button),
                 onClick = {}
             )

         }
     }

}

