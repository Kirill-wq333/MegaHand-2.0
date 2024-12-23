package com.evothings.mhand.presentation.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.evothings.mhand.R
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.evothings.mhand.presentation.feature.home.ui.components.BrandsItem
import com.evothings.mhand.presentation.feature.home.ui.components.CouponBanner
import com.evothings.mhand.presentation.feature.home.ui.components.PreloadItem
import com.evothings.mhand.presentation.feature.home.ui.components.QrCode
import com.evothings.mhand.presentation.feature.home.ui.components.StoriesItem
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.BottomBarNavigation
import com.evothings.mhand.presentation.feature.shared.header.Header
import com.evothings.mhand.presentation.feature.shared.loyalityCard.BalanceAndCashback
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

@Composable
fun HomeScreen(){
    Content()
}



@Composable
private fun Content() {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {

            Header(
                nameCategory = "",
                money = "7 180",
                chevronLeftVisible = false,
                logoVisible = true,
                balanceVisible = true,
                locationVisible = true,
                notificationVisible = true
            )

        },
    ) {

        Box(
            modifier = Modifier
                .padding(it)
        ) {
            Column(Modifier.fillMaxSize().verticalScroll(scrollState)) {
                StoriesLists()
                Spacer(modifier = Modifier.height(MaterialTheme.paddings.extraLarge))
                LoyalityCard(
                    money = "0",
                    visibleCashback = false,
                    visible = false
                    )
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
                CouponBanner(banner = R.drawable.loyality_onboarding_banner, selected = false)
                NewProduct()
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
                CouponBanner(banner = R.drawable.profile_onboarding_banner, selected = false)
                Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
                BrandsList()
            }
        }

    }
}

@Composable
fun BrandsList(){
    val brands = listOf(
        "https://s3-alpha-sig.figma.com/img/997c/f6cf/1ca7984783573f3aa9869d9638c2aeef?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=Un3~jchfSI8T3u6yQ8fwcCbR22m9t8fg5APYvIWZcOv87SJbCNzGl7OYhaZiFxMGJNzX2PHFlQciNzrWT5ca3dWS~I9L2sz~mS-DcU8domm524bAqOKe4nxU~ZlIGdOTp-9PQsN7qkjRcJK9FnYstCPlEjezA2Fv7OIP-P7wV9AvpavBX-ZDXkyfw24aIKitPBMCPk-rNj8py4sePEcpg190dY2EkXu4TOexkpYbzTDUYTMp~VhEWT3bB7PFQO5Oj-sCSAcV9JtHlcZaar2K8JZJKEWEuz5qlYTI0LGZLh1Itu2QlbZOxVvQHsf~8WJM~opblTzIZGDVHanL8gOvvA__",
        "https://s3-alpha-sig.figma.com/img/d086/4d00/f5b43864ba115b22730e56e9f1c2ddd1?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=Ln3vw8YqGg6R92-7maS-~Ar12FtKfCj0xgfQiLGYd-kIeOOqGzl2wCeHEcaXGmnRUK6JeJIqBWCA7pF-CxRFXsTPDjaIHImbscETHkw3EA78E-bfgtMXakoe3iTWNk-4qGLDyy6kI9T8c0HwRYl149VADUxpKbPjZM65Zkx6WP5iTVfZOZmZ0~IFI-wms703SgqU5sYC8YTfHbAaDWVM7PNM67utP1ckLwCkVbHqWW~a-N9J8vPg97zm-OFU2yvNdGxAXLbTQ5sSpEoQvHmB4DqnyHcNFH2zpVdqnPt0HGQ~~c3HUCm~-c-RjJdSg7gXJiBX5pXHBrAsnCjpPqIvyQ__",
        "https://s3-alpha-sig.figma.com/img/f93f/5333/a438da1ee09e4f1370f4972e654728ed?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=cKO5XYqZeTa1wBSCwET1CAjs~lBjbyueJdbPTB8haWMRxTyYsMmNPuJC06C-DzpZXrS89JW6rYL-v0iuZNL2nVKnosRp~Av04fmCC8p7OyRc9sjmhHCzhWb3BShY76qUl83LzNT9lHki0FM8a-xc1KlrscpsqyHyyfr0ye2vSERHu~juHKUlgjlHNclAj5nROzBe26RH5-l0N1nMdw~zQm511u22MtM-oonv1prXM~-WgmzGMrxfa~fF7gVrgXEo8ok37v353Jm5gctf~yz0ClWIpOf3hgEU44LKkD-o64~zD77UCjjS3~mD2fAG~sbaSF61kViIWUzwJh8ZEgQfBw__",
        "https://s3-alpha-sig.figma.com/img/1a62/a16a/c23a0fc1d87bc79175a0181924ff6b92?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=qT1tqOi1-thYPu4ZxM~qGae6KDYl3bONO7POLbUZ~3xBAuNHpiox0UDeV-fhnes8Tg2OhG~VmS2fztd-6aLM3tgjapAEz1oy9Nphmdz-XDVIBzoirBxZeaKZq0zjOLe~XpcqJrdlIqaMamjQuPzPL4fW8Z1ZijHJ7lRWo0GHU-7wb2zp-oIPUpglC3RNmpKndHQUzi1IjuuHwS8pupIcARql9DwvGLbXJAUfvwFobhIjyCQrY9WNRGgSOZfsAQyuCRcsi3ayQcG-sgkk6M4fWtXB622OxWea5TydYqoVvxqj2jG2M4yrtrPr7G4sem7HaDk6AoO2w9od7C4WTNoOAw__"
    )
    LazyRow(
        modifier = Modifier
            .padding(MaterialTheme.paddings.extraLarge),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
    ) {
        items(brands) { it ->
            BrandsItem(brands = it)
        }
    }
}

