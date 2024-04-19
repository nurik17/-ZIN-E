package com.example.ozinsheapp.utils.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ozinsheapp.ui.theme.PrimaryRed500
import com.example.ozinsheapp.utils.Constant

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = PrimaryRed500,
    onClick: () -> Unit,
    textColor: Color = Color.White,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        onClick = { onClick.invoke() },
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = textColor,
            fontFamily = Constant.font500
        )
    }
}