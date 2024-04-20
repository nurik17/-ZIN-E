package com.example.ozinsheapp.presentation.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ozinsheapp.R
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.ui.theme.ErrorRed
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.ProgressBlock

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navigateToLogin: () -> Unit
) {
    val singUpState by viewModel.signUp.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()){
        when (singUpState) {
            is Resource.Loading -> {
                ProgressBlock()
            }

            is Resource.Success -> {
                navigateToLogin()
            }

            is Resource.Failure -> {
                errorMessage = (singUpState as Resource.Failure).message
                viewModel.changeState()
            }

            is Resource.Unspecified -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(24.dp)
                ) {
                    RegistrationTopBlock(
                        mainText = stringResource(id = R.string.follow),
                        subText = stringResource(id = R.string.fill_the_information)
                    )
                    TextFieldBlock(
                        email = email,
                        password = password,
                        onEmailChanged = { newEmail ->
                            email = newEmail
                        },
                        onPasswordChanged = { newPassword ->
                            password = newPassword
                        },
                        onConfirmPasswordChanged = { newValue->
                            confirmPassword = newValue
                        },
                        confirmPassword = confirmPassword,
                        includeLastTextField = true
                    )
                    if(errorMessage.isNotEmpty() && errorMessage == "Error: Email is already in use!"){
                        Text(
                            text = stringResource(id = R.string.email_already_registered),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .fillMaxWidth(),
                            color = ErrorRed,
                            fontSize = 14.sp,
                            fontFamily = Constant.font400
                        )
                    }
                    ButtonWithSubTextBlock(
                        buttonText = stringResource(id = R.string.enter),
                        navigation = { navigateToLogin() },
                        annotatedString = stringResource(id = R.string.follow),
                        subText = stringResource(id = R.string.do_you_have_account),
                        onClickAction = {
                            viewModel.signUp(email, password)
                        }
                    )
                }
            }
        }
    }
}