package com.example.precision

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.precision.ui.theme.PrecisionTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


object DataRepository {
    fun getSavedData(context: Context): Map<String, Int> {
        val sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val allEntries = sharedPreferences.all
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        return allEntries.keys.groupBy {
            it.split(" ")[0] // Extract date part
        }.mapValues { (_, values) ->
            values.size // Count the number of entries per date
        }.filterKeys {
            // Filter last 7 days
            val date = formatter.parse(it)
            date != null && date.time > System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000
        }
    }
}

class CounterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


                Counter(context = LocalContext.current)


        }
    }
}

@Composable
fun Counter(context: Context) {
    //val count = remember { mutableStateOf(0) }
    val savedData = remember { mutableStateOf(context.getSharedPreferences("MyPref", Context.MODE_PRIVATE).all) }
    val count = remember { mutableStateOf(savedData.value.size) }
    val sharedPref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        //Display Counter Value
        CountedNo("${count.value}")


        // Display saved data in a scrollable column
        LazyRow() {
            items(savedData.value.entries.toList()) { entry ->
                Text("${entry.key}: ${entry.value}", modifier = Modifier.padding(4.dp))
            }
        }


        Row(verticalAlignment = Alignment.Top) {
            Button(onClick = {
                val currentDateTime = Date()
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val formattedDateTime = formatter.format(currentDateTime)

                with (sharedPref.edit()) {
                    putInt(formattedDateTime, 1)
                    apply()
                }
                savedData.value = sharedPref.all // Update the saved data here
                count.value = savedData.value.size
            }) {
                Text("Increase Count")
            }
        }

    }

    LaunchedEffect(count.value) {
        // This block will be executed whenever `count.value` changes
        // Here you can update your UI or perform other side effects
    }
}


