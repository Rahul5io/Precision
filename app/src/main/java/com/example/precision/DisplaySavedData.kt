package com.example.precision

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun DisplaySavedData() {
    val context = LocalContext.current
    val savedDataState = remember { mutableStateOf<Map<String, Int>>(DataRepository.getSavedData(context)) }

    DisposableEffect(Unit) {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
            savedDataState.value = DataRepository.getSavedData(context)
        }

        val prefs = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        prefs.registerOnSharedPreferenceChangeListener(listener)

        onDispose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    Column {



        LazyRow {
            items(savedDataState.value.toList().sortedByDescending { it.first }) { (dateString, count) ->
                val formattedDate = formatDate(dateString)
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 97.dp, height = 40.dp)
                        .shadow(elevation = 2.dp, RoundedCornerShape(25.dp))
                        .background(Color(0xFF8A2BE2), shape = RoundedCornerShape(25.dp))
                ) {
                    Text(
                        text = "$formattedDate",
                        modifier = Modifier.padding(15.dp, 10.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
    
}


fun formatDate(dateString: String): String {
    val formatter = SimpleDateFormat("dd-MM EEE", Locale.getDefault())
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(parser.parse(dateString) ?: return dateString)
}