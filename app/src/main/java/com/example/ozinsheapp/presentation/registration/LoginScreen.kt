package com.example.ozinsheapp.presentation.registration

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ozinsheapp.R
import com.example.ozinsheapp.data.model.Resource
import com.example.ozinsheapp.ui.theme.Grey200
import com.example.ozinsheapp.ui.theme.Grey400
import com.example.ozinsheapp.ui.theme.Grey500
import com.example.ozinsheapp.ui.theme.Grey900
import com.example.ozinsheapp.ui.theme.PrimaryRed300
import com.example.ozinsheapp.utils.Constant
import com.example.ozinsheapp.utils.common.CustomButton
import com.example.ozinsheapp.utils.common.ProgressBlock
import com.example.ozinsheapp.utils.common.RegistrationTextField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navigateToSignUp: () -> Unit,
    navigateToHome: () -> Unit
) {
    val loginState by viewModel.login.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    when (loginState) {

        is Resource.Loading -> {
            ProgressBlock()
        }

        is Resource.Success -> {
            navigateToHome()
        }

        is Resource.Failure -> {
            Log.d("CheckRequestStatus", ":failure")
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(24.dp)
            ) {

                RegistrationTopBlock(
                    mainText = stringResource(id = R.string.hello),
                    subText = stringResource(id = R.string.enter_to_account)
                )

                TextFieldBlock(
                    email = email,
                    password = password,
                    onEmailChanged = { newEmail ->
                        email = newEmail
                    },
                    onPasswordChanged = { newPassword ->
                        password = newPassword
                    }
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 15.dp),
                    text = stringResource(id = R.string.are_you_forget_password),
                    fontSize = 14.sp,
                    fontFamily = Constant.font500,
                    color = PrimaryRed300
                )

                ButtonWithSubTextBlock(
                    viewModel = viewModel,
                    email = email,
                    password = password,
                    buttonText = stringResource(id = R.string.enter),
                    navigation = navigateToSignUp,
                    annotatedString = stringResource(id = R.string.follow),
                    subText = stringResource(id = R.string.dont_have_account)
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp),
                    text = stringResource(id = R.string.or),
                    fontSize = 14.sp,
                    fontFamily = Constant.font500,
                    color = Grey400
                )

                LoginWithBlock(
                    icon = R.drawable.ic_google,
                    blockText = stringResource(id = R.string.login_with_google)
                )
            }
        }
    }
}

@Composable
fun RegistrationTopBlock(
    mainText: String,
    subText: String,
) {
    Icon(
        modifier = Modifier
            .padding(start = 8.dp)
            .width(6.dp)
            .height(12.dp),
        painter = painterResource(id = R.drawable.ic_back),
        contentDescription = ""
    )
    Text(
        modifier = Modifier.padding(top = 24.dp),
        text = mainText,
        fontFamily = Constant.font700,
        fontSize = 24.sp,
        color = Grey900
    )
    Text(
        modifier = Modifier.padding(top = 5.dp),
        text = subText,
        fontFamily = Constant.font400,
        fontSize = 16.sp,
        color = Grey500
    )
}

@Composable
fun TextFieldBlock(
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit
) {

    var showPasswordValue by remember { mutableStateOf(value = false) }


    Text(
        modifier = Modifier.padding(top = 29.dp),
        text = stringResource(id = R.string.email),
        fontFamily = Constant.font700,
        fontSize = 14.sp,
        color = Grey900
    )
    RegistrationTextField(
        onValueChanged = { newEmail ->
            onEmailChanged(newEmail)
        },
        value = email,
        hint = "Сіздің email",
        showPassword = true,
        leadingIcon = R.drawable.ic_message
    )

    Text(
        modifier = Modifier.padding(top = 12.dp),
        text = stringResource(id = R.string.password),
        fontFamily = Constant.font700,
        fontSize = 14.sp,
        color = Grey900
    )
    RegistrationTextField(
        onValueChanged = { newPassword ->
            onPasswordChanged(newPassword)
        },
        value = password,
        hint = "Сіздің құпия сөзіңіз",
        leadingIcon = R.drawable.ic_message,
        showPassword = showPasswordValue,
        trailingIcon = {
            if (showPasswordValue) {
                IconButton(onClick = { showPasswordValue = false }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_show_password),
                        contentDescription = "show_password"
                    )
                }
            } else {
                IconButton(
                    onClick = { showPasswordValue = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_show_password),
                        contentDescription = "hide_password"
                    )
                }
            }
        }
    )
}

@Composable
fun ButtonWithSubTextBlock(
    viewModel: LoginViewModel,
    email: String = "",
    password: String = "",
    buttonText: String,
    navigation: () -> Unit,
    annotatedString: String,
    subText: String
) {
    Column() {
        CustomButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 40.dp),
            text = buttonText, //Кіру
            onClick = {
                viewModel.login(email.trim(), password.trim())
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = subText,
                color = Grey400,
                fontSize = 14.sp,
                fontFamily = Constant.font400,
                textAlign = TextAlign.Center
            )
            Text(
                text = buildAnnotatedString {
                    pushStyle(SpanStyle(color = PrimaryRed300))
                    append(" $annotatedString")
                    pop()
                },
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                fontFamily = Constant.font400,
                modifier = Modifier.clickable {
                    navigation()
                }
            )
        }
    }
}

@Composable
fun LoginWithBlock(
    icon: Int,
    blockText: String
) {
    Card(
        modifier = Modifier
            .padding(top = 16.dp)
            .border(2.dp, Grey200, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 70.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = icon),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = blockText,
                fontSize = 14.sp,
                fontFamily = Constant.font500,
                color = Grey900
            )
        }
    }
}