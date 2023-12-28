package com.example.precision

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavigateToCounter(activityClass: Class<*>, buttonText: String) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(context, activityClass)
            context.startActivity(intent)
        }
    ) {
        Text(buttonText)
    }
}

@Composable
fun NameText(text: String) {
    Row(
        modifier = Modifier.
        padding(0.dp, 0.dp, 0.dp, 10.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp // You can adjust the size as needed
        )
    }
}

@Composable
fun HeadText(text: String) {
  Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Start
  ) {
      Text(
          text = text,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
      )
  }
}

@Composable
fun CountedNo(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 200.sp // You can adjust the font size as needed
        )
    }
}


