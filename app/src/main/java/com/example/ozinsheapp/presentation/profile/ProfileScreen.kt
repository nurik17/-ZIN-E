package com.example.ozinsheapp.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.ozinsheapp.R
import com.example.ozinsheapp.data.model.AppTheme
import com.example.ozinsheapp.navigation.MainDestinations
import com.example.ozinsheapp.presentation.home.detail.TopBarBlock
import com.example.ozinsheapp.ui.theme.Grey200
import com.example.ozinsheapp.ui.theme.Grey300
import com.example.ozinsheapp.ui.theme.Grey400
import com.example.ozinsheapp.ui.theme.Grey50
import com.example.ozinsheapp.ui.theme.Grey900
import com.example.ozinsheapp.ui.theme.PrimaryRed300
import com.example.ozinsheapp.ui.theme.PrimaryRed600
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.CustomButton

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navigateToUserInfoScreen: () -> Unit,
    navigateToChangePasswordScreen: () -> Unit,
    navController: NavHostController
) {

    val userEmail by viewModel.userEmail.collectAsStateWithLifecycle()

    SuccessStateProfile(
        navigateToUserInfoScreen = navigateToUserInfoScreen,
        navigateToChangePasswordScreen = navigateToChangePasswordScreen,
        email = userEmail,
        navController = navController,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessStateProfile(
    viewModel: ProfileViewModel,
    navigateToUserInfoScreen: () -> Unit,
    navigateToChangePasswordScreen: () -> Unit,
    email: String,
    navController: NavHostController
) {

    var isChecked by rememberSaveable { mutableStateOf(false) }
    var isChecked2 by rememberSaveable { mutableStateOf(false) }

    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    var logoutSheetOpen by rememberSaveable { mutableStateOf(false) }
    var languageSheetOpen by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBarBlock(
                screenName = stringResource(id = R.string.profile),
                iconId = R.drawable.ic_logout,
                onClick = {
                    logoutSheetOpen = true
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileImageBlock(email = email)
            }
            val context = LocalContext.current
            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .background(Grey50)
            ) {
                var chosenLanguage by rememberSaveable { mutableStateOf("") }
                ChooseLanguageBottomSheet(
                    visible = languageSheetOpen,
                    strings = listOf("English", "Қазақша", "Русский"),
                    chosenIndex = 1,
                    chooseCallback = { index ->
                        chosenLanguage = when (index) {
                            0 -> {
                                viewModel.changeLocales(context, "en")
                                "English"
                            }

                            1 -> {
                                viewModel.changeLocales(context, "kk")
                                "Қазақша"
                            }  // Kazakh
                            2 -> {
                                viewModel.changeLocales(context, "ru")
                                "Русский"
                            }

                            else -> {
                                viewModel.changeLocales(context, "en")
                                "English"
                            }
                        }
                    }
                ) {
                    languageSheetOpen = false
                }
                Spacer(modifier = Modifier.height(20.dp))
                RowProfileItem(
                    name = stringResource(id = R.string.user_data),
                    functionText = stringResource(id = R.string.change_data),
                    onClick = {
                        navigateToUserInfoScreen()
                    }
                )
                RowProfileItem(name = stringResource(id = R.string.change_password), onClick = {
                    navigateToChangePasswordScreen()
                })
                RowProfileItem(
                    name = stringResource(id = R.string.language),
                    functionText = chosenLanguage,
                    onClick = {
                        languageSheetOpen = true
                    })
                RowProfileItem(name = stringResource(id = R.string.rules))

                TextWithSwitch(
                    text = stringResource(id = R.string.notification),
                    isChecked = isChecked,
                    onCheckedChange = { isChecked = it }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 20.dp),
                    thickness = 1.dp,
                    color = Grey300
                )
                TextWithSwitch(
                    text = stringResource(id = R.string.night_mode),
                    isChecked = isChecked2,
                    onCheckedChange = {
                        isChecked2 = it
                        viewModel.changeTheme(if (isChecked2) AppTheme.NIGHT else AppTheme.DAY)
                    }
                )
            }
            if (logoutSheetOpen) {
                ModalBottomSheet(
                    modifier = Modifier,
                    sheetState = sheetState,
                    onDismissRequest = {
                        logoutSheetOpen = false
                    },
                    containerColor = Color.White,
                    dragHandle = { BottomSheetDefaults.DragHandle() },
                ) {
                    LogoutBottomSheetContent(
                        navController = navController
                    )
                }
            }
        }
    }
}


@Composable
fun LogoutBottomSheetContent(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .height(300.dp)
            .padding(24.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 24.dp),
            text = stringResource(id = R.string.logout),
            fontSize = 24.sp,
            fontFamily = Constant.font700,
            color = Grey900
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = R.string.logout_question),
            fontSize = 16.sp,
            fontFamily = Constant.font400,
            color = Grey400
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomButton(
            text = stringResource(id = R.string.logout_question_confirm),
            onClick = {
                navController.navigate(MainDestinations.LoginScreen_route) {
                }
            }
        )
        Text(
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.no),
            fontSize = 16.sp,
            fontFamily = Constant.font500,
            color = PrimaryRed600
        )
    }
}

@Composable
fun TextWithSwitch(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Grey900,
            fontFamily = Constant.font500
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryRed300,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Grey200,
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
fun RowProfileItem(
    name: String,
    functionText: String = "",
    onClick: () -> Unit = {}
) {
    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = name,
                fontSize = 16.sp,
                color = Grey900,
                fontFamily = Constant.font500
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = functionText,
                fontSize = 13.sp,
                color = Grey400,
                fontFamily = Constant.font500
            )
            Icon(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(10.dp),
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "",
                tint = PrimaryRed300
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 20.dp),
            thickness = 1.dp,
            color = Grey300
        )
    }
}

@Composable
fun ProfileImageBlock(
    email: String
) {
    Column() {
        Image(
            modifier = Modifier
                .padding(top = 30.dp)
                .align(Alignment.CenterHorizontally)
                .size(120.dp),
            painter = painterResource(id = R.drawable.image_profile),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.my_profile),
            fontSize = 24.sp,
            fontFamily = Constant.font700,
            color = Grey900,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = email ?: "",
            fontSize = 14.sp,
            fontFamily = Constant.font700,
            color = Grey400,
            textAlign = TextAlign.Center
        )
    }
}

