package com.example.ozinsheapp.presentation.home.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.ozinsheapp.R
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.domain.entity.home.Season
import com.example.ozinsheapp.domain.entity.userhistory.Video
import com.example.ozinsheapp.presentation.home.HomeViewModel
import com.example.ozinsheapp.ui.theme.Grey50
import com.example.ozinsheapp.ui.theme.Grey700
import com.example.ozinsheapp.ui.theme.Grey900
import com.example.ozinsheapp.ui.theme.PrimaryRed400
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.CircularProgressBox

@Composable
fun SeasonInfoScreen(
    viewModel: HomeViewModel,
    id: String?,
    navigateToVideoPlayer: (String) -> Unit,
    navController: NavHostController
) {
    val seasonInfo by viewModel.seasonInfo.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (id != null) {
            viewModel.getSeasonInfo(id.toInt())
        }
    }

    when (seasonInfo) {
        is Resource.Loading -> {
            CircularProgressBox()
        }

        is Resource.Success -> {
            val item = (seasonInfo as Resource.Success).data
            if (item != null) {
                SuccessState(
                    listVideo = item.seasons.flatMap { season -> season.videos },
                    listSeason = item.seasons,
                    navigateToVideoPlayer = navigateToVideoPlayer,
                    navController = navController
                )
            }
        }

        is Resource.Failure -> {
            Log.d("SeasonInfoScreen", "error")
        }

        else -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessState(
    listSeason: List<Season>,
    listVideo: List<Video>,
    navigateToVideoPlayer: (String) -> Unit,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TopBarBlock(
                        screenName = stringResource(id = R.string.episodes),
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            LazyRow(
                modifier = Modifier.padding(top = 50.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = listSeason) { item ->
                    NumberSeasonItem(item = item)
                }
            }
            LazyColumn(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = listVideo) { item ->
                    SeasonVideoItem(
                        item = item,
                        onClick = {
                            navigateToVideoPlayer(item.link)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun NumberSeasonItem(
    item: Season
) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(34.dp)
            .background(PrimaryRed400, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${item.number} ${stringResource(id = R.string.episodes)}",
            fontSize = 12.sp,
            fontFamily = Constant.font500,
            color = Grey50
        )
    }
}

@Composable
fun SeasonVideoItem(
    item: Video,
    onClick: () -> Unit
) {
    Column() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable { onClick() },
            colors = CardDefaults.cardColors(
                containerColor = Grey700
            )
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = "http://img.youtube.com/vi/${item.link}/maxresdefault.jpg",
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "${item.number} ${stringResource(id = R.string.series)}",
            fontSize = 14.sp,
            fontFamily = Constant.font700,
            color = MaterialTheme.colorScheme.onBackground
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.surfaceDim
        )
    }
}


@Composable
fun TopBarBlock(
    screenName: String,
    iconId: Int = 0,
    onBackClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    onBackClick()
                },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = screenName,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            fontFamily = Constant.inter700
        )
        if (iconId != 0) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClick() },
                painter = painterResource(id = iconId),
                contentDescription = "",
                tint = Color.Red
            )
        } else {
            Box {}
        }
    }
}

