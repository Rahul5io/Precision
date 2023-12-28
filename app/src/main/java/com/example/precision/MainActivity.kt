package com.example.precision

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



import androidx.compose.ui.Modifier


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addTestValues(this)             //To re-add data after clearing the data

        setContent {

             Column(
                 modifier = Modifier
                     .fillMaxSize()
                     .padding(10.dp),
                 horizontalAlignment = Alignment.CenterHorizontally,
                 verticalArrangement = Arrangement.Center

             ) {
                 NameText("Precision")
                 HeadText("Relapse Overview:")
                 ShowChart()
                 HeadText("Relapse On:")
                 DisplaySavedData()

                 Spacer(modifier = Modifier.weight(0.5f))
                 NavigateToCounter(CounterActivity::class.java, "Navigate to Counter")

             }


        }
    }
}






@Preview
@Composable
fun PreviewHelloWorld() {
    ShowChart()
}
