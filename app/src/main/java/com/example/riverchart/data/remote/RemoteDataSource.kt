package com.example.riverchart.data.remote

import com.example.riverchart.data.Resource
import com.example.riverchart.dto.RiverData

internal interface RemoteDataSource {
    suspend fun requestData(): Resource<RiverData>
}
