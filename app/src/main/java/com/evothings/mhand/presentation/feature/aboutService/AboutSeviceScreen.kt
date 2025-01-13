package com.evothings.mhand.presentation.feature.aboutService

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

@Preview
@Composable
private fun AboutServicePreview() {
    MegahandTheme(false) {
        AboutServiceScreen()
    }
}

@Composable
fun AboutServiceScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.onSecondary)
    ) {
        Content()
    }
}


@Composable
private fun Content(
    modifier: Modifier = Modifier
) {
    
   Column(
       modifier = modifier
           .fillMaxSize()
           .padding(horizontal = MaterialTheme.paddings.extraLarge)
           .verticalScroll(rememberScrollState()),
       verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraLarge)
   ) {
       ServiceVideo()
       Heading()
       Evidence()
       Footer()
   }
    
}

@Composable
private fun ServiceVideo(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(214.dp),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(R.drawable.about_service_image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .padding(top =  MaterialTheme.paddings.extraLarge)
                .clip(MegahandShapes.extraLarge)
            )
        Box(
            modifier = Modifier
                .background(color = colorScheme.onSecondary, shape = MegahandShapes.medium),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                tint = colorScheme.secondary,
                icon = ImageVector.vectorResource(R.drawable.ic_play),
                onClick = {},
                )
        }
    }

}

@Composable
private fun Heading(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.about_service_headline),
        color = colorScheme.secondary,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier
            .padding(horizontal = MaterialTheme.paddings.extraLarge)
    )
}

@Composable
fun Evidence(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val textParts: List<AnnotatedString> = remember {
        val paragraphs = listOf(
            context.getString(R.string.preference_paragraph_1),
            context.getString(R.string.preference_paragraph_2),
            context.getString(R.string.preference_paragraph_3),
            context.getString(R.string.preference_paragraph_4),
            context.getString(R.string.preference_paragraph_5),
            context.getString(R.string.preference_paragraph_6),
            context.getString(R.string.preference_paragraph_7),
        )
        val bullet = '\u2022'
        val style = ParagraphStyle(
            textIndent = TextIndent(restLine = 12.sp),
            lineHeight = 20.sp
        )

        paragraphs.map { paragraph ->
            buildAnnotatedString {
                withStyle(style) {
                    append(bullet)
                    append("\t\t")
                    append(paragraph)
                }
            }
        }
    }
    repeat(textParts.size) { i ->
        Text(
            text = textParts[i],
            color = colorScheme.secondary,
            style = MegahandTypography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.paddings.extraLarge)
        )
    }
}

@Composable
fun Footer(
    modifier: Modifier = Modifier
) {
        Image(
            painter = painterResource(R.drawable.about_service_footer_image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.paddings.large)
                .clip(MegahandShapes.extraLarge)
        )
}