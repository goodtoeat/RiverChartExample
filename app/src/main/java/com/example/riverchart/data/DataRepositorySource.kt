package com.example.riverchart.data

import com.example.riverchart.dto.RiverData
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun doDataRequest(): Flow<Resource<RiverData>>
}
