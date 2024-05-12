package com.example.ozinsheapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.ozinsheapp.R
import com.example.ozinsheapp.domain.entity.home.HomeMoviesItem
import com.example.ozinsheapp.domain.entity.home.MoviesMainItem
import com.example.ozinsheapp.domain.entity.userhistory.CategoryAge
import com.example.ozinsheapp.domain.entity.userhistory.Genre
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.ui.theme.Grey400
import com.example.ozinsheapp.ui.theme.PrimaryRed500
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.ShimmerBox
import java.util.Locale

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToMovieDetails: (Int) -> Unit
) {
    val userHistory by viewModel.userHistory.collectAsStateWithLifecycle()
    val moviesMain by viewModel.moviesMain.collectAsStateWithLifecycle()
    val genres by viewModel.genres.collectAsStateWithLifecycle()
    val categoryAges by viewModel.categoryAges.collectAsStateWithLifecycle()

    val isLoadingUserHistory by viewModel.isLoadingUserHistory.collectAsStateWithLifecycle()
    val isLoadingMoviesMain by viewModel.isLoadingMoviesMain.collectAsStateWithLifecycle()
    val isLoadingMovies by viewModel.isLoadingMovies.collectAsStateWithLifecycle()
    val isLoadingGenre by viewModel.isLoadingGenre.collectAsStateWithLifecycle()
    val isLoadingCategoryAge by viewModel.isLoadingCategoryAge.collectAsStateWithLifecycle()

    val categoryNames = listOf(
        "ÖZINŞE–де танымал",
        "Телехикаялар",
        "Толықметрлі мультфильмдер",
        "Ситкомдар",
        "Мультсериалдар"
    )

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 24.dp, end = 24.dp, top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AppNameBlock()
        Spacer(modifier = Modifier.height(20.dp))
        if (!isLoadingMoviesMain && moviesMain.isNotEmpty()) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(moviesMain) { item ->
                    MoviesMain(
                        item = item,
                        onClick = {
                        },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        if (!isLoadingUserHistory && userHistory.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.watch_history),
                fontSize = 16.sp,
                fontFamily = Constant.font700,
                color = MaterialTheme.colorScheme.onBackground
            )
            LazyRow(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(userHistory) { item ->
                    UserHistoryMovieItem(
                        item = item,
                        onClick = {
                            navigateToMovieDetails(item.id)
                        }
                    )
                }
            }
        }
        //movie by categories
        categoryNames.forEach { categoryName ->
            val movies by viewModel.getMovies(categoryName).collectAsState()

            if (!isLoadingMovies && movies.isNotEmpty()) {
                Column {
                    Text(
                        modifier = Modifier.padding(top = 30.dp),
                        text = categoryName,
                        fontSize = 16.sp,
                        fontFamily = Constant.font700,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    LazyRow(modifier = Modifier.padding(top = 8.dp)) {
                        items(items = movies) { movie ->
                            movie.movies.forEach { item ->
                                PopularInOzinsheItem(
                                    item = movie,
                                    onClick = {
                                        navigateToMovieDetails(item.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        //genres
        if (!isLoadingGenre && genres.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = stringResource(id = R.string.choose_genre),
                fontSize = 16.sp,
                fontFamily = Constant.font700,
                color = MaterialTheme.colorScheme.onBackground
            )
            LazyRow(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = genres) { item ->
                    GenresItem(
                        item = item,
                        onClick = {},
                    )
                }
            }
        }
        //by ages
        if (!isLoadingCategoryAge && categoryAges.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(top = 30.dp),
                text = stringResource(id = R.string.category_by_ages),
                fontSize = 16.sp,
                fontFamily = Constant.font700,
                color = MaterialTheme.colorScheme.onBackground
            )
            LazyRow(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = categoryAges) { item ->
                    CategoryAgeItem(
                        item = item,
                        onClick = {},
                    )
                }
            }
        }
    }
}

@Composable
fun AppNameBlock() {
    Card(
        modifier = Modifier
            .width(90.dp)
            .height(40.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiary
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo1),
                contentDescription = "",
            )
            Image(
                modifier = Modifier.padding(start = 2.dp),
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}

@Composable
fun MoviesMain(
    item: MoviesMainItem,
    onClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onClick() },
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = item.movie.poster.link,
                contentScale = ContentScale.Crop,
                contentDescription = "movie main item",
                loading = {
                    ShimmerBox()
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(24.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = PrimaryRed500,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = item.movie.movieType.lowercase(Locale.getDefault()),
                        fontSize = 12.sp,
                        fontFamily = Constant.font500,
                        color = Color.White
                    )
                }
            }
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = item.movie.name,
            fontSize = 14.sp,
            fontFamily = Constant.font700,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = item.movie.description,
            fontSize = 14.sp,
            fontFamily = Constant.font400,
            color = Grey400,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun UserHistoryMovieItem(
    item: Movie,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(120.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable { onClick() }
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = item.poster.link,
                contentScale = ContentScale.Crop,
                contentDescription = "user history item",
                loading = {
                    ShimmerBox()
                }
            )
        }
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = item.name,
            fontSize = 14.sp,
            fontFamily = Constant.font700,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = "${item.watchCount + 1}-${stringResource(id = R.string.series)}",
            fontSize = 14.sp,
            fontFamily = Constant.font400,
            color = Grey400,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PopularInOzinsheItem(
    item: HomeMoviesItem,
    onClick: () -> Unit,
) {

    item.movies.forEach { movie ->
        Column(
            modifier = Modifier
                .width(120.dp)
                .wrapContentHeight()
                .padding(end = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(164.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onClick() }
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = movie.poster.link,
                    contentScale = ContentScale.Crop,
                    contentDescription = "user history item",
                    loading = {
                        ShimmerBox()
                    }
                )
            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = movie.name,
                fontSize = 14.sp,
                fontFamily = Constant.font700,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = movie.genres[0].name,
                fontSize = 13.sp,
                fontFamily = Constant.font400,
                color = Grey400,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun GenresItem(
    item: Genre,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = item.link,
            contentScale = ContentScale.Crop,
            contentDescription = "user history item",
            loading = {
                ShimmerBox()
            }
        )
        Text(
            text = item.name,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontFamily = Constant.font500
        )
    }
}

@Composable
fun CategoryAgeItem(
    item: CategoryAge,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = item.link,
            contentScale = ContentScale.Crop,
            contentDescription = "user history item",
            loading = {
                ShimmerBox()
            }
        )
        Text(
            text = "${item.name} ${stringResource(id = R.string.age)}",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontFamily = Constant.font500
        )
    }
}
