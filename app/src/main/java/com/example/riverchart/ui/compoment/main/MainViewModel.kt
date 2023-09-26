package com.example.riverchart.ui.compoment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.riverchart.data.DataRepositorySource
import com.example.riverchart.data.Resource
import com.example.riverchart.dto.RiverData
import com.example.riverchart.ui.base.BaseViewModel
import com.example.riverchart.utils.MPChartHelp
import com.github.mikephil.charting.data.LineData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource,
    private val mpChartHelp: MPChartHelp
): BaseViewModel() {

    private val lineDataPrivate = MutableLiveData<LineData>()
    val lineData: LiveData<LineData> get() = lineDataPrivate

    private val xLabelPrivate = MutableLiveData<List<String>>()
    val xLabel: LiveData<List<String>> get() = xLabelPrivate

    fun getRiverRawData(){
        viewModelScope.launch {
            dataRepository.doDataRequest().collect(){
                when (it){
                    is Resource.Success -> {
                        xLabelPrivate.value = getMPChartXValue(it.data!!)
                        lineDataPrivate.value = handleRiverData(it.data)
                    }
                    is Resource.DataError ->{
                        // Request error
                    }
                    is Resource.Loading -> {}
                }

            }
        }
    }

    private fun getMPChartXValue(riverData: RiverData): List<String>{
        return mpChartHelp.getLabels(riverData)
    }

    private fun handleRiverData(riverData: RiverData): LineData{
        //取得繪圖資料
        val chartData = mpChartHelp.getChartData(riverData)
        return mpChartHelp.getLineData(riverData, chartData)
    }


}