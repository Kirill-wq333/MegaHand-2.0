package com.evothings.mhand.presentation.feature.home.ui

import CheckUpdateServiceImpl
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.evothings.domain.feature.home.model.Brand
import com.evothings.domain.feature.home.model.Story
import com.evothings.domain.feature.product.model.Product
import com.evothings.domain.util.Mock
import com.evothings.mhand.R
import com.evothings.mhand.presentation.feature.coupon.ui.Coupon
import com.evothings.mhand.presentation.feature.home.ui.components.BrandsItem
import com.evothings.mhand.presentation.feature.home.ui.components.CouponBanner
import com.evothings.mhand.presentation.feature.home.ui.components.PreloadScreen
import com.evothings.mhand.presentation.feature.home.ui.components.QrCode
import com.evothings.mhand.presentation.feature.home.ui.components.StoriesItems
import com.evothings.mhand.presentation.feature.home.ui.components.bottomsheet.UpdateAvailableBottomSheet
import com.evothings.mhand.presentation.feature.home.ui.components.bottomsheet.UserSurveyBottomSheet
import com.evothings.mhand.presentation.feature.home.viewmodel.HomeContract
import com.evothings.mhand.presentation.feature.home.viewmodel.HomeViewModel
import com.evothings.mhand.presentation.feature.navigation.graph.NavGraph
import com.evothings.mhand.presentation.feature.onboarding.ui.screen.HomeOnboarding
import com.evothings.mhand.presentation.feature.shared.bottomsheet.MhandModalBottomSheet
import com.evothings.mhand.presentation.feature.shared.header.ui.HeaderProvider
import com.evothings.mhand.presentation.feature.shared.loyalityCard.BalanceAndCashback
import com.evothings.mhand.presentation.feature.shared.loyalityCard.BigQrcode
import com.evothings.mhand.presentation.feature.shared.product.ProductItem
import com.evothings.mhand.presentation.feature.shared.product.callback.ProductCardCallback
import com.evothings.mhand.presentation.feature.shared.pullToRefresh.PullRefreshLayout
import com.evothings.mhand.presentation.feature.shared.screen.NoInternetConnectionScreen
import com.evothings.mhand.presentation.feature.shared.screen.ServerErrorScreen
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.MegahandTypography
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.utils.sdkutil.Connectivity
import com.evothings.mhand.presentation.utils.sdkutil.openPrivacyPolicyPage
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay

data class HomeUiState(
    val stories: List<Story> = listOf(),
    val brands: List<Brand> = persistentListOf(),
    val newProducts: List<Product> = listOf(),
    val showCard: Boolean = true,
    val cardQRLink: String = "",
    val cashback: Int = 0,
    val showCouponBanner: Boolean = false,
    val couponAmount: Int = 0,
    val isUpdateAvailable: Boolean = false,
    val isUserSurvey: Boolean = false
)

private interface HomeScreenCallback : ProductCardCallback {
    fun onClickStory(storyIndex: Int) {}

    fun navigateToProfile() {}

    fun openPrivacyPolicy() {}

    fun openCouponPhoneConfirmationScreen(phone: String) {}

    fun openAppMarketPage() {}

    fun refresh() {}

    fun openMainScreen(id: Int) {}
}

@Stable
private object EmptyHomeScreenCallback : HomeScreenCallback {
    override fun openProductDetailScreen(id: Int) {}

    override fun addToCart(id: Int) {}

    override fun toggleFavourite(id: Int) {}
}

