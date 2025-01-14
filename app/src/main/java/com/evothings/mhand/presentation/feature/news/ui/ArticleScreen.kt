package com.evothings.mhand.presentation.feature.news.ui

import android.content.Intent
import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.evothings.domain.feature.news.model.NewsArticle
import com.evothings.domain.util.Mock
import com.evothings.mhand.presentation.feature.home.ui.components.CouponBanner
import com.evothings.mhand.presentation.feature.news.ui.articleComponents.InformationArticle
import com.evothings.mhand.presentation.feature.news.ui.components.NewsItem
import com.evothings.mhand.presentation.feature.news.viewmodel.article.ArticleContract
import com.evothings.mhand.presentation.feature.news.viewmodel.article.ArticleViewModel
import com.evothings.mhand.presentation.feature.shared.text.util.toAnnotateString
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes

data class ArticleUiState(
    val newsModel: NewsArticle?,
    val similarArticles: List<NewsArticle>,
    val couponAmount: Int = 0,
    val showCouponBanner: Boolean = false,
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

}

@Composable
private fun ArticleContent(
    uiState: ArticleUiState,
    state: ArticleContract.State,
    callback: ArticleCallback,
){
    Content(
        uiState = uiState,
        callback = callback
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    uiState: ArticleUiState,
    callback: ArticleCallback
) {
    var couponBannerVisible by remember(uiState.showCouponBanner) {
        mutableStateOf(uiState.showCouponBanner)
    }
    var couponBottomSheetEnabled by remember { mutableStateOf(false) }

    if (uiState.newsModel == null) return


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.onSecondary)
    ) {
        item {
            ImageNews(
                mainImage = uiState.newsModel.previewImageLink,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(214.dp)
                    .padding(horizontal = MaterialTheme.paddings.extraLarge)
            )
        }
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            with(uiState.newsModel) {
                InformationArticle(
                    title = title,
                    publicationDate = publishingDate,
                    informationNews = content,
                    onClick = { callback.shareArticle(articleLink) }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        }
        item {
            ArticleTitle(
                content = uiState.newsModel.content,
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.paddings.extraLarge)
            )
        }
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
            if (couponBannerVisible) {
                CouponBanner(
                    banner = uiState.couponAmount,
                    onClose = { couponBannerVisible = false },
                    onClick = {
                        couponBottomSheetEnabled = true
                        couponBannerVisible = false
                    },
                )
            }
        }
        items(
            items = uiState.similarArticles,
            key = { it.id }
        ) {
            NewsItem(
                imageLink = uiState.newsModel.previewImageLink,
                publicationDate = uiState.newsModel.publishingDate,
                information = uiState.newsModel.title,
                category = uiState.newsModel.categories
            )
        }

    }
}


@Composable
fun ArticleTitle(
    modifier: Modifier = Modifier,
    content: String
) {
    val formattedText = remember {
        Html.fromHtml(
            content.replace("\\r\\n", "<br>"),
            Html.FROM_HTML_MODE_COMPACT
        )
    }
    val annotatedString = remember {
        formattedText.toAnnotateString(
            linkColor = Color.Blue,
            baseSpanStyle = null
        )
    }

    Text(
        modifier = modifier,
        text = annotatedString,
        style = typography.bodyLarge,
    )
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
    MegahandTheme(false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorScheme.onSecondary)
        ) {
            Content(
                uiState = ArticleUiState(
                    newsModel = Mock.demoNews,
                    similarArticles = Mock.demoNewsList,
                    couponAmount = 1,
                    showCouponBanner = false
                ),
                callback = object : ArticleCallback {
                    override fun openSimilarArticle(id: String) {}
                    override fun reload() {}
                    override fun shareArticle(link: String) {}
                    override fun onBack() {}
                }
            )
        }
    }
}