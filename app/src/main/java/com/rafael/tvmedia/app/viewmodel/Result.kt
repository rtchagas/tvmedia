package com.rafael.tvmedia.app.viewmodel

import timber.log.Timber

sealed class Result<out T : Any> {

    data class Success<T : Any>(val data: T) : Result<T>()

    data class Error(val error: Throwable) : Result<Nothing>()

}

fun <T : Any> resultOf(value: T): Result<T> = Result.Success(value)

fun <T : Any> resultOf(value: Exception): Result<T> = Result.Error(value)

inline fun <T : Any> resultOf(block: () -> T): Result<T> {
    return try {
        Result.Success(block.invoke())
    } catch (e: Exception) {
        Timber.w(e)
        Result.Error(e)
    }
}
