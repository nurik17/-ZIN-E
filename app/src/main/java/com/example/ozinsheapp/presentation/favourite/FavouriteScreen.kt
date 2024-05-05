package com.example.ozinsheapp.presentation.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.ozinsheapp.R
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.presentation.home.detail.TopBarBlock
import com.example.ozinsheapp.ui.theme.Grey300
import com.example.ozinsheapp.ui.theme.Grey400
import com.example.ozinsheapp.ui.theme.Grey900
import com.example.ozinsheapp.ui.theme.PrimaryRed400
import com.example.ozinsheapp.ui.theme.PrimaryRed50
import com.example.ozinsheapp.ui.theme.PrimaryRed500
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.CircularProgressBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(
    viewModel: FavouriteViewModel,
    navigateToSeasonInfo:()->Unit,
    navController: NavHostController
) {
    val favouriteMovieList by viewModel.favouriteMovie.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.getFavouriteMovie()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TopBarBlock(
                        screenName = stringResource(id = R.string.list),
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(all = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            LazyColumn() {
                items(favouriteMovieList) { item ->
                    CardMovieItem(
                        item = item,
                        onClick = {/*
                            navigateToSeasonInfo()*/
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CardMovieItem(
    item: Movie,
    onClick: () -> Unit
) {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(115.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = item.poster.link,
                    contentScale = ContentScale.Crop,
                    contentDescription = "user history item",
                    loading = {
                        CircularProgressBox(
                            indicatorColor = PrimaryRed500
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column() {
                Text(
                    text = item.name,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = Constant.font700
                )
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp),
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
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .width(80.dp)
                        .height(26.dp)
                        .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
                        .clickable { onClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.size(12.dp),
                            painter = painterResource(id = R.drawable.ic_play),
                            contentDescription = "",
                            tint = (PrimaryRed400)
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            text = stringResource(id = R.string.see_movie),
                            fontSize = 12.sp,
                            color = PrimaryRed400,
                            fontFamily = Constant.font700
                        )
                    }
                }
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 24.dp),
            thickness = 1.dp,
            color = Grey300
        )
    }
}