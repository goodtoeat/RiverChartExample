package com.example.riverchart.data.remote

import com.example.riverchart.data.Resource
import com.example.riverchart.data.remote.service.APIService
import com.example.riverchart.dto.RiverData
import com.example.riverchart.error.NETWORK_ERROR
import com.example.riverchart.error.NO_INTERNET_CONNECTION
import com.example.riverchart.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


class RemoteData @Inject
constructor(private val serviceGenerator: ServiceGenerator, private val networkConnectivity: NetworkConnectivity) :
    RemoteDataSource {
    override suspend fun requestData(): Resource<RiverData> {
        val dataService = serviceGenerator.createService(APIService::class.java)
        return when (val response = processCall(dataService::loadData)) {
            is RiverData -> {
                Resource.Success(data = response)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}
