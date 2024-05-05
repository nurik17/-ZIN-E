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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.ozinsheapp.presentation.favourite.CardMovieItem
import com.example.ozinsheapp.presentation.home.detail.TopBarBlock
import com.example.ozinsheapp.ui.theme.Grey900
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.CircularProgressBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel
) {
    val searchByQuery by viewModel.searchByQuery.collectAsStateWithLifecycle()
    val searchByNameLoading by viewModel.searchByNameLoading.collectAsStateWithLifecycle()
    val searchByName by viewModel.searchByName.collectAsStateWithLifecycle()
    val genresList by viewModel.genres.collectAsStateWithLifecycle()


    var searchText by remember {
        mutableStateOf("")
    }
    var showFlowBlock by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        viewModel.getGenres()
    }

    var showNameList by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TopBarBlock(
                        screenName = stringResource(id = R.string.search),
                        onBackClick = {
                            showFlowBlock = true
                            showNameList = false
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
            Spacer(modifier = Modifier.height(40.dp))

            SearchRow(
                searchText = searchText,
                onSearchTextChanged = { searchText = it },
                onSearchClicked = {
                    viewModel.searchByName(searchText)
                    showFlowBlock = false
                    showNameList = true
                },
                onDeleteClick = {
                    showFlowBlock = true
                    showNameList = false
                }
            )
            if (showFlowBlock) {
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(id = R.string.category),
                    fontSize = 24.sp,
                    fontFamily = Constant.font700,
                    color = MaterialTheme.colorScheme.onBackground
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
                                showFlowBlock = false
                                viewModel.searchByQuery(genreId = item.id)
                            }
                        )
                    }
                }
            } else {
                if (searchByName.isNotEmpty() && showNameList) {
                    if (searchByNameLoading) {
                        CircularProgressBox()
                    } else {
                        LazyColumn(
                            modifier = Modifier.padding(top = 20.dp)
                        ) {
                            items(searchByName) { item ->
                                CardMovieItem(
                                    item = item,
                                    onClick = {}
                                )
                            }
                        }
                    }
                } else {
                    when (searchByQuery) {
                        is Resource.Loading -> {
                            CircularProgressBox()
                        }

                        is Resource.Success -> {
                            val searchListByQuery = (searchByQuery as Resource.Success).data
                            val contentList = searchListByQuery?.content ?: emptyList()
                            Text(
                                modifier = Modifier.padding(top = 32.dp, bottom = 24.dp),
                                text = stringResource(id = R.string.result_of_search),
                                fontSize = 24.sp,
                                fontFamily = Constant.font700,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            LazyColumn() {
                                items(contentList) { item ->
                                    CardMovieItem(
                                        item = item,
                                        onClick = {}
                                    )
                                }
                            }
                        }

                        is Resource.Failure -> {
                        }

                        else -> {
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FlowRowBlock(
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
                }
            )
        }
    }
}


@Composable
fun SearchRow(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchClicked: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        SearchTextField(
            text = searchText,
            onTextChanged = onSearchTextChanged,
            modifier = Modifier.padding(end = 16.dp),
            onClick = {
                onDeleteClick()
            }
        )
        Card(
            modifier = Modifier
                .size(56.dp)
                .clickable { onSearchClicked() },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                modifier = Modifier
                    .padding(18.dp)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.ic_search2),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSecondary
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
            containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            contentColor = MaterialTheme.colorScheme.inversePrimary
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            text = name,
            fontSize = 12.sp,
            fontFamily = Constant.font700,
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }
}
