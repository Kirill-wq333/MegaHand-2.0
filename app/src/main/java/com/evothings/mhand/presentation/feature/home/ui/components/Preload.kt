package com.evothings.mhand.presentation.feature.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Buttons
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Discount
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.Price
import com.evothings.mhand.presentation.feature.home.ui.components.preloadComponents.SizeAndStars
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun PreloadItem(
    contentDescription: String?,
    information: String,
    product: String,
    price: String,
    size: String,
    estimation: String,
    cashback: String,
    discount: String,
    discountPercent: String
){
    Box(
        modifier = Modifier
            .width(180.dp),
        contentAlignment = Alignment.TopStart
    ){
        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = shapes.large)
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = product,
                    contentDescription = contentDescription,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraMedium))
            Price(price = price, cashback = cashback)
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.tiny))
            Discount(discount = discount, discountPercent = discountPercent)
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
            Text(
                text = information,
                color = colorScheme.secondary.copy(0.6f),
                fontSize = 16.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.paddings.extraLarge
                    )
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.normal))
            SizeAndStars(estimation = estimation, textSize = size)
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraMedium))
            Buttons()
        }
    }
}


@Preview
@Composable
fun PreviewPreload(){
    Surface(color = Color.White) {
        PreloadItem(
            cashback = "172",
            contentDescription = null,
            price = "3 592",
            discount = "6 990",
            discountPercent = "49",
            information = "Футболка A Shock, черно-розовая",
            size = "М",
            estimation = "Высокое",
            product = "https://s3-alpha-sig.figma.com/img/bbc9/b4f4/3bcb0ac3ea5a25f8bff886fa37da5c5d?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=AQyMhZk8IhlCmT-0PnG5aoBYVuy~QU7XpvBX-zmheXo2yABCgT18nTSVlhQl3CkzxGcYl76a2mNMvFZI4-r6lHfd65DnBkRiCRkEACHRekl7AlIEHN6u2NftRCb53RWgs1sEYVQPw72e4mjR5PYQGMFroWxnhQ2cqYuERt7DlbEAh73hw2GWau5fx5m~6eDPs2h7MR5z3x07un84o7AIJ~Jwo6p-EwD9OmrGLCj4su1zIi3oZNzOKwJX0bbfZx571gxUejCZLRkw0vxL~AMa7EAL2ywi-UpROw0zbEM8xWrE~MzQhriBATrIAU1tEbjx9iP2tWq2V6FzS1zy9UTRqw__"
        )
    }
}