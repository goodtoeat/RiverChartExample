package com.example.riverchart.usecase.errors

import com.example.riverchart.error.mapper.ErrorMapper
import com.example.riverchart.error.Error
import javax.inject.Inject

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}
