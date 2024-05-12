package com.example.ozinsheapp.presentation.profile

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.navigation.MainDestinations
import com.example.ozinsheapp.presentation.home.detail.TopBarBlock
import com.example.ozinsheapp.ui.theme.Grey200
import com.example.ozinsheapp.ui.theme.Grey400
import com.example.ozinsheapp.ui.theme.PrimaryRed300
import com.example.ozinsheapp.ui.theme.PrimaryRed600
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.CustomButton
import com.example.ozinsheapp.utils.common.clickableWithoutRipple
import com.yariksoffice.lingver.Lingver
import java.util.Locale

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navigateToUserInfoScreen: () -> Unit,
    navigateToChangePasswordScreen: () -> Unit,
    navController: NavHostController
) {

    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = Modifier.fillMaxSize()) {
        when (userInfo) {
            is Resource.Loading -> {

            }

            is Resource.Success -> {
                val item = (userInfo as Resource.Success).data
                SuccessStateProfile(
                    navigateToUserInfoScreen = navigateToUserInfoScreen,
                    navigateToChangePasswordScreen = navigateToChangePasswordScreen,
                    email = item?.user?.email ?: throw IllegalStateException(),
                    navController = navController,
                    viewModel = viewModel
                )
            }

            is Resource.Failure -> {}
            else -> {}
        }
    }

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
    val context = LocalContext.current

    var isChecked by rememberSaveable { mutableStateOf(false) }
    var isChecked2 by rememberSaveable { mutableStateOf(false) }

    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    var logoutSheetOpen by rememberSaveable { mutableStateOf(false) }
    var languageSheetOpen by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TopBarBlock(
                        screenName = stringResource(id = R.string.profile),
                        iconId = R.drawable.ic_logout,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onClick = {
                            logoutSheetOpen = true
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
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onPrimary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileImageBlock(email = email)
            }
            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                var chosenLanguage by rememberSaveable { mutableStateOf("") }
                val systemLocale: Locale = Resources.getSystem().configuration.locale
                val defaultLanguage = systemLocale.displayLanguage
                Log.d("SuccessStateProfile", defaultLanguage)

                ChooseLanguageBottomSheet(
                    visible = languageSheetOpen,
                    strings = listOf("English", "Қазақша", "Русский"),
                    chosenIndex = 1,
                    chooseCallback = { index ->
                        chosenLanguage = when (index) {
                            0 -> {
                                viewModel.setLocale(context,"en")
                                "English"
                            }
                            1 -> {
                                viewModel.setLocale(context,"kk")
                                "Қазақша"
                            }
                            2 -> {
                                viewModel.setLocale(context,"ru")
                                "Русский"
                            }
                            else -> {
                                val languageCode = systemLocale.language
                                Log.d("SuccessStateProfile", languageCode)
                                viewModel.setLocale(context, languageCode)
                                defaultLanguage
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
                RowProfileItem(
                    name = stringResource(id = R.string.change_password),
                    onClick = {
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
                    color = MaterialTheme.colorScheme.surfaceDim
                )
                isChecked2 = viewModel.restoreSwitchState().value
                TextWithSwitch(
                    text = stringResource(id = R.string.night_mode),
                    isChecked = isChecked2,
                    onCheckedChange = {
                        isChecked2 = it
                        viewModel.changeTheme(if (isChecked2) AppTheme.NIGHT else AppTheme.DAY)
                        viewModel.saveSwitchState(isChecked2)
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
                    containerColor = MaterialTheme.colorScheme.onPrimary,
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
            color = MaterialTheme.colorScheme.onBackground
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
            color = MaterialTheme.colorScheme.onBackground,
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
    val interactionSource = remember { MutableInteractionSource() }

    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clickableWithoutRipple(
                    interactionSource = interactionSource,
                    onClick = {
                        onClick()
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = name,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
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
            color = MaterialTheme.colorScheme.surfaceDim
        )
    }
}

@Composable
fun ProfileImageBlock(
    email: String
) {
    Column(
        modifier = Modifier
            .padding(vertical = 40.dp)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(100.dp),
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
            color = MaterialTheme.colorScheme.onBackground,
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

