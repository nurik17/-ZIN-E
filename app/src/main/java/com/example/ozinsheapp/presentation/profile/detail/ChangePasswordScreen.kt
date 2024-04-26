package com.example.ozinsheapp.presentation.profile.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.ozinsheapp.R
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.domain.entity.profile.ChangePassword
import com.example.ozinsheapp.presentation.home.detail.TopBarBlock
import com.example.ozinsheapp.presentation.profile.ProfileViewModel
import com.example.ozinsheapp.ui.theme.Grey900
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.CustomButton
import com.example.ozinsheapp.utils.common.RegistrationTextField

@Composable
fun ChangePasswordScreen(
    viewModel: ProfileViewModel,
    navController: NavHostController
) {

    val changePassword by viewModel.changePassword.collectAsStateWithLifecycle()

    Column() {
        SuccessStateChangePassword(
            viewModel = viewModel,
            changePassword = changePassword,
            navController = navController
        )
    }
}

@Composable
fun SuccessStateChangePassword(
    viewModel: ProfileViewModel,
    changePassword: Resource<ChangePassword>,
    navController: NavHostController
) {

    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(value = true) }

    Scaffold(
        topBar = {
            TopBarBlock(
                screenName = stringResource(id = R.string.user_data)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(top = 35.dp),
                text = "Password",
                fontFamily = Constant.font700,
                fontSize = 14.sp,
                color = Grey900
            )

            RegistrationTextField(
                onValueChanged = { newPassword ->
                    password = newPassword
                },
                value = password,
                hint = stringResource(id = R.string.your_password),
                showPassword = showPassword,
                leadingIcon = R.drawable.ic_message,
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = false }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_show_password),
                                contentDescription = "show_password"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPassword = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_show_password),
                                contentDescription = "hide_password"
                            )
                        }
                    }
                },
            )
            when (changePassword) {
                    is Resource.Success -> {
                        navController.popBackStack()
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Failure -> {
                    }

                    else -> Unit
                }
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(
                text = stringResource(id = R.string.change_edit),
                onClick = { viewModel.changePassword(password) }
            )
        }
    }
}