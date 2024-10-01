package com.example.chart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.AccessibilityConfig
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.drawBarGraph
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.chart.ui.theme.ChartTheme




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChartTheme {
               Surface(modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background) {
                   ChartsScreen()


                }
            }
        }
    }
}

@Composable
fun ChartsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "Charts Demo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LineChartSection()
        Spacer(modifier = Modifier.height(32.dp))
        PieChartSection()
        Spacer(modifier = Modifier.height(32.dp))
        BarChartSection()
    }
}

@Composable
fun LineChartSection() {
    Text("Line Chart", fontSize = 20.sp, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(8.dp))

    val pointsData = listOf(
        Point(1f, 40f), Point(2f, 90f), Point(3f, 0f),
        Point(4f, 60f), Point(5f, 10f)
    )

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .steps(pointsData.size - 1)
        .labelData { i -> (i + 1).toString() }
        .build()

    val yAxisData = AxisData.Builder()
        .steps(5)
        .labelData { i -> (i * 20).toString() }
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(color = Color.Blue, width = 5f),
                    IntersectionPoint(color = Color.Blue, radius = 6.dp),
                    SelectionHighlightPoint(color = Color.Red, radius = 8.dp),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Blue, Color.Transparent)
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(Color.LightGray, 1.dp)
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}

@Composable
fun PieChartSection() {
    Text("Pie Chart", fontSize = 20.sp, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(8.dp))

    val pieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("Slice 1", 30f, Color.Red),
            PieChartData.Slice("Slice 2", 20f, Color.Blue),
            PieChartData.Slice("Slice 3", 15f, Color.Green),
            PieChartData.Slice("Slice 4", 35f, Color.Magenta)
        ),
        plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        labelVisible = true,
        strokeWidth = 120f,
        labelColor = Color.Black,
        activeSliceAlpha = .9f,
        isAnimationEnable = true,
        chartPadding = 25
    )

    PieChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        pieChartData = pieChartData,
        pieChartConfig = pieChartConfig
    )
}

@Composable
fun BarChartSection() {
    Text("Bar Chart", fontSize = 20.sp, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(8.dp))

    val barChartData = BarChartData(
        chartData = listOf(
            BarData(Point(0f, 100f), Color.Red),
            BarData(Point(1f, 200f), Color.Blue),
            BarData(Point(2f, 300f), Color.Green),
            BarData(Point(3f, 400f), Color.Magenta),
            BarData(Point(4f, 500f), Color.Cyan)
        ),
        xAxisData = AxisData.Builder()
            .axisStepSize(100.dp)
            .steps(4)
            .labelData { i -> listOf("Jan", "Feb", "Mar", "Apr", "May")[i] }
            .build(),
        yAxisData = AxisData.Builder()
            .steps(5)
            .labelData { i -> (i * 100).toString() }
            .build(),
        backgroundColor = Color.White,
        horizontalExtraSpace = 0.dp,
        barStyle = BarStyle(),
        paddingEnd = 10.dp,
        paddingTop = 0.dp,
        tapPadding = 10.dp,
        showYAxis = true,
        showXAxis = true,
        accessibilityConfig = AccessibilityConfig(),
        barChartType = BarChartType.VERTICAL,
        drawBar = { drawScope, barData, drawOffset, height, barChartType, barStyle ->
            // default implementation
            drawBarGraph(drawScope, barData, drawOffset, height, barChartType, barStyle)
        }
    )



    BarChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        barChartData = barChartData
    )
}

