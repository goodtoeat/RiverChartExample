package com.example.riverchart.data.remote.service

import com.example.riverchart.dto.RiverData
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("per-river/interview?stock_id=2330")
    suspend fun loadData(): Response<RiverData>
}