@Composable
fun HomeScreen(
    vm: HomeViewModel,
    openStoriesScreen: (storyIndex: Int) -> Unit,
    openProductInfoScreen: (Int) -> Unit,
    openProfile: () -> Unit,
    openCouponPhoneConfirmationScreen: (String) -> Unit,
    openMainScreen: (Int) -> Unit
) {
    val context = LocalContext.current

    val uiState by vm.uiState.collectAsState()
    val state by vm.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state is HomeContract.State.Loading) {
            vm.handleEvent(HomeContract.Event.LoadData)
        }
    }

    LaunchedEffect(Unit) {
        val activity = context as ComponentActivity
        val updateService = CheckUpdateServiceImpl(activity)
        vm.handleEvent(HomeContract.Event.CheckIsUpdateAvailable(updateService))
    }

    LaunchedEffect(Unit) {
        while(true) {
            delay(1000)
            if (!Connectivity.hasInternetConnection(context)) {
                vm.handleEvent(HomeContract.Event.EnableNoInternetConnectionState)
            }
        }
    }

    val callback = object : HomeScreenCallback {

        override fun onClickStory(storyIndex: Int) =
            openStoriesScreen(storyIndex)

        override fun navigateToProfile() = openProfile()

        override fun refresh() =
            vm.handleEvent(HomeContract.Event.Refresh)

        override fun openPrivacyPolicy() {
            openPrivacyPolicyPage(context)
        }

        override fun openCouponPhoneConfirmationScreen(phone: String) {
            openCouponPhoneConfirmationScreen(phone)
        }

        override fun addToCart(id: Int) =
            vm.handleEvent(HomeContract.Event.AddProductToCart(id))

        override fun openProductDetailScreen(id: Int) =
            openProductInfoScreen(id)

        override fun openMainScreen(id: Int) {
            openMainScreen(id)
        }

        override fun toggleFavourite(id: Int) =
            vm.handleEvent(HomeContract.Event.ToggleFavouriteOnProduct(id))

        override fun openAppMarketPage() {
            val packageName = context.packageName
            val deeplinkIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            )
            val fallbackIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )

            try {
                context.startActivity(deeplinkIntent)
            } catch(e: ActivityNotFoundException) {
                context.startActivity(fallbackIntent)
            }

        }

    }


    if (state is HomeContract.State.OnboardingActive) {
        HomeOnboarding(
            onFinish = {
                vm.handleEvent(HomeContract.Event.FinishOnboarding)
            }
        )
        return
    }

    HomeContent(
        state = state,
        uiState = uiState,
        callback = callback
    )
}

@Suppress("UNUSED_EXPRESSION")
@Composable
private fun HomeContent(
    state: HomeContract.State,
    uiState: HomeUiState,
    callback: HomeScreenCallback
) {
    var updateAvailableBottomSheetVisible by remember(uiState.isUpdateAvailable) {
        mutableStateOf(uiState.isUpdateAvailable)
    }

    var isUserSurveyBottomSheet by remember(uiState.isUserSurvey) {
        mutableStateOf(uiState.isUserSurvey)
    }

    HeaderProvider(
        screenTitle = "",
        isHomeScreen = true,
        onBack = {}
    ) { headerPadding ->
        PullRefreshLayout(
            modifier = Modifier.padding(headerPadding),
            onRefresh = callback::refresh
        ) {
            when (state) {
                is HomeContract.State.Loading ->
                    PreloadScreen()

                is HomeContract.State.Loaded ->
                    Content(
                        uiState = uiState,
                        callback = callback,
                    )

                is HomeContract.State.NetworkError ->
                    ServerErrorScreen(
                        onRefresh = callback::refresh
                    )
                is HomeContract.State.NoInternetConnection ->
                    NoInternetConnectionScreen(
                        onReload = callback:: refresh
                    )
                else -> {}
            }
        }
    }
    if (updateAvailableBottomSheetVisible) {
        UpdateAvailableBottomSheet(
            onDismiss = { updateAvailableBottomSheetVisible = false },
            onClickUpdate = callback::openAppMarketPage
        )
    }
    if (isUserSurveyBottomSheet){
        MhandModalBottomSheet(
            onDismissRequest = { isUserSurveyBottomSheet = false }
        ) {
            UserSurveyBottomSheet(
                onSubmit = { callback::openMainScreen; isUserSurveyBottomSheet = false }
            )
        }
    }
}

@Composable
private fun Content(
    uiState: HomeUiState,
    callback: HomeScreenCallback = EmptyHomeScreenCallback,
) {
    var showQrCodeView by remember { mutableStateOf(false) }

    var couponBannerVisible by remember(uiState.showCouponBanner) {
        mutableStateOf(uiState.showCouponBanner)
    }

    var couponBottomSheetEnabled by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        StoriesLists(list = uiState.stories, onClick = callback::onClickStory)
        Spacer(modifier = Modifier.height(MaterialTheme.paddings.extraLarge))
        if (uiState.showCard) {
            LoyalityCard(
                cashback = uiState.cashback,
                openProfile = { NavGraph.BottomNav.Profile },
                enableBalance = true,
                cardQRUrl = uiState.cardQRLink,
                showQR = { showQrCodeView = true },
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
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
        NewProduct(products = uiState.newProducts, callback = callback)
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))
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
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        BrandsList(brand = uiState.brands)
    }

    if (uiState.showCard) {
        AnimatedVisibility(
            visible = showQrCodeView,
            enter = fadeIn() + scaleIn(tween(150)),
            exit = fadeOut() + scaleOut(tween(150))
        ) {
            BigQrcode(
                qrCodeLink = uiState.cardQRLink,
                onClose = { showQrCodeView = false }
            )
        }
    }
    if (couponBottomSheetEnabled) {
        Coupon(
            onDismiss = { couponBottomSheetEnabled = false },
            openConfirmationScreen = callback::openCouponPhoneConfirmationScreen
        )
    }
}

@Composable
fun BrandsList(brand: List<Brand>) {
    LazyRow(
        modifier =
        Modifier
            .padding(MaterialTheme.paddings.extraLarge),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium),
    ) {
        items(brand) {
            BrandsItem(brands = it.photoLink)
        }
    }
}

@Composable
fun StoriesLists(
    list: List<Story>,
    onClick: (Int) -> Unit,
) {
    LazyRow(
        modifier =
        Modifier
            .padding(MaterialTheme.paddings.extraLarge),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        itemsIndexed(list) { index, storiesItem ->
            StoriesItems(
                storiesImage = storiesItem.imageLink,
                textStories = storiesItem.title,
                onClickStory = { onClick(index) },
            )
        }
    }
}

@Composable
fun LoyalityCard(
    modifier: Modifier = Modifier,
    cashback: Int,
    cardBalance: Int = -1,
    cardQRUrl: String,
    isOnboarding: Boolean = false,
    showQR: () -> Unit,
    openProfile: () -> Unit,
    enableBalance: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .height(184.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxSize()
            ) {
                BalanceAndCashback(
                    modifier = Modifier.weight(.5f),
                    enableBalance = enableBalance,
                    cashback = cashback,
                    onClickIncrease = openProfile,
                    money = cardBalance
                )
                QrCode(
                    modifier = Modifier.weight(.5f),
                    qrLink = cardQRUrl,
                    isOnboarding = isOnboarding,
                    onClick = showQR
                )
            }
        }
    }
}

@Composable
fun NewProduct(
    modifier: Modifier = Modifier,
    products: List<Product>,
    callback: ProductCardCallback,
) {
    val gridHeight =
        remember {
            val verticalPadding = 18 * 2
            val spacing = 12 * 2
            val productCardHeight = 400

            (verticalPadding + spacing + productCardHeight * 2).dp
        }

    Box(modifier = modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            modifier = Modifier
                .height(gridHeight),
            columns = GridCells.Fixed(2),
            userScrollEnabled = false,
            contentPadding =
            PaddingValues(
                horizontal = MaterialTheme.spacers.medium,
                vertical = MaterialTheme.spacers.large,
            ),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraLarge),
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) },
            ) {
                Text(
                    text = stringResource(R.string.news_screen_title),
                    color = colorScheme.secondary,
                    style = MegahandTypography.titleLarge,
                    modifier =
                    Modifier
                        .padding(start = MaterialTheme.paddings.giant),
                )
            }
            items(products.size.coerceAtMost(4)) { i ->
                val item = remember { products[i] }
                ProductItem(
                    model = item,
                    callback = callback,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewHome() {
    MegahandTheme(true) {
        Surface {
            HomeContent(
                state = HomeContract.State.Loaded,
                uiState =
                HomeUiState(
                    stories = Mock.demoStoriesList,
                    brands = Mock.demoBrand,
                    newProducts = Mock.demoProductsList,
                ),
                callback = object : HomeScreenCallback {
                    override fun onClickStory(storyIndex: Int) {}

                    override fun navigateToProfile() {}

                    override fun openPrivacyPolicy() {}

                    override fun openProductDetailScreen(id: Int) {}

                    override fun addToCart(id: Int) {}

                    override fun toggleFavourite(id: Int) {}

                    override fun openAppMarketPage() {}

                    override fun refresh() {}
                }
            )
        }
    }
}
