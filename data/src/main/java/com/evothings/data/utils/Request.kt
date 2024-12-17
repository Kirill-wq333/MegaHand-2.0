package com.evothings.data.utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal object InvalidResponseException : Exception()

internal suspend inline fun <T, R> getAndTransform(
    transform: T.() -> R,
    block: () -> Call<T>
): Result<R> = block().awaitResult().mapCatching(transform)

internal suspend fun <T> Call<T>.awaitResult(): Result<T> =
    suspendCoroutine { continuation ->
        this.enqueue(
            object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resume(Result.failure(t))
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val result = if (response.isSuccessful)
                        Result.success(response.body()!!)
                    else
                        Result.failure(InvalidResponseException)
                    continuation.resume(result)
                }
            }
        )
    }
