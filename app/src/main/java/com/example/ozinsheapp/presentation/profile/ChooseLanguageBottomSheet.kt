package com.example.ozinsheapp.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ozinsheapp.R
import com.example.ozinsheapp.ui.theme.Grey300
import com.example.ozinsheapp.ui.theme.Grey900
import com.example.ozinsheapp.ui.theme.PrimaryRed300
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.ModalSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseLanguageBottomSheet(
    visible: Boolean,
    strings: List<String>,
    chosenIndex: Int,
    chooseCallback: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    ModalSheet(
        visible = visible,
        onDismiss = onDismiss
    ) { sheetState ->
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                modifier = Modifier
                    .padding(top = 24.dp),
                text = stringResource(id = R.string.language),
                fontSize = 24.sp,
                fontFamily = Constant.font700,
                color = Grey900
            )
            Spacer(modifier = Modifier.height(32.dp))
            LazyColumn(
                modifier = Modifier.padding(bottom = 50.dp),
            ) {
                itemsIndexed(strings) { index, item ->
                    Column() {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                                .clickable(
                                    onClick = {
                                        scope
                                            .launch { sheetState.hide() }
                                            .invokeOnCompletion {
                                                if (!sheetState.isVisible) {
                                                    chooseCallback(index)
                                                    onDismiss()
                                                }
                                            }
                                    }
                                )
                        ) {
                            Text(
                                text = item,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 16.sp,
                                fontFamily = Constant.font500
                            )
                            if (index == chosenIndex) {
                                Spacer(modifier = Modifier.weight(1F))
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryRed300),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .size(15.dp),
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 10.dp),
                            thickness = 1.dp,
                            color = Grey300
                        )
                    }
                }
            }
        }
    }
}