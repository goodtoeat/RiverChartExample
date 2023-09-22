package com.example.riverchart.utils

import android.graphics.Color
import com.example.riverchart.dto.RiverData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class MPChartHelp {

    fun getChartData(data: RiverData): List<MutableList<Entry>> {
        var chartData: MutableList<MutableList<Entry>> = mutableListOf()
        //初始化數據大小
        initEntity(data.data[0].河流圖資料[0].本益比股價基準.size, chartData)
        //預設只抓本益比資料
        data.data[0].河流圖資料.forEachIndexed{ index, 河流圖資料 ->
            河流圖資料.本益比股價基準.forEachIndexed{ indexInner, s ->
                chartData[indexInner].add(Entry((data.data[0].河流圖資料.size - index).toFloat(), s.toFloat()))
            }
        }
        //LineData資料反轉, 讓舊資料在圖表左邊
        chartData = chartData.reversed().toMutableList()

        //加入股價資料
        val entryList = mutableListOf<Entry>()
        data.data[0].河流圖資料.forEachIndexed{ index, 河流圖資料 ->
            entryList.add(Entry((data.data[0].河流圖資料.size - index).toFloat(), 河流圖資料.月平均收盤價.toFloat()))
        }
        chartData.add(entryList)

        //LineData集合反轉, 讓高數值的資料先繪製, 避免覆蓋掉低數值顏色區塊
        chartData.forEachIndexed { index, entries ->
            chartData[index] = entries.reversed().toMutableList()
        }
        return chartData
    }

    fun getLabels(data: RiverData): List<String> {
        val chartLabels: MutableList<String> = mutableListOf()
        data.data[0].河流圖資料.forEachIndexed { index, 河流圖資料 ->
            chartLabels.add(河流圖資料.年月)
        }
        return chartLabels.reversed()
    }

    fun getLineData(data: RiverData, chartData: List<List<Entry>>): LineData {
        val dataSets = ArrayList<ILineDataSet>()
        chartData.forEachIndexed { index, _ ->
            val dataSet = if (index==6){
                LineDataSet(chartData[index], "股價")
            }else{
                LineDataSet(chartData[index], "${data.data[0].本益比基準[index]} 倍 ${chartData[index].last().y}")
            }
            dataSet.axisDependency = AxisDependency.RIGHT
            dataSet.color = getLineColor(index)
            dataSet.setCircleColor(Color.TRANSPARENT)
            dataSet.lineWidth = getLineWidth(index)
            dataSet.circleRadius = 0f
            dataSet.setDrawCircleHole(false)
            dataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER

            if (enableFill(index)) {
                dataSet.setDrawFilled(true) // 启用填充
                dataSet.fillAlpha = getFillAlpha(index)
                dataSet.fillColor = getFillColor(index) // 设置填充颜色
            }else{
                dataSet.setDrawFilled(false)

            }
            dataSets.add(dataSet)
        }

        //將下一筆dataSet傳遞給當前dataSet MyFillFormatter, 用來當作fillColor時的boundary
        dataSets.forEachIndexed { index, it ->
            if (enableFill(index)) {
                (it as LineDataSet).fillFormatter = MyFillFormatter(dataSets[index+1])
            }
        }

        return LineData(dataSets)
    }

    private fun initEntity(size: Int, chartData: MutableList<MutableList<Entry>>){
        repeat(size) {
            val entryList = mutableListOf<Entry>()
            chartData.add(entryList)
        }
    }

    fun initMPChart(chart: LineChart){
        chart.description.isEnabled = true
        chart.setTouchEnabled(false)
        chart.setDrawGridBackground(false)
        chart.isHighlightPerDragEnabled = true
        chart.setHardwareAccelerationEnabled(true) // 启用硬件加速
        chart.setDrawBorders(false) // 禁用图表的边框
        chart.setBackgroundColor(Color.DKGRAY)
        chart.animateX(1500)

        //客製化Renderer, 讓fillColor可以只分布在兩組DataSet之間
        chart.renderer = MyLineLegendRenderer(chart, chart.animator, chart.viewPortHandler)

        val l = chart.legend
        l.form = LegendForm.SQUARE
        l.textSize = 14f
        l.textColor = Color.WHITE
        l.isWordWrapEnabled = true
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)

        val xAxis = chart.xAxis
        xAxis.textSize = 11f
        xAxis.textColor = Color.WHITE
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.xOffset = -30f
        xAxis.setDrawGridLines(true)
        xAxis.setDrawAxisLine(false)

        val leftAxis = chart.axisLeft
        leftAxis.isEnabled = false

        val rightAxis = chart.axisRight
        rightAxis.textColor = Color.GRAY
    }

    private fun getFillColor(position: Int): Int{
        return when (position){
            0 -> Color.parseColor("#FB7376")
            1 -> Color.parseColor("#FBA861")
            2 -> Color.parseColor("#FFD4AE")
            3 -> Color.parseColor("#BED8FF")
            4 -> Color.parseColor("#6287D6")
            5 -> Color.TRANSPARENT
            else -> Color.TRANSPARENT
        }
    }

    private fun getLineColor(position: Int): Int{
        return when (position){
            0 -> Color.parseColor("#FB7376")
            1 -> Color.parseColor("#FBA861")
            2 -> Color.parseColor("#FFD4AE")
            3 -> Color.parseColor("#BED8FF")
            4 -> Color.parseColor("#6287D6")
            5 -> Color.parseColor("#247778")
            6 -> Color.RED
            else -> Color.TRANSPARENT
        }
    }

    private fun getLineWidth(position: Int): Float{
        return when (position){
            5 -> 5f
            6 -> 3f
            else -> 0f
        }
    }

    private fun enableFill(position: Int): Boolean{
        return when (position){
            5 -> false
            6 -> false
            else -> true
        }
    }

    private fun getFillAlpha(position: Int): Int{
        return when (position){
            5 -> 0
            6 -> 0
            else -> 255
        }
    }

}