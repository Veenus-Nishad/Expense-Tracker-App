package com.example.expensetracker.feature.stats

import android.view.LayoutInflater
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.expensetracker.R
import com.example.expensetracker.viewmodel.StatsViewModel
import com.example.expensetracker.viewmodel.StatsViewModelFactory
import com.example.expensetracker.widgets.ExpenseTextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun StatsScreen(navController: NavController) {
    Scaffold(topBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 16.dp, end = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier.align(
                    Alignment.CenterStart
                ), colorFilter = ColorFilter.tint(Color.Black)
            )
            ExpenseTextView(
                text = "Statistics",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = R.drawable.dots_menu),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }
    }) { innerPadding ->
        val viewModel=StatsViewModelFactory(navController.context).create(StatsViewModel::class.java)
        val dataState=viewModel.entries.collectAsState(emptyList())
        Column(modifier = Modifier.padding(innerPadding)) {
            val entries=viewModel.getEntriesForChart(dataState.value)
            LineChart(entries=entries)
        }
    }
}

/*
For graphs there are some Things that sdk require
*/
@Composable
fun LineChart(entries:List<Entry>) {
    val context= LocalContext.current
    AndroidView(factory = { // yaha layout ko banana hai main ie link xml file
        val view=LayoutInflater.from(context).inflate(R.layout.stats_line_chart,null)
        view
    },modifier=Modifier){view->
    val lineChart=view.findViewById<LineChart>(R.id.lineChart)
        //passing the data
        val dataset=LineDataSet(entries,"Expenses").apply{

        }
    }
}