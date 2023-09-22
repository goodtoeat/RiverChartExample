package com.example.riverchart.usecase.errors

import com.example.riverchart.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
