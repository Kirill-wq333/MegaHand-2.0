package com.evothings.mhand.presentation.feature.news.ui

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.evothings.domain.feature.news.model.NewsArticle
import com.evothings.mhand.presentation.feature.news.ui.articleComponents.InformationArticle
import com.evothings.mhand.presentation.feature.news.viewmodel.article.ArticleContract
import com.evothings.mhand.presentation.feature.news.viewmodel.article.ArticleViewModel
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

data class ArticleUiState(
    val newsModel: NewsArticle?,
    val similarArticles: List<NewsArticle>
)

private interface ArticleCallback {
    fun openSimilarArticle(id: String) {}
    fun reload() {}
    fun shareArticle(link: String) {}
    fun onBack() {}
}

@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    vm: ArticleViewModel,
    id: String,
    openAnotherArticle: (id: String) -> Unit,
    onBack: () -> Unit
) {

    val context = LocalContext.current

    val state by vm.state.collectAsStateWithLifecycle()
    val news by vm.article.collectAsState()
    val similarArticles by vm.similarArticles.collectAsState()

    LaunchedEffect(state) {
        if (state is ArticleContract.State.Loading) {
            vm.handleEvent(ArticleContract.Event.LoadArticle(id))
        }
    }

    val callback = object : ArticleCallback {

        override fun shareArticle(link: String) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "News article link")
                putExtra(Intent.EXTRA_TEXT, link)
            }
            context.startActivity(Intent.createChooser(intent, null))
        }

        override fun reload() =
            vm.handleEvent(ArticleContract.Event.LoadArticle(id))

        override fun onBack() = onBack()

        override fun openSimilarArticle(id: String) = openAnotherArticle(id)

    }

//    Content(
//        mainImage = news.previewImageLink,
//        title = news.title,
//        publicationDate = news.publishingDate,
//        informationNews = news.content
//    )

}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    mainImage: String,
    title: String,
    publicationDate: String,
    informationNews: String,
) {
    val text = listOf(
        "Вступление",
        "Сдержанный шик",
        "Аристократия"
    )

    LazyColumn {

        item {
            ImageNews(
                mainImage = mainImage,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(214.dp)
                    .padding(horizontal = MaterialTheme.paddings.extraLarge)
            )
        }

        item {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        }

        item {
            InformationArticle(
                title = title,
                publicationDate = publicationDate,
                informationNews = informationNews,
                onClick = { }
            )
        }
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        }

        item{
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.paddings.giant)
            ) {
                items(text) { item ->
                    Button(
                        text = item,
                        selected = true
                    )
                }
            }
        }

    }
}


@Composable
private fun Button(
    modifier: Modifier = Modifier,
    selected: Boolean,
    text: String
) {
    val color =
        if (selected) colorScheme.secondary.copy(.4f) else colorScheme.secondary

    Box(){
        Text(
            text = text,
            color = color,
            style = MegahandTypography.bodyLarge
        )
    }

}


@Composable
fun ImageNews(
    modifier: Modifier = Modifier,
    mainImage: String,
) {
    Box(
        modifier = modifier
    ) {
        AsyncImage(
            model = mainImage,
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .clip(shape = MegahandShapes.extraLarge)
        )
    }
}


@Preview
@Composable
private fun ArticleScreenPreview() {
    MegahandTheme {  }
}