package com.evothings.mhand.presentation.feature.home.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evothings.mhand.presentation.theme.MegahandTheme
import com.evothings.mhand.presentation.theme.paddings
import com.evothings.mhand.presentation.theme.spacers
import com.evothings.mhand.presentation.theme.values.MegahandShapes


@Composable
fun PreloadScreen(
    modifier: Modifier = Modifier
) {
    val background =
        Modifier.background(
            color = colorScheme.secondary.copy(.05f),
            shape = MegahandShapes.large
        )
    
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(
                horizontal = 12.dp,
                vertical = 12.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
        ) {
            repeat(4){
                Box(
                    modifier = background
                        .size(96.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(26.dp))
        Box(
            modifier = background
                .fillMaxWidth()
                .height(136.dp)
        )
        Spacer(modifier = Modifier.height(30.dp))
        LazyVerticalGrid(
            modifier = Modifier.height(850.dp),
            userScrollEnabled = false,
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
        ) {
            item(
                span = {GridItemSpan(maxLineSpan)}
            ) {
                Box(
                    modifier = background
                        .width(102.dp)
                        .height(26.dp)
                )
            }
            items(4){
                ProductPreloadItem(
                    modifier = background
                )
            }
        }
        Box(
            modifier = background
                .fillMaxWidth()
                .height(100.dp)
                .padding(MaterialTheme.paddings.extraLarge)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.extraLarge))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.medium)
        ) {
            repeat(4) {
                Box(
                    modifier = background
                        .width(100.dp)
                        .height(60.dp)
                )
            }
        }
    }

}

@Composable
fun ProductPreloadItem(
    modifier: Modifier = Modifier
) {

      Column(
          modifier = Modifier
              .width(180.dp),
          verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.extraMedium)
      ){
          Box(
              modifier = modifier
                  .height(180.dp)
                  .fillMaxWidth()
          )
          Column(
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = MaterialTheme.paddings.large),
              verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacers.normal)
          ) {
              Row(
                  verticalAlignment = Alignment.CenterVertically
              ) {
                  Box(
                      modifier = modifier
                          .height(20.dp)
                          .width(70.dp)
                  )
                  Spacer(modifier = Modifier.width(MaterialTheme.spacers.medium))
                  Box(
                      modifier = modifier
                          .height(18.dp)
                          .width(55.dp)
                  )
              }
              Row(
                  verticalAlignment = Alignment.CenterVertically
              ) {
                  Box(
                      modifier = modifier
                          .height(15.dp)
                          .width(39.dp)
                  )
                  Spacer(modifier = modifier.width(MaterialTheme.spacers.tiny))
                  Box(
                      modifier = modifier
                          .height(15.dp)
                          .width(30.dp)
                  )
              }
              Box(
                  modifier = modifier
                      .fillMaxWidth()
                      .height(40.dp)
              )
              Box(
                  modifier = modifier
                      .fillMaxWidth()
                      .height(15.dp)
              )
          }
          Row(
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = MaterialTheme.paddings.large),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
          ) {
              Box(
                  modifier = modifier
                      .width(111.dp)
                      .height(44.dp)
              )
              Box(
                  modifier = modifier
                      .size(42.dp)
              )
          }
      }

}

@Preview
@Composable
private fun ProductPreloadItemPreview() {
    MegahandTheme {
        Surface {
            ProductPreloadItem(
                modifier = Modifier
                    .background(
                        color = colorScheme.secondary.copy(.05f),
                        shape = MegahandShapes.large
                    )
            )
        }
    }
}

@Preview
@Composable
private fun PreloadScreenPreview() {
    MegahandTheme {
        Surface {
            PreloadScreen()
        }
    }
}