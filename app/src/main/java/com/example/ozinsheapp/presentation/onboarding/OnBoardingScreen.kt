package com.example.ozinsheapp.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ozinsheapp.presentation.onboarding.components.OnBoardingPage
import com.example.ozinsheapp.presentation.onboarding.components.PageIndicator
import com.example.ozinsheapp.presentation.onboarding.components.pages
import com.example.ozinsheapp.utils.common.CustomButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    navigateToLogin: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0) {
        pages.size
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(state = pagerState) { index ->
            OnBoardingPage(
                page = pages[index],
                pageIndex = index,
                totalPages = pages.size,
                navigationHome = { navigateToLogin() }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            PageIndicator(
                modifier = Modifier.padding(top = 30.dp),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )
        }

        if(pagerState.currentPage == 2) {
            CustomButton(
                modifier = Modifier.padding(24.dp),
                text = "Әрі қарай",
                onClick = { navigateToLogin() }
            )
        }
    }
}
