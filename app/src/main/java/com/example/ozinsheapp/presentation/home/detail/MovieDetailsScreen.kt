package com.example.ozinsheapp.presentation.home.detail

import android.util.Log
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.ozinsheapp.R
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.domain.entity.home.Screenshot
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.presentation.home.HomeViewModel
import com.example.ozinsheapp.ui.theme.Grey300
import com.example.ozinsheapp.ui.theme.Grey400
import com.example.ozinsheapp.ui.theme.Grey600
import com.example.ozinsheapp.ui.theme.Grey900
import com.example.ozinsheapp.ui.theme.PrimaryRed300
import com.example.ozinsheapp.ui.theme.PrimaryRed500
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.CircularProgressBox

@Composable
fun MovieDetailsScreen(
    viewModel: HomeViewModel,
    id: String?,
    navigateSeasonInfo: (Int) -> Unit
) {
    val moviesByIdState by viewModel.movieById.collectAsStateWithLifecycle()
    val getScreenShotState by viewModel.screenshot.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {

        if (id != null) {
            viewModel.getMoviesById(id.toInt())
        }
        if (id != null) {
            viewModel.getScreenShot(id.toInt())
        }

    }
    Log.d("MovieDetailsScreen", "id movie: ${id.toString()}")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (moviesByIdState) {
            is Resource.Loading -> {
                CircularProgressBox()
            }

            is Resource.Failure -> {
                Log.d("MovieDetailsScreen", "error")
            }

            is Resource.Success -> {
                val item = (moviesByIdState as Resource.Success).data
                item?.let {
                    if (id != null) {
                        SuccessState(
                            item = it,
                            listScreenShots = getScreenShotState,
                            id = id.toInt(),
                            navigateSeasonInfo = {
                                navigateSeasonInfo(id.toInt())
                            },
                            viewModel = viewModel
                        )
                    }
                }
            }

            else -> Unit
        }
    }
}

@Composable
fun SuccessState(
    item: Movie,
    id: Int,
    navigateSeasonInfo: (Int) -> Unit,
    listScreenShots: List<Screenshot>,
    viewModel: HomeViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ImageBlock(
            item = item,
            id = id,
            navigateSeasonInfo = navigateSeasonInfo,
            viewModel = viewModel
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    translationY = -85f
                }
                .background(Color.White, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                MovieInfoBlock(item = item)
                SeriesBlock(item = item, id = id, navigateSeasonInfo = { navigateSeasonInfo(id) })
                ScreenshotsBlock(listScreenShots)
            }
        }
    }
}

@Composable
fun ScreenshotsBlock(listScreenShots: List<Screenshot>) {
    Text(
        modifier = Modifier.padding(top = 32.dp),
        text = stringResource(id = R.string.screenshots),
        fontSize = 16.sp,
        color = Grey900,
        fontFamily = Constant.font700
    )
    LazyRow(
        modifier = Modifier.padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = listScreenShots) { item ->
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = item.link,
                    contentScale = ContentScale.Crop,
                    contentDescription = "screenshots item",
                    loading = {
                        CircularProgressBox(
                            indicatorColor = PrimaryRed500
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SeriesBlock(
    item: Movie,
    id: Int,
    navigateSeasonInfo: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.episodes),
            fontSize = 16.sp,
            color = Grey900,
            fontFamily = Constant.font700
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.clickable { navigateSeasonInfo(id) },
            text = "${item.seasonCount} ${stringResource(id = R.string.season)}, ${item.seriesCount} ${
                stringResource(
                    id = R.string.series
                )
            }",
            fontSize = 12.sp,
            color = Grey400,
            fontFamily = Constant.font500
        )
        Icon(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(16.dp),
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "",
            tint = PrimaryRed300
        )
    }
}

