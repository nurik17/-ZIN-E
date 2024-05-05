package com.example.ozinsheapp.utils.common

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ozinsheapp.ui.theme.Grey400
import com.example.ozinsheapp.ui.theme.PrimaryRed400
import com.example.ozinsheapp.utils.Constant

@Composable
fun RegistrationTextField(
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: Int = 1,
    hint: String,
    value: String,
    showPassword: Boolean = false,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorState: Boolean = false,
) {
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = { newValue ->
            onValueChanged(newValue)
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = if (errorState) Color.Red else if (isFocused) PrimaryRed400 else MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(12.dp)
            )
            .shadow(8.dp),
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = Constant.font400,
            color = MaterialTheme.colorScheme.onBackground
        ),
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = leadingIcon),
                contentDescription = ""
            )
        },
        trailingIcon = trailingIcon,
        placeholder = {
            Text(
                text = hint,
                color = Grey400,
                fontFamily = Constant.font400,
                fontSize = 15.sp
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedLeadingIconColor = Grey400,
            unfocusedLeadingIconColor = Grey400,
            focusedTrailingIconColor = Grey400,
            unfocusedTrailingIconColor = Grey400,
            focusedPlaceholderColor = Grey400,
            unfocusedPlaceholderColor = Grey400,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),

        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
    )
}