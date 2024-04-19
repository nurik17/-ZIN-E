package com.example.ozinsheapp.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ozinsheapp.ui.theme.Grey50
import com.example.ozinsheapp.ui.theme.Grey500
import com.example.ozinsheapp.ui.theme.Grey900
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.CustomButton

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
    pageIndex: Int,
    totalPages: Int,
    navigationHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = page.image),
                contentDescription = page.title,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFFFFFF),
                                Color(0xFAFFFFFF),
                                Color(0xFFFFFFFF)
                            ),
                            startY = 100f,
                            endY = 600f
                        )
                    ),
            )
            if (pageIndex != totalPages - 1) {
                Button(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 16.dp, end = 16.dp)
                        .width(80.dp)
                        .height(28.dp)
                        .background(Grey50, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp)),
                    onClick = { navigationHome() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Grey50,
                        contentColor = Grey900
                    )
                ) {
                    Text(
                        text = "Өткізу",
                        fontSize = 10.sp,
                        fontFamily = Constant.font500,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = page.title,
            fontSize = 24.sp,
            color = Grey900,
            fontFamily = Constant.font700,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = page.description,
            fontSize = 14.sp,
            color = Grey500,
            fontFamily = Constant.font500,
            textAlign = TextAlign.Center
        )
    }
}