@Composable
fun MovieInfoBlock(
    item: Movie
) {

    var expanded by remember { mutableStateOf(false) }
    val collapsedLines = 5
    val lines = if (expanded) Int.MAX_VALUE else collapsedLines

    Text(
        text = item.name,
        fontSize = 24.sp,
        fontFamily = Constant.font700,
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 4.dp),
            text = item.year.toString(),
            fontSize = 12.sp,
            color = Grey400,
            fontFamily = Constant.font500
        )
        Image(
            modifier = Modifier
                .size(4.dp),
            painter = painterResource(id = R.drawable.ic_circle),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Grey400)
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = item.categories[0].name,
            fontSize = 12.sp,
            color = Grey400,
            fontFamily = Constant.font500
        )
    }
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 24.dp),
        thickness = 1.dp,
        color = Grey300
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = item.description,
            fontSize = 14.sp,
            fontFamily = Constant.font400,
            color = Grey400,
            maxLines = lines
        )
    }
    Text(
        modifier = Modifier
            .padding(top = 16.dp)
            .clickable { expanded = !expanded },
        text = if (expanded) stringResource(id = R.string.hide) else stringResource(id = R.string.show_all_text),
        fontSize = 14.sp,
        fontFamily = Constant.font500,
        color = PrimaryRed300
    )
    BlockWithNameOfCast(
        text = stringResource(id = R.string.rejisser),
        nameOfText = item.director,
        paddingTop = 24.dp
    )
    BlockWithNameOfCast(
        text = stringResource(id = R.string.producer),
        nameOfText = item.producer,
        paddingTop = 8.dp
    )
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 24.dp),
        thickness = 1.dp,
        color = Grey300
    )
}

@Composable
fun BlockWithNameOfCast(
    text: String,
    nameOfText: String,
    paddingTop: Dp = 0.dp,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingTop)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = Constant.font400,
            color = Grey600
        )
        Text(
            modifier = Modifier
                .padding(start = 18.dp),
            text = nameOfText,
            fontSize = 14.sp,
            fontFamily = Constant.font500,
            color = Grey400
        )
    }
}

@Composable
fun ImageBlock(
    item: Movie,
    id: Int,
    navigateSeasonInfo: (Int) -> Unit,
    viewModel: HomeViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    ) {
        Image(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.TopStart),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "arrow back",
        )
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = item.poster.link,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            loading = {
                CircularProgressBox(
                    indicatorColor = PrimaryRed500
                )
            }
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconWithTextBlock(
                icon = R.drawable.ic_saved,
                text = stringResource(id = R.string.add_to_saved),
                isChecked = item.favorite,
                onClick = { isChecked ->
                    if (isChecked) {
                        viewModel.addToFavourite(item.id)
                        Log.d("ImageBlock", "addFavourite")
                    } else {
                        viewModel.deleteFromFavourite(item.id)
                        Log.d("ImageBlock", "delete")
                    }
                    Log.d("ImageBlock", isChecked.toString())
                }
            )
            Image(
                modifier = Modifier
                    .size(128.dp)
                    .clickable { navigateSeasonInfo(id) },
                painter = painterResource(id = R.drawable.ic_play_video),
                contentDescription = ""
            )
            IconWithTextBlock(
                icon = R.drawable.ic_share,
                text = stringResource(id = R.string.share),
            )
        }
    }
}

@Composable
fun IconWithTextBlock(
    icon: Int,
    text: String,
    isChecked: Boolean = false,
    onClick: (Boolean) -> Unit = {}
) {
    val checkState = remember { mutableStateOf(isChecked) }

    Column(
        verticalArrangement = Arrangement.Center
    ) {
        IconToggleButton(
            checked = checkState.value,
            onCheckedChange = { newCheckedState ->
                checkState.value = newCheckedState
                onClick(newCheckedState)
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = if (checkState.value) {
                    PrimaryRed300
                } else {
                    Color.White
                }
            )
        }
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = text,
            fontSize = 12.sp,
            fontFamily = Constant.font500,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}