package com.evothings.mhand.presentation.feature.home.ui.stories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.evothings.domain.feature.home.model.Story
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.home.ui.stories.components.Controls
import com.evothings.mhand.presentation.feature.home.ui.stories.components.StoryTimeCounter
import com.evothings.mhand.presentation.feature.home.viewmodel.story.StoriesContract
import com.evothings.mhand.presentation.feature.home.viewmodel.story.StoriesViewModel
import com.evothings.mhand.presentation.feature.shared.button.Button
import com.evothings.mhand.presentation.feature.shared.button.icon.IconButton
import com.evothings.mhand.presentation.feature.shared.loading.LoadingScreen
import com.evothings.mhand.presentation.theme.colorScheme.ColorTokens

@Composable
fun StoriesScreen(
    vm: StoriesViewModel,
    storyIndex: Int,
    openMainScreen: () -> Unit
){
    val context = LocalContext.current

    val storiesList by vm.storiesList.collectAsState()
    val state by vm.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.handleEvent(StoriesContract.Event.LoadStories)
    }


}

@Composable
private fun Content(
    storiesList: List<Story>,
    storyIndex: Int,
    onClickButton: (String) -> Unit,
    onFinish: () -> Unit
){
    var currentStoryIndex by remember { mutableIntStateOf(storyIndex) }
    val item = remember(currentStoryIndex) { storiesList[currentStoryIndex] }

    val watchPaused = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ){

        StoryImage(
            link = item.imageLink
        )
        Scrim()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ){
            StoryTimeCounter(
                storiesCount = storiesList.size,
                currentStoryIndex = currentStoryIndex,
                watchPaused = watchPaused.value,
                onTimerFinish = {
                    if (currentStoryIndex != storiesList.lastIndex) {
                        currentStoryIndex++
                    } else {
                        onFinish()
                    }
                }
            )
        }
        Controls(
            onClickLeft = {
                val new = currentStoryIndex - 1
                currentStoryIndex = new.coerceAtLeast(0)
            },
            onClickRight = {
                val new = currentStoryIndex + 1
                currentStoryIndex = new.coerceAtMost(storiesList.lastIndex)
            },
            onHold = { watchPaused.value = true },
            onRelease = { watchPaused.value = false }
        )
        IconButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_close),
            tint = ColorTokens.Graphite,
            backgroundColor = Color.White,
            borderColor = ColorTokens.Graphite.copy(0.1f),
            onClick = onFinish,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    end = 24.dp,
                    top = 48.dp
                )
        )
        StoryContents(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            title = item.title,
            content = item.description,
            buttonLabel = item.buttonLabel,
            displayButton = item.articleLink.isNotEmpty(),
            onClick = { onClickButton(item.articleLink) }
        )
    }

}


@Composable
private fun StoryImage(link: String) {

    var isLoading by remember { mutableStateOf(true) }

    AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = link,
        contentDescription = null,
        onState = { isLoading = it is AsyncImagePainter.State.Loading },
        contentScale = ContentScale.Crop
    )

    if (isLoading) {
        Surface {
            LoadingScreen()
        }
    }
}

@Composable
private fun Scrim() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent,
                        Color.Black
                    )
                ),
                alpha = 0.6f
            )
    )
}

@Composable
fun StoryContents(
    modifier: Modifier,
    title: String,
    content: String,
    buttonLabel: String,
    displayButton: Boolean,
    onClick: () -> Unit
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = typography.headlineLarge,
            color = Color.White
        )
        Text(
            text = content,
            style = typography.bodyLarge,
            color = Color.White
        )
        if (displayButton) {
            Spacer(
                modifier = Modifier.height(6.dp)
            )
            Button(
                text = buttonLabel,
                textColor = ColorTokens.Graphite,
                backgroundColor = colorScheme.primary,
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}