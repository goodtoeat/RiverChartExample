package com.example.riverchart.data

import com.example.riverchart.data.remote.RemoteData
import com.example.riverchart.dto.RiverData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val ioDispatcher: CoroutineContext) :
    DataRepositorySource {

    override suspend fun doDataRequest(): Flow<Resource<RiverData>> {
        return flow {
            emit(remoteRepository.requestData())
        }.flowOn(ioDispatcher)
    }
}
