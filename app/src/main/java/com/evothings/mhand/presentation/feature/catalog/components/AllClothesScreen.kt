package com.evothings.mhand.presentation.feature.catalog.components

import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.home.ui.Painter
import com.evothings.mhand.presentation.feature.home.ui.components.PreloadItem
import com.evothings.mhand.presentation.feature.navigation.bottomBar.ui.BottomBarNavigation
import com.evothings.mhand.presentation.feature.shared.header.Header
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Composable
fun AllClothesScreen(){
    Scaffold(
        topBar = {
            Header(
                nameCategory = stringResource(R.string.all_clothes),
                balanceVisible = true,
                notificationVisible = true,
                logoVisible = false,
                chevronLeftVisible = true,
                locationVisible = false,
            )
        },
    ) {
        Box(modifier = Modifier.padding(it)){
            Content()
        }
    }
}

@Composable
private fun Content() {
    val textList = listOf(
        stringResource(R.string.t_shirts),
        stringResource(R.string.shirts),
        stringResource(R.string.pants),
        stringResource(R.string.trousers),
    )
    val number = 123

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = MaterialTheme.paddings.extraLarge),
        horizontalAlignment = Alignment.Start,
    ) {

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.small)
        ) {
            items(textList) { item ->
                Button(
                    text = item
                )
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.filters),
                color = colorScheme.secondary.copy(0.4f),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                modifier = Modifier
                    .padding(MaterialTheme.paddings.medium)
            )
            Text(
                text = "$number товара(ов)",
                color = colorScheme.secondary.copy(0.4f),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(listOf(Font(R.font.golos_400))),
                modifier = Modifier
                    .padding(
                        horizontal = MaterialTheme.paddings.extraLarge,
                        vertical = MaterialTheme.paddings.medium
                    )
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
        Products()
    }
}

@Composable
fun Products() {

    val product = listOf(
        Painter("https://s3-alpha-sig.figma.com/img/bbc9/b4f4/3bcb0ac3ea5a25f8bff886fa37da5c5d?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=AQyMhZk8IhlCmT-0PnG5aoBYVuy~QU7XpvBX-zmheXo2yABCgT18nTSVlhQl3CkzxGcYl76a2mNMvFZI4-r6lHfd65DnBkRiCRkEACHRekl7AlIEHN6u2NftRCb53RWgs1sEYVQPw72e4mjR5PYQGMFroWxnhQ2cqYuERt7DlbEAh73hw2GWau5fx5m~6eDPs2h7MR5z3x07un84o7AIJ~Jwo6p-EwD9OmrGLCj4su1zIi3oZNzOKwJX0bbfZx571gxUejCZLRkw0vxL~AMa7EAL2ywi-UpROw0zbEM8xWrE~MzQhriBATrIAU1tEbjx9iP2tWq2V6FzS1zy9UTRqw__"),
        Painter("https://s3-alpha-sig.figma.com/img/df49/6794/1fb70577a140768d7de1141121f7e2d8?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=lLRFCZT-R9FBFqIjaxZQLFGgfvZ21AoHqtZ0uu6N2q2BDPK5JEvaC~xWk~NzdLWx6miP~GfxlbnNKvLPEU2yFLWGQuJQUTzc1h79X83hBVQ-2~z3f34owe4Ka~q8FnNr2qDi8cPpjqTH40DrvDT-YTIDci~icESQaXz434m~uWMihTT1H26JHyl8fyaJhGj1bP-UfcLeNa1qsSG7fzHKD121MV1Esce7YBZCiyGd8~JsRfwjy9-mH~eiX-Xg3FW5sEhP~KLjltDFC8klEdbGtn1zDxAmifhrJT6YfN1-AutpDY5bLjuLmZD60cG79cvIKdCvLds--CYhU9r~zKatng__"),
        Painter("https://s3-alpha-sig.figma.com/img/05c6/2dfc/c09c9213bdfb064bfc272aeecc6448a3?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=VJLLThkEga3Jy3n~zPrdsJsypf7mUTz~dIzH7zYHPz7y~3OD~lY8cpsL4JJ5OLFo-w8TmP5QIiHb1XmEEMKFBq1VF5ZPrGnubOZexulCnJMsXv2OCmlZm6z-vt4GDIHPjGo8C2~-74Vo~sIWtGv7Tb8kIFgu67MCBA4F3VGL6pjrajGLxKeVQaAoQJ~ErwgTBxLKw0bGs3Veri4Vqx3cwyMZsRjoOfcivEGwhBspOK0WRJf9UCqvSGnanc9LX8jNLplqsS8IaNIPeY7iFL5W2FsNJrL6Od7c6D1QmIMjl5z2soVrtt0~zKa~ubOWB1l9wwnbGoNL15eQIQkWxUvEig__"),
        Painter("https://s3-alpha-sig.figma.com/img/d953/57ed/9052a8b6d203d5563212b12d8f3b23f7?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=PtCKKGjjhEuq8pqdUaUx1fY995HzhwA-3saEMiuNnrDadv0K-LLrWH~w7nRa9CGzQQtDscbPN~XFrMpFOTMzJUGMVN3kVtdQteC3Igbpn79FM11IXuBd26lhIUMPxyR6mgv7dOTMKtiwgIXp9yIF-aO770KCfOHPK40ppGF6Wp7UKjwJe4LYgDupMU1oiXmPtc~Xo5HS3KW8kTgrKHT0pexywey84-tgnI41PP0PSbwmDpWNjil-KcSNbjrz8v-9AfFjQ8SHnhFDsoP8KU~GSYMuWBVmcMTd6xW-nd5A5fCoy2pIRx~KjgUxI1mQwu~UBDYttbOBiOxKE~trbtnAGQ__"),
    )

    val gridHeight = remember {
        val verticalPadding = 18 * 2
        val spacing = 9 * 2
        val productCardHeight = 400

        (verticalPadding + spacing + productCardHeight * 2).dp
    }


    LazyVerticalGrid(
        modifier = Modifier
            .height(gridHeight),
        columns = GridCells.Fixed(2),
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraLarge),
    ) {
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

@Composable
fun Button(
    text: String
){
    Box(
        modifier = Modifier
            .border(width = 1.dp, color = colorScheme.primary, shape = MegahandShapes.medium)
    ){
        Text(
            text = text,
            color = colorScheme.secondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500,
            fontFamily = FontFamily(listOf(Font(R.font.golos_500))),
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.paddings.giant,
                    vertical = MaterialTheme.paddings.extraLarge
                )
        )
    }
}

@Preview
@Composable
fun PreviewAllclothesScreen(){
    MegahandTheme {
        AllClothesScreen()
    }
}