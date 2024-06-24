package com.example.domain.common

sealed class Resource<out T> {

    data class Success<T>(val model: T): Resource<T>()

    data class Error<T>(val exception: Exception, val model: T? = null): Resource<T>()

    object Loading: Resource<Nothing>()

}