@file:OptIn(ExperimentalLayoutApi::class)

package com.example.ozinsheapp.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ozinsheapp.R
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.domain.entity.userhistory.Genre
import com.example.ozinsheapp.domain.entity.userhistory.Movie
import com.example.ozinsheapp.presentation.favourite.CardMovieItem
import com.example.ozinsheapp.presentation.home.detail.TopBarBlock
import com.example.ozinsheapp.ui.theme.Grey100
import com.example.ozinsheapp.ui.theme.Grey700
import com.example.ozinsheapp.ui.theme.Grey900
import com.example.ozinsheapp.ui.theme.PrimaryRed300
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.CircularProgressBox

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel
) {
    val resultList by viewModel.searchByQuery.collectAsStateWithLifecycle()
    val searchByName by viewModel.searchByName.collectAsStateWithLifecycle()
    val genresList by viewModel.genres.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var searchText by remember {
        mutableStateOf("")
    }
    var chooseShowList by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getGenres()
    }

    Scaffold(
        topBar = {
            TopBarBlock(
                screenName = stringResource(id = R.string.search),
                onBackClick = {
                    viewModel.changedUiState()
                }
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            SearchRow(
                searchText = searchText,
                onSearchTextChanged = { searchText = it },
                onSearchClicked = {
                    viewModel.searchByName(searchText)
                    chooseShowList = true
                    viewModel.changedUiState()

                },
                onClick = {
                    viewModel.changedUiState()
                }
            )
            if (uiState) {
                when (resultList) {
                    is Resource.Loading -> {
                        CircularProgressBox()
                    }

                    is Resource.Success -> {
                        val searchListByQuery = (resultList as Resource.Success).data
                        val contentList = searchListByQuery?.content ?: emptyList()
                        Text(
                            modifier = Modifier.padding(top = 32.dp, bottom = 24.dp),
                            text = stringResource(id = R.string.result_of_search),
                            fontSize = 24.sp,
                            fontFamily = Constant.font700,
                            color = Grey900
                        )
                        LazyColumn() {
                            if(!chooseShowList){
                                items(contentList) { item ->
                                    CardMovieItem(
                                        item = item,
                                        onClick = {}
                                    )
                                }
                            }else{
                                items(searchByName){item->
                                    CardMovieItem(
                                        item = item,
                                        onClick = {}
                                    )
                                }
                            }
                        }
                    }

                    is Resource.Failure -> {

                    }

                    else -> {}
                }
            } else {
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(id = R.string.category),
                    fontSize = 24.sp,
                    fontFamily = Constant.font700,
                    color = Grey900
                )
                FlowRow(
                    modifier = Modifier.padding(top = 5.dp),
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    genresList.forEach { item ->
                        GenresItem(
                            name = item.name,
                            onClick = {
                                chooseShowList = false
                                viewModel.searchByQuery(genreId = item.id)
                                viewModel.changedUiState()
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SearchRow(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onClick:()->Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        SearchTextField(
            text = searchText,
            onTextChanged = onSearchTextChanged,
            modifier = Modifier.padding(end = 16.dp),
            onClick = {
                onClick()
            }
        )
        Card(
            modifier = Modifier
                .size(56.dp)
                .clickable { onSearchClicked() },
            colors = CardDefaults.cardColors(
                containerColor = Grey100,
                contentColor = PrimaryRed300
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                modifier = Modifier
                    .padding(18.dp)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.ic_search2),
                contentDescription = "",
                tint = PrimaryRed300
            )
        }
    }
}

@Composable
fun GenresItem(
    name: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Grey100,
            contentColor = Grey700
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            text = name,
            fontSize = 12.sp,
            fontFamily = Constant.font700,
            color = Grey700
        )
    }
}


@Composable
fun CategoriesFlowBlock(
    genresList: List<Genre>,
    viewModel: SearchViewModel
) {
    Text(
        modifier = Modifier.padding(top = 20.dp),
        text = stringResource(id = R.string.category),
        fontSize = 24.sp,
        fontFamily = Constant.font700,
        color = Grey900
    )
    FlowRow(
        modifier = Modifier.padding(top = 5.dp),
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        genresList.forEach { item ->
            GenresItem(
                name = item.name,
                onClick = {
                    viewModel.searchByQuery(genreId = item.id)
                    viewModel.changedUiState()
                }
            )
        }
    }
}

@Composable
fun SearchResultListBlock(
    list: List<Movie>
) {
    Text(
        modifier = Modifier.padding(top = 32.dp, bottom = 24.dp),
        text = stringResource(id = R.string.result_of_search),
        fontSize = 24.sp,
        fontFamily = Constant.font700,
        color = Grey900
    )
    LazyColumn() {
        items(list) { item ->
            CardMovieItem(
                item = item,
                onClick = {}
            )
        }
    }
}