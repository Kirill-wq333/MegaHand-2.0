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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.product.ui.components.SliderPhoto
import com.evothings.mhand.presentation.feature.shared.header.Header
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
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Content()
        }
    }
}



@Composable
private fun Content(
    heading: String
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        SliderPhoto(model = "https://s3-alpha-sig.figma.com/img/997c/f6cf/1ca7984783573f3aa9869d9638c2aeef?Expires=1734912000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=Un3~jchfSI8T3u6yQ8fwcCbR22m9t8fg5APYvIWZcOv87SJbCNzGl7OYhaZiFxMGJNzX2PHFlQciNzrWT5ca3dWS~I9L2sz~mS-DcU8domm524bAqOKe4nxU~ZlIGdOTp-9PQsN7qkjRcJK9FnYstCPlEjezA2Fv7OIP-P7wV9AvpavBX-ZDXkyfw24aIKitPBMCPk-rNj8py4sePEcpg190dY2EkXu4TOexkpYbzTDUYTMp~VhEWT3bB7PFQO5Oj-sCSAcV9JtHlcZaar2K8JZJKEWEuz5qlYTI0LGZLh1Itu2QlbZOxVvQHsf~8WJM~opblTzIZGDVHanL8gOvvA__")
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

    }
}


