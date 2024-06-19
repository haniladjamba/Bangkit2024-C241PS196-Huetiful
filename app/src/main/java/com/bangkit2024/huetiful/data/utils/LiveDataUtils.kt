package com.bangkit2024.huetiful.data.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
suspend fun <T> LiveData<T>.awaitFirstValue(): T? = withContext(Dispatchers.Main) {
    suspendCancellableCoroutine { cont ->
        val observer = object : Observer<T> {
            override fun onChanged(value: T) {
                value?.let {
                    cont.resume(it)
                    this@awaitFirstValue.removeObserver(this)
                }
            }
        }
        this@awaitFirstValue.observeForever(observer)
        cont.invokeOnCancellation { this@awaitFirstValue.removeObserver(observer) }
    }
}