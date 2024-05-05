package com.example.ozinsheapp.presentation.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ozinsheapp.ui.theme.Grey400
import com.example.ozinsheapp.utils.Constant

@Composable
fun ProfileTextField(
    label: String,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Number,
    enabled: Boolean = true,
    value: String,
) {
    var text by remember { mutableStateOf(value) }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChanged(it)
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = Constant.font400,
            color = MaterialTheme.colorScheme.onBackground
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedLabelColor = Grey400,
            unfocusedLabelColor = Grey400,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            /*cursorColor = Color.Blue,
            focusedLabelColor = MaterialTheme.colorScheme.inversePrimary,
            errorLabelColor = Color.Red,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledLabelColor = MaterialTheme.colorScheme.inversePrimary,
            disabledTextColor = MaterialTheme.colorScheme.inversePrimary*/
        ),
        singleLine = false,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        enabled = enabled,
    )
}
