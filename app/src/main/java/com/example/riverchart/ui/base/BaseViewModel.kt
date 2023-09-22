package com.example.riverchart.ui.base

import androidx.lifecycle.ViewModel
import com.example.riverchart.usecase.errors.ErrorManager
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    lateinit var errorManager: ErrorManager
}
