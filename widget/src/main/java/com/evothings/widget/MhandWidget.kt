package com.evothings.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalSize
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.layout.Box
import androidx.glance.layout.wrapContentSize
import androidx.glance.state.GlanceStateDefinition
import com.evothings.widget.layout.BigSquare
import com.evothings.widget.layout.FatHorizontalRectangle
import com.evothings.widget.layout.SmallSquare
import com.evothings.widget.layout.ThinHorizontalRectangle
import com.evothings.widget.layout.ThinLongHorizontalRectangle
import com.evothings.widget.layout.VerticalRectangle
import com.evothings.widget.viewmodel.MhandWidgetDataLoader
import com.evothings.widget.viewmodel.WidgetState
import com.evothings.widget.viewmodel.datastore.WidgetKeyDataStore
import com.evothings.widget.viewmodel.uiState.MhandWidgetUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class MhandWidget(
    private val dataLoader: MhandWidgetDataLoader
) : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<Int>
        get() = object : GlanceStateDefinition<Int> {

            override suspend fun getDataStore(context: Context, fileKey: String): DataStore<Int> =
                withContext(Dispatchers.IO) {
                    val file = File("${context.cacheDir}/$fileKey")
                    file.createNewFile()
                    WidgetKeyDataStore()
                }

            override fun getLocation(context: Context, fileKey: String): File =
                File("${context.cacheDir}/$fileKey")

        }

    companion object {
        val SMALL_SQUARE = DpSize(30.dp, 30.dp)
        val BIG_SQUARE = DpSize(90.dp, 180.dp)
        val VERTICAL_RECTANGLE = DpSize(120.dp, 180.dp)
        val THIN_LONG_HORIZONTAL_RECTANGLE = DpSize(300.dp, 60.dp)
        val THIN_HORIZONTAL_RECTANGLE = DpSize(240.dp, 60.dp)
        val FAT_HORIZONTAL_RECTANGLE = DpSize(180.dp, 60.dp)
    }

    private val sizes: Set<DpSize> =
        setOf(
            SMALL_SQUARE, BIG_SQUARE,
            VERTICAL_RECTANGLE,
            THIN_LONG_HORIZONTAL_RECTANGLE, THIN_HORIZONTAL_RECTANGLE,
            FAT_HORIZONTAL_RECTANGLE
        )

    override val sizeMode: SizeMode = SizeMode.Responsive(sizes)

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val uiState by dataLoader.uiState.collectAsState()
            val counter = remember { mutableIntStateOf(0) }

            LaunchedEffect(key1 = currentState(), key2 = counter.intValue) {
                dataLoader.updateWidgetUiState()
            }

            uiState?.let { uiData ->
                Widget(
                    uiState = uiData,
                    context = context,
                    onReload = { counter.intValue++ }
                )
            }

        }
    }

    @Composable
    private fun Widget(
        uiState: MhandWidgetUiState,
        context: Context,
        onReload: () -> Unit
    ) {
        val size = LocalSize.current

        val qrBitmap = remember { mutableStateOf<Bitmap?>(null) }

        LaunchedEffect(key1 = Unit, key2 = uiState.qrLink) {
            uiState.qrLink?.let { link ->
                val bitmap = dataLoader.getOrDownloadBitmap(link, context.cacheDir)
                qrBitmap.value = bitmap
            }
        }

        GlanceTheme {
            WidgetContent(
                state = uiState.state,
                size = size,
                cardBalance = uiState.cardBalance,
                qrBitmap = qrBitmap.value,
                onReload = onReload,
                onClickWidget = { openAppScreen(context, uiState.state) }
            )
        }
    }

    private fun openAppScreen(context: Context, state: WidgetState) {
        val deeplink =
            "mhand://" + if (state is WidgetState.NotAuthorized) "auth" else "card"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(deeplink))
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    @Composable
    private fun WidgetContent(
        state: WidgetState,
        size: DpSize,
        cardBalance: Int,
        qrBitmap: Bitmap?,
        onReload: () -> Unit,
        onClickWidget: () -> Unit,
    ) {
        Box(
            modifier = GlanceModifier
                .wrapContentSize()
                .clickable(onClickWidget)
        ) {
            when (size) {
                SMALL_SQUARE -> {
                    SmallSquare(
                        state = state,
                        cardBalance = cardBalance,
                        qrBitmap = qrBitmap
                    )
                }

                BIG_SQUARE -> {
                    BigSquare(
                        state = state,
                        cardBalance = cardBalance,
                        qrBitmap = qrBitmap,
                        onReload = onReload
                    )
                }

                VERTICAL_RECTANGLE -> {
                    VerticalRectangle(
                        state = state,
                        cardBalance = cardBalance,
                        qrBitmap = qrBitmap,
                        onReload = onReload
                    )
                }

                THIN_LONG_HORIZONTAL_RECTANGLE -> {
                    ThinLongHorizontalRectangle(
                        state = state,
                        cardBalance = cardBalance,
                        qrBitmap = qrBitmap,
                        onReload = onReload
                    )
                }

                THIN_HORIZONTAL_RECTANGLE -> {
                    ThinHorizontalRectangle(
                        state = state,
                        cardBalance = cardBalance,
                        qrBitmap = qrBitmap,
                        onReload = onReload
                    )
                }

                FAT_HORIZONTAL_RECTANGLE -> {
                    FatHorizontalRectangle(
                        state = state,
                        cardBalance = cardBalance,
                        qrBitmap = qrBitmap,
                        onReload = onReload
                    )
                }

                else -> {}
            }
        }
    }

}