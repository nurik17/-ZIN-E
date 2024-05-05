package com.example.ozinsheapp.presentation.profile.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.ozinsheapp.R
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.domain.entity.profile.UserInfo
import com.example.ozinsheapp.presentation.home.detail.TopBarBlock
import com.example.ozinsheapp.presentation.profile.ProfileTextField
import com.example.ozinsheapp.presentation.profile.ProfileViewModel
import com.example.ozinsheapp.utils.common.CircularProgressBox
import com.example.ozinsheapp.utils.common.CustomButton
import com.example.ozinsheapp.utils.common.ProgressBlock

@Composable
fun UserInfoScreen(
    viewModel: ProfileViewModel,
    navController: NavHostController
) {

    val userInfoState by viewModel.userInfo.collectAsStateWithLifecycle()
    val updateProfileState by viewModel.updateProfile.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getUserInfo()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (userInfoState) {
            is Resource.Loading -> {
                CircularProgressBox()
            }

            is Resource.Failure -> {
                Log.d("UserInfoScreen", "error")
            }

            is Resource.Success -> {
                val item = (userInfoState as Resource.Success).data
                item?.let {
                    SuccessStateUserInfo(
                        item = item,
                        viewModel = viewModel,
                        updateProfileState = updateProfileState,
                        navController = navController
                    )
                }
            }

            else -> Unit
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessStateUserInfo(
    item: UserInfo?,
    viewModel: ProfileViewModel,
    updateProfileState: Resource<UserInfo>,
    navController: NavHostController
) {

    var name by remember { mutableStateOf(item?.name.toString()) }
    var email by remember { mutableStateOf(item?.user?.email) }
    var phoneNumber by remember { mutableStateOf(item?.phoneNumber.toString()) }
    var birthDate by remember { mutableStateOf(item?.birthDate.toString()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TopBarBlock(
                        screenName = stringResource(id = R.string.user_data),
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
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            ProfileTextField(
                label = stringResource(id = R.string.your_name),
                onValueChanged = { newValue ->
                    name = newValue
                },
                value = name,
                keyboardType = KeyboardType.Text
            )
            email?.let {
                ProfileTextField(
                    label = "Email",
                    onValueChanged = { newValue ->
                        email = newValue
                    },
                    value = it,
                    keyboardType = KeyboardType.Text
                )
            }
            ProfileTextField(
                label = stringResource(id = R.string.phone),
                onValueChanged = { newValue ->
                    phoneNumber = newValue
                },
                value = phoneNumber,
            )
            ProfileTextField(
                label = stringResource(id = R.string.birth_date),
                onValueChanged = { newValue ->
                    birthDate = newValue
                },
                value = birthDate
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                when (updateProfileState) {
                    is Resource.Success -> {
                        navController.popBackStack()
                    }

                    is Resource.Loading -> {
                        ProgressBlock()
                    }

                    is Resource.Failure -> {
                        Log.d("SuccessStateUserInfo", "update profile error")
                    }

                    is Resource.Unspecified -> {

                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                text = stringResource(id = R.string.change_edit),
                onClick = {
                    viewModel.updateProfile(
                        birthDate = birthDate,
                        0,
                        "",
                        name,
                        phoneNumber
                    )
                }
            )
        }
    }
}