@Composable
fun StoriesLists(){
    val image = listOf(
        StoriesItem("О магазинах Волгограда", R.drawable.onboarding_story_1),
        StoriesItem("Новые поступления", R.drawable.onboarding_story_3),
        StoriesItem("Одежда Cristian Dior", R.drawable.onboarding_story_2),
        StoriesItem("Купоны и скидки в приложении", R.drawable.onboarding_story_4)
    )
    LazyRow(
        modifier = Modifier
            .padding(MaterialTheme.paddings.extraLarge),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(image) { storiesItem ->
            StoriesItem(
                storiesImage = storiesItem.image,
                textStories = storiesItem.name
            )
        }
    }
}

@Composable
fun LoyalityCard(
    money: String,
    visibleCashback: Boolean,
    visible: Boolean
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.paddings.extraLarge),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        BalanceAndCashback(
            money = money,
            button = visibleCashback,
            visible = visible
        )
        QrCode()
    }
}

data class Painter(
    val image: String
)

@Composable
fun NewProduct() {

    val product = listOf(
        Painter("https://s3-alpha-sig.figma.com/img/bbc9/b4f4/3bcb0ac3ea5a25f8bff886fa37da5c5d?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=AQyMhZk8IhlCmT-0PnG5aoBYVuy~QU7XpvBX-zmheXo2yABCgT18nTSVlhQl3CkzxGcYl76a2mNMvFZI4-r6lHfd65DnBkRiCRkEACHRekl7AlIEHN6u2NftRCb53RWgs1sEYVQPw72e4mjR5PYQGMFroWxnhQ2cqYuERt7DlbEAh73hw2GWau5fx5m~6eDPs2h7MR5z3x07un84o7AIJ~Jwo6p-EwD9OmrGLCj4su1zIi3oZNzOKwJX0bbfZx571gxUejCZLRkw0vxL~AMa7EAL2ywi-UpROw0zbEM8xWrE~MzQhriBATrIAU1tEbjx9iP2tWq2V6FzS1zy9UTRqw__"),
        Painter("https://s3-alpha-sig.figma.com/img/df49/6794/1fb70577a140768d7de1141121f7e2d8?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=lLRFCZT-R9FBFqIjaxZQLFGgfvZ21AoHqtZ0uu6N2q2BDPK5JEvaC~xWk~NzdLWx6miP~GfxlbnNKvLPEU2yFLWGQuJQUTzc1h79X83hBVQ-2~z3f34owe4Ka~q8FnNr2qDi8cPpjqTH40DrvDT-YTIDci~icESQaXz434m~uWMihTT1H26JHyl8fyaJhGj1bP-UfcLeNa1qsSG7fzHKD121MV1Esce7YBZCiyGd8~JsRfwjy9-mH~eiX-Xg3FW5sEhP~KLjltDFC8klEdbGtn1zDxAmifhrJT6YfN1-AutpDY5bLjuLmZD60cG79cvIKdCvLds--CYhU9r~zKatng__"),
        Painter("https://s3-alpha-sig.figma.com/img/05c6/2dfc/c09c9213bdfb064bfc272aeecc6448a3?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=VJLLThkEga3Jy3n~zPrdsJsypf7mUTz~dIzH7zYHPz7y~3OD~lY8cpsL4JJ5OLFo-w8TmP5QIiHb1XmEEMKFBq1VF5ZPrGnubOZexulCnJMsXv2OCmlZm6z-vt4GDIHPjGo8C2~-74Vo~sIWtGv7Tb8kIFgu67MCBA4F3VGL6pjrajGLxKeVQaAoQJ~ErwgTBxLKw0bGs3Veri4Vqx3cwyMZsRjoOfcivEGwhBspOK0WRJf9UCqvSGnanc9LX8jNLplqsS8IaNIPeY7iFL5W2FsNJrL6Od7c6D1QmIMjl5z2soVrtt0~zKa~ubOWB1l9wwnbGoNL15eQIQkWxUvEig__"),
        Painter("https://s3-alpha-sig.figma.com/img/d953/57ed/9052a8b6d203d5563212b12d8f3b23f7?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=PtCKKGjjhEuq8pqdUaUx1fY995HzhwA-3saEMiuNnrDadv0K-LLrWH~w7nRa9CGzQQtDscbPN~XFrMpFOTMzJUGMVN3kVtdQteC3Igbpn79FM11IXuBd26lhIUMPxyR6mgv7dOTMKtiwgIXp9yIF-aO770KCfOHPK40ppGF6Wp7UKjwJe4LYgDupMU1oiXmPtc~Xo5HS3KW8kTgrKHT0pexywey84-tgnI41PP0PSbwmDpWNjil-KcSNbjrz8v-9AfFjQ8SHnhFDsoP8KU~GSYMuWBVmcMTd6xW-nd5A5fCoy2pIRx~KjgUxI1mQwu~UBDYttbOBiOxKE~trbtnAGQ__"),
    )

    val gridHeight = remember {
        val verticalPadding = 18 * 2
        val spacing = 12 * 2
        val productCardHeight = 400

        (verticalPadding + spacing + productCardHeight * 2).dp
    }

    Box() {
        LazyVerticalGrid(
            modifier = Modifier
                .height(gridHeight),
            columns = GridCells.Fixed(2),
            userScrollEnabled = false,
            contentPadding = PaddingValues(
                horizontal = MaterialTheme.spacers.medium,
                vertical = MaterialTheme.spacers.large
            ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraLarge),
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Text(
                    text = stringResource(R.string.news_screen_title),
                    color = colorScheme.secondary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
                    modifier = Modifier
                        .padding(start = MaterialTheme.paddings.giant)
                )

            }
            items(product) { item ->
                PreloadItem(
                    contentDescription = null,
                    cashback = "172",
                    price = "3592",
                    discount = "6990",
                    discountPercent = "49",
                    information = "Футболка A Shock, черно-розовая",
                    size = "M",
                    estimation = "Высокое",
                    product = item.image
                )
            }
        }
    }

}


@Preview
@Composable
fun PreviewContent() {
    MegahandTheme {
        HomeScreen()
    }
}