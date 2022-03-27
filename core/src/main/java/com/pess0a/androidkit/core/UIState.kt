package com.pess0a.androidkit.core

sealed class UIState<out E, out S> {
    object Loading : UIState<Nothing, Nothing>()

    data class Error<out E>(val error: E) : UIState<E, Nothing>()

    data class Success<out S>(val value: S) : UIState<Nothing, S>()

    fun onSuccess(onResult: (S) -> Unit): UIState<E, S> {
        if (this is Success) {
            onResult(this.value)
        }
        return this
    }

    fun onError(onResult: (E) -> Unit): UIState<E, S> {
        if (this is Error) {
            onResult(this.error)
        }
        return this
    }

    fun onLoading(onResult: (Boolean) -> Unit): UIState<E, S> {
        onResult(this is Loading)
        return this
    }
}