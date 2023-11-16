package com.dado.utils


sealed class ResourceState <T> {

    class Loading<T> : ResourceState<T>()
    data class Success<T> (val data: T) : ResourceState<T>()
    data class Error<T> (val error: String) : ResourceState<T>()
    data class Offline<T> (val error: String) : ResourceState<T>()
}
