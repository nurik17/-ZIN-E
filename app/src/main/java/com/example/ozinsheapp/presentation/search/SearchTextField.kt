package com.example.ozinsheapp.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ozinsheapp.R
import com.example.ozinsheapp.ui.theme.Grey200
import com.example.ozinsheapp.ui.theme.Grey400
import com.example.ozinsheapp.ui.theme.Grey900
import com.example.ozinsheapp.ui.theme.PrimaryRed400
import com.example.ozinsheapp.utils.Constant

@Composable
fun SearchTextField(
    text: String,
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit,
    onClick: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = text,
        onValueChange = {
            onTextChanged(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        placeholder = {
            Text(
                text = "Іздеу",
                fontSize = 16.sp,
                color = Grey400,
                fontFamily = Constant.font700
            )
        },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .width(270.dp)
            .height(56.dp)
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .border(
                width = 1.5.dp,
                color = if (isFocused) PrimaryRed400 else Grey200,
                shape = RoundedCornerShape(12.dp)
            ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            cursorColor = PrimaryRed400,
            focusedTextColor = Grey900,
            unfocusedPlaceholderColor = Grey400,
            unfocusedTextColor = Grey900,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = Constant.font700
        ),
        trailingIcon = {
            if (text.isNotEmpty()) {
                ClearIcon(
                    onClick = {
                        onClick()
                        onTextChanged("")
                    }
                )
            }
        },
    )
}

@Composable
private fun ClearIcon(
    onClick: () -> Unit,
) {
    val customClearDrawable = painterResource(id = R.drawable.ic_clear)

    Image(
        painter = customClearDrawable,
        contentDescription = null,
        modifier = Modifier
            .size(20.dp)
            .clickable { onClick() }
    )
}