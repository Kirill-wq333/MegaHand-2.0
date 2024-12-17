package com.evothings.mhand.presentation.feature.shared.product.components

//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScope
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import com.evothings.mhand.R
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.PagerState
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.MaterialTheme.colorScheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.pointer.PointerEventType
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import coil.compose.AsyncImage
//import com.evothings.mhand.presentation.theme.MegahandTheme
//import com.evothings.mhand.presentation.theme.spacers
//import kotlinx.coroutines.delay
//
//@Composable
//fun PhotosSlider(
//    modifier: Modifier,
//    images: List<String>
//) {
//
//    val pagerState = rememberPagerState { images.size }
//
//    val indicatorVisibilityTimer = 1000L
//    val isIndicatorVisible = remember { mutableStateOf(false) }
//
//    LaunchedEffect(isIndicatorVisible.value, pagerState.currentPage) {
//        if (isIndicatorVisible.value) {
//            delay(indicatorVisibilityTimer)
//            isIndicatorVisible.value = false
//        }
//    }
//
//    Box(
//        modifier = modifier,
//        contentAlignment = Alignment.Center
//    ) {
//        if (images.size in 0..1) {
//            ProductPhoto(
//                link = images.firstOrNull().orEmpty()
//            )
//        } else {
//            Pager(
//                state = pagerState,
//                photos = images,
//                onPress = { isIndicatorVisible.value = true }
//            )
//            AnimatedVisibility(
//                modifier = Modifier.align(Alignment.BottomCenter),
//                visible = isIndicatorVisible.value,
//                enter = fadeIn(),
//                exit = fadeOut()
//            ) {
//                PageIndicator(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(12.dp),
//                    pages = images.size,
//                    current = pagerState.currentPage
//                )
//            }
//        }
//    }
//
//}
//
//@Composable
//private fun BoxScope.Pager(
//    state: PagerState,
//    photos: List<String>,
//    onPress: () -> Unit
//) {
//    HorizontalPager(
//        modifier = Modifier
//            .matchParentSize()
//            .pointerInput(PointerEventType.Press) {
//                awaitPointerEventScope {
//                    while (true) {
//                        val event = awaitPointerEvent()
//                        if (event.type == PointerEventType.Press) onPress()
//                    }
//                }
//            },
//        pageSpacing = MaterialTheme.spacers.tiny,
//        state = state
//    ) { page ->
//        ProductPhoto(
//            link = photos[page]
//        )
//    }
//}
//
//@Composable
//fun ProductPhoto(
//    modifier: Modifier = Modifier,
//    link: String
//) {
//    AsyncImage(
//        model = link,
//        contentDescription = null,
//        contentScale = ContentScale.Crop,
//        placeholder = painterResource(id = R.drawable.image_placeholder),
//        error = painterResource(id = R.drawable.no_photo_placeholder),
//        modifier = modifier
//            .fillMaxSize()
//            .clip(MaterialTheme.shapes.large)
//    )
//}
//
//@Composable
//private fun PageIndicator(
//    modifier: Modifier,
//    pages: Int,
//    current: Int
//) {
//    Row(
//        modifier = modifier,
//        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.tiny)
//    ) {
//        repeat(pages) {
//            Box(
//                modifier = Modifier
//                    .weight(0.2f)
//                    .height(1.dp)
//                    .background(
//                        color = colorScheme.secondary.copy(
//                            alpha = if (current == it) 1.0f else 0.2f
//                        )
//                    )
//            )
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun PhotosSliderPreview() {
//    MegahandTheme(true) {
//        Surface {
//            Box(
//                modifier = Modifier
//                    .size(190.dp)
//                    .background(Color.White),
//                contentAlignment = Alignment.Center
//            ) {
//                PhotosSlider(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp),
//                    images = listOf(
//                        "https://avatars.mds.yandex.net/i?id=a6ba61c6cddc1b283e54a203a5b0c9bd33a6a0c9-8549420-images-thumbs&n=13",
//                        "https://avatars.mds.yandex.net/i?id=a6ba61c6cddc1b283e54a203a5b0c9bd33a6a0c9-8549420-images-thumbs&n=13",
//                        "https://i.pinimg.com/originals/26/6f/9a/266f9a441a7177ebc9b141d76994bb08.png",
//                    )
//                )
//            }
//        }
//    }
//}