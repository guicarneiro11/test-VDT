package com.guicarneirodev.wayairlines.domain.model

sealed class ResponseData <out T> {
    data class Success<T>(val data: T): ResponseData<T>()
    data class Error(val code: Int? = null, val message: String? = null): ResponseData<Nothing>()
    data object Loading: ResponseData<Nothing>()
}