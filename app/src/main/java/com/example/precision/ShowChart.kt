package com.example.precision

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entriesOf
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ShowChart() {
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

    val last7Days = getLast7Days()
    val composedChartEntryModelProducer = ComposedChartEntryModelProducer.build {
        val chartEntries = last7Days.mapIndexed { index, date ->
            val count = savedDataState.value[date] ?: 0
            index.toFloat() to count.toFloat()
        }
        add(entriesOf(*chartEntries.toTypedArray()))
    }

    val lineChart = lineChart()
    Chart(
        chart = remember { lineChart },
        chartModelProducer = composedChartEntryModelProducer,
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis(),
    )
}

fun getLast7Days(): List<String> {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return (0..6).map {
        Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -it) }.time
    }.map { formatter.format(it) }.reversed()
}



fun addTestValues(context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val testDates = listOf(

        "2023-12-13",
        "2023-12-15",
        "2023-12-18",
        "2023-12-19",
        "2023-12-20",
        "2023-12-21",
        "2023-12-23"
    )

    testDates.forEach { date ->
        val count = sharedPreferences.getInt(date, 0)
        editor.putInt(date, count + 1)
    }

    editor.apply()
}

