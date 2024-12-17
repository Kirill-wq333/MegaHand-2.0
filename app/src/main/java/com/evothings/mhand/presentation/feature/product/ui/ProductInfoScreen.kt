package com.evothings.mhand.presentation.feature.product.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.product.ui.components.BrandProduct
import com.evothings.mhand.presentation.feature.product.ui.components.OrderSheet
import com.evothings.mhand.presentation.feature.product.ui.components.ParametersProduct
import com.evothings.mhand.presentation.feature.product.ui.components.SliderPhoto
import com.evothings.mhand.presentation.feature.shared.header.Header
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun ProductInfoScreen(){
    Scaffold(
        topBar = {
            Header(
                nameCategory = "Товар",
                chevronLeftVisible = true,
                notificationVisible = true,
                balanceVisible = true,
                logoVisible = false,
                locationVisible = false,
                money = "7 180"
            )
        },
        bottomBar = {
            OrderSheet()
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Content(
                heading = "Кроссовки QUESTAR 2 M, черные, 43",
                sizeProduct = "43 (11 US)",
                qualityProduct = "Высокое",
                seriesProduct = "Air Force 1",
                styleProduct = "CW2288-111",
                releaseDateProduct = "01.01.2018",
                colorProduct = "Тройной белый",
                information = "Это не просто обувь, а настоящая икона стиля, которая с момента своего появления в 1982 году завоевала сердца миллионов. Разработанные дизайнером Брюсом Килгором, они были первыми баскетбольными кроссовками, в которых использовалась революционная технология амортизации Nike Air, обеспечивающая непревзойденный комфорт и поддержку."
            )
        }
    }
}



@Composable
private fun Content(
    heading: String,
    sizeProduct: String,
    qualityProduct: String,
    seriesProduct: String,
    styleProduct: String,
    releaseDateProduct: String,
    colorProduct: String,
    information: String
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {

        SliderPhoto(model = "https://s3-alpha-sig.figma.com/img/bbc9/b4f4/3bcb0ac3ea5a25f8bff886fa37da5c5d?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=AQyMhZk8IhlCmT-0PnG5aoBYVuy~QU7XpvBX-zmheXo2yABCgT18nTSVlhQl3CkzxGcYl76a2mNMvFZI4-r6lHfd65DnBkRiCRkEACHRekl7AlIEHN6u2NftRCb53RWgs1sEYVQPw72e4mjR5PYQGMFroWxnhQ2cqYuERt7DlbEAh73hw2GWau5fx5m~6eDPs2h7MR5z3x07un84o7AIJ~Jwo6p-EwD9OmrGLCj4su1zIi3oZNzOKwJX0bbfZx571gxUejCZLRkw0vxL~AMa7EAL2ywi-UpROw0zbEM8xWrE~MzQhriBATrIAU1tEbjx9iP2tWq2V6FzS1zy9UTRqw__")
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Text(
            text = heading,
            textAlign = TextAlign.Start,
            color = colorScheme.secondary,
            fontSize = 24.sp,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        ParametersProduct(
            sizeProduct = sizeProduct,
            qualityProduct = qualityProduct,
            seriesProduct = seriesProduct,
            styleProduct = styleProduct,
            releaseDateProduct = releaseDateProduct,
            colorProduct = colorProduct
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        Text(
            text = information,
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
            color = colorScheme.secondary.copy(0.6f),
            modifier = Modifier
                .padding(horizontal = MaterialTheme.paddings.extraGiant)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        BrandProduct(
            brand = "https://s3-alpha-sig.figma.com/img/997c/f6cf/1ca7984783573f3aa9869d9638c2aeef?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=Un3~jchfSI8T3u6yQ8fwcCbR22m9t8fg5APYvIWZcOv87SJbCNzGl7OYhaZiFxMGJNzX2PHFlQciNzrWT5ca3dWS~I9L2sz~mS-DcU8domm524bAqOKe4nxU~ZlIGdOTp-9PQsN7qkjRcJK9FnYstCPlEjezA2Fv7OIP-P7wV9AvpavBX-ZDXkyfw24aIKitPBMCPk-rNj8py4sePEcpg190dY2EkXu4TOexkpYbzTDUYTMp~VhEWT3bB7PFQO5Oj-sCSAcV9JtHlcZaar2K8JZJKEWEuz5qlYTI0LGZLh1Itu2QlbZOxVvQHsf~8WJM~opblTzIZGDVHanL8gOvvA__"
        )

    }
}

@Preview
@Composable
fun PreviewProductInfoScreen(){
    MegahandTheme{
        ProductInfoScreen()
    }
}
