package com.evothings.mhand.presentation.feature.news.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.news.model.NewsArticle
import com.evothings.domain.feature.news.model.NewsCategory
import com.evothings.domain.util.Mock
import com.evothings.mhand.presentation.feature.news.ui.components.MainNews
import com.evothings.mhand.presentation.feature.news.ui.components.NewsItem
import com.evothings.mhand.presentation.feature.news.viewmodel.NewsContract
import com.evothings.mhand.presentation.feature.news.viewmodel.NewsViewModel
import com.evothings.mhand.presentation.feature.shared.MLazyRow
import com.evothings.mhand.presentation.feature.shared.button.Chip
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers

data class NewsUiState(
    val primaryArticle: NewsArticle = NewsArticle(),
    val selectedCategoryIndex: Int = 0,
    val categories: List<NewsCategory> = listOf(),
    val news: List<NewsArticle> = listOf(),
)

private interface NewsCallback {
    fun openArticle(id: String) {}
    fun shareArticle(link: String) {}
    fun changeCategory(newCategoryIndex: Int) {}
    fun refresh() {}
    fun navigateToMain() {}
}

@Stable
private object EmptyNewsCallback : NewsCallback

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    vm: NewsViewModel,
    openArticle: (id: String) -> Unit,
    openMainScreen: () -> Unit
) {
    val context = LocalContext.current

    val state by vm.state.collectAsStateWithLifecycle()
    val newsUiState by vm.uiState.collectAsState(initial = NewsUiState())

    LaunchedEffect(state) {
        if (state is NewsContract.State.Loading) {
            vm.handleEvent(NewsContract.Event.LoadData)
        }
    }

    val callback = object : NewsCallback {

        override fun changeCategory(newCategoryIndex: Int) =
            vm.handleEvent(NewsContract.Event.SelectCategory(newCategoryIndex))

        override fun shareArticle(link: String) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "News article link")
                putExtra(Intent.EXTRA_TEXT, link)
            }
            context.startActivity(Intent.createChooser(intent, null))
        }

        override fun refresh() {
            vm.handleEvent(NewsContract.Event.Reload)
        }

        override fun openArticle(id: String) = openArticle(id)

        override fun navigateToMain() = openMainScreen()

    }

        NewsContent(
            uiState = newsUiState,
            state = state,
            callback = callback
        )
}

@Composable
private fun NewsContent(
    modifier: Modifier = Modifier,
    uiState: NewsUiState,
    state: NewsContract.State,
    callback: NewsCallback = EmptyNewsCallback,
) {

    Box() {
        Content(
            articles = uiState.news,
            categories = uiState.categories,
            selectedCategoryIndex = uiState.selectedCategoryIndex,
            article = uiState.primaryArticle,
            callback = callback
        )
    }

}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    articles: List<NewsArticle>,
    categories: List<NewsCategory>,
    selectedCategoryIndex: Int,
    article: NewsArticle,
    callback: NewsCallback
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorScheme.onSecondary)
            .padding(horizontal = MaterialTheme.paddings.extraLarge)
    ) {
        item {
            MainNews(
                title = article.categories,
                mainImage = article.previewImageLink,
                publicationDate = article.publishingDate,
                informationNews = article.title,
                onShare = { callback.shareArticle(article.articleLink) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        }

        item {
            MLazyRow(
                itemsList = categories,
                listHorizontalPadding = MaterialTheme.spacers.extraLarge
            ) { item, index ->
                Chip(
                    text = item.name,
                    enabled = (selectedCategoryIndex == index),
                    onClick = { callback.changeCategory(index) }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        }

        items(
            items = articles,
            key = { it.id }
        ) { news ->
            NewsItem(
                imageLink = news.previewImageLink,
                publicationDate = news.publishingDate,
                information = news.title,
                category = news.categories
            )
        }

    }
}

@Preview(showSystemUi = true, fontScale = 1.65f)
@Preview(showSystemUi = true,)
@Composable
private fun PreviewNewsScreen() {
    MegahandTheme {
        NewsContent(
            state = NewsContract.State.Loaded,
            uiState = NewsUiState(
                primaryArticle = Mock.demoNews,
                news = Mock.demoNewsList,
                categories = Mock.demoNewsCategories
            )
        )
    }
}