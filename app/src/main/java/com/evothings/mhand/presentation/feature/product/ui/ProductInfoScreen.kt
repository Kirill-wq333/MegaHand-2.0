package com.evothings.mhand.presentation.feature.product.ui

import androidx.compose.foundation.background
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
import com.evothings.mhand.presentation.feature.shared.header.ui.Header
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun ProductInfoScreen() {
    Scaffold(
        bottomBar = {
            OrderSheet()
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
            .background(color = colorScheme.onSecondary)
        ) {
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

        SliderPhoto(model = "https://s3-alpha-sig.figma.com/img/bbc9/b4f4/3bcb0ac3ea5a25f8bff886fa37da5c5d?Expires=1737331200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=UOaJOsLzPywgg4rKqi8N4dF57FqYGapd9W8zk8Clogx7zMrAPSMIfzGOQa-FxmvLr892~0kf0fBkh7dSc10hE~TqDCbIBfLqfvD9XpPuZVMjh6tV9VrsNZNvxjLSmLMFxvIQhlFtOe4RpmWkwwKt5kxXA3nCd9SLjtdhcrNKIXLRhiMLPse14ODJ-HeaefTfAmg1gvnunQhpRruCr29BCJQLupgrZ91shl-zyvToBaNYAhzE6M49Wq6BeaE2xcHswbYXrb4EIgPdWlg99GoquFpvvXLNTkrGE6Kg0MN5uP5qmmlGnvbdwCn76wTfVQfz4nMCmEyjVBv-fAiHoZnZMw__")
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Text(
            text = heading,
            textAlign = TextAlign.Start,
            style = MegahandTypography.titleLarge,
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
            style = MegahandTypography.bodyLarge,
            color = colorScheme.secondary.copy(0.6f),
            modifier = Modifier
                .padding(horizontal = MaterialTheme.paddings.extraGiant)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
        BrandProduct(
            modifier = Modifier
            .padding(start = MaterialTheme.paddings.extraGiant),
            brand = "https://s3-alpha-sig.figma.com/img/997c/f6cf/1ca7984783573f3aa9869d9638c2aeef?Expires=1737331200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=UO~tqmhTxGO5NmiQbKeNZVHi1tl~YR4X1EevQZDX4jLkrRtBgggr91p5eAnLDVsjer-J3Qt~ASPcQNgel~rGgTc8WfCCWr9DuAAwjJmbDYJdMzpwG-NPkPTHgmzWX9~aYOW47QtvasQre5328BGe74pL2fEJPRNNRsl6-taMakIuWcokkpyBibI9aBydQwXfhCfk9eLMJLvIKFZDJjeaMuXXWjJ6vbqBRxjAWJDci8TUEfGWpaoH-d8rSmkQ~EkVWVjqV8A2vfR~YMmQsGDGMPtouSjIoaLRmE3tOd4wkYi1jnPL4YXm6g4tzKD1gMs1tG2wzSmmiIbaA9Ju~GG8LA__"
        )

    }
}

@Preview
@Composable
fun PreviewProductInfoScreen(){
    MegahandTheme(false){
        ProductInfoScreen()
    }
}
