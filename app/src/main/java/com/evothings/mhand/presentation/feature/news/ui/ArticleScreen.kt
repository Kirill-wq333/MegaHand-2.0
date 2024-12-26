package com.evothings.mhand.presentation.feature.news.ui

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.news.model.NewsArticle
import com.evothings.mhand.presentation.feature.news.viewmodel.article.ArticleContract
import com.evothings.mhand.presentation.feature.news.viewmodel.article.ArticleViewModel

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
}