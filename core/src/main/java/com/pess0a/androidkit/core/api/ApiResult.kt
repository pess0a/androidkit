package com.pess0a.androidkit.core.api

/** Encapsulate the HTTP result details. */
data class ApiResult<T>(
    val body: T? = null,
    val code: Int? = null,
    val isSuccessful: Boolean,
    val headers: HashSet<Pair<String, String>> = hashSetOf(),
    val throwable: Throwable? = null
) {

    /** Try to get the api result response body, otherwise throw an [IllegalArgumentException]. */
    fun getBodyOrThrow(): T {
        val exception = throwable ?: IllegalArgumentException("Could not retrieve body ApiResult response.")
        return body ?: throw exception
    }

    /** Throw an [throwable] when is not [isSuccessful]. */
    fun throwIfNotSuccess() {
        if (!isSuccessful) {
            throw throwable ?: IllegalArgumentException("Api Result is not succeed.")
        }
    }

    /** Invoke [onResult] when [isSuccessful] is true. */
    fun onSuccess(onResult: (T) -> Unit): ApiResult<T> {
        if (isSuccessful) {
            onResult(getBodyOrThrow())
        }
        return this
    }

    /** Invoke [onResult] informing an [throwable] when [isSuccessful] is not true. */
    fun onError(onResult: (Throwable?) -> Unit): ApiResult<T> {
        if (!isSuccessful) {
            onResult(throwable)
        }
        return this
    }
}