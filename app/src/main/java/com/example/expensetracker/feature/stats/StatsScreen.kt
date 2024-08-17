package com.example.expensetracker.feature.stats

import android.view.LayoutInflater
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.Utils
import com.example.expensetracker.feature.home.TransactionList
import com.example.expensetracker.viewmodel.StatsViewModel
import com.example.expensetracker.viewmodel.StatsViewModelFactory
import com.example.expensetracker.widgets.ExpenseTextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
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
        val topExpense=viewModel.topEntries.collectAsState(initial = emptyList())
        Column(modifier = Modifier.padding(innerPadding)) {
            val entries=viewModel.getEntriesForChart(dataState.value)
            LineChart(entries=entries)
            Spacer(modifier = Modifier.height(16.dp))
            TransactionList(modifier =Modifier , list =topExpense.value,"Top Spending" )
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
    },modifier= Modifier
        .fillMaxWidth()
        .height(250.dp)){view->
    val lineChart=view.findViewById<LineChart>(R.id.lineChart)
        //passing the data
        val dataset=LineDataSet(entries,"Expenses").apply{
        //define the graph properties otherwise default values
            // 29:36 part 4
            color = android.graphics.Color.parseColor("#FF2F7E79")
            valueTextColor = android.graphics.Color.BLACK
            lineWidth = 3f
            axisDependency = YAxis.AxisDependency.RIGHT
            setDrawFilled(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            valueTextSize = 12f
            valueTextColor = android.graphics.Color.parseColor("#FF2F7E79")
            val drawable = ContextCompat.getDrawable(context, R.drawable.char_gradient)
            drawable?.let {
                fillDrawable = it
            }
        }
        object : com.github.mikephil.charting.formatter.ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return Utils.formatDateForChart(value.toLong())
            }
        }
        lineChart.data=LineData(dataset)
        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.setDrawAxisLine(false)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.invalidate()
    }
}