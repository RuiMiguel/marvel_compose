package com.codelabs.marvelcompose.base.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val exceptionHandler: CoroutineContext =
        CoroutineExceptionHandler { _, throwable ->
            Log.e("BaseViewModel CoroutineExceptionHandler", throwable.message, throwable)
        },
) : ViewModel() {

    val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext + dispatcher + exceptionHandler
}