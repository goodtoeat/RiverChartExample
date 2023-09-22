package com.example.riverchart.ui.compoment.main

import android.os.Bundle
import androidx.activity.viewModels
import com.example.riverchart.databinding.ActivityMainBinding
import com.example.riverchart.ui.base.BaseActivity
import com.example.riverchart.utils.MPChartHelp
import com.example.riverchart.utils.observe
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mpChartHelp: MPChartHelp

    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mpChartHelp.initMPChart(binding.chart)
        viewModel.getRiverRawData()
    }

    override fun observeViewModel() {
        observe(viewModel.xLabel, ::setMPChartXAxisValue)
        observe(viewModel.lineData, ::drawMPChart)
    }

    private fun setMPChartXAxisValue(chartLabels: List<String>){
        binding.chart.xAxis.valueFormatter = IndexAxisValueFormatter(chartLabels)
    }

    private fun drawMPChart(lineData: LineData){
        binding.chart.data = lineData
    }
}