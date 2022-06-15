package com.picpay.desafio.android.Data.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    const val MEDIA_TYPE = "text/plain"
    private const val SUCCESS = "Success"
    private const val FAILURE = "Failure"

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInteger(max: Int = 500): Int {
        return ThreadLocalRandom.current().nextInt(1, max + 1)
    }

    fun randomLong(): Long {
        return randomInteger().toLong()
    }

    fun randomBoolean(): Boolean {
        return Random().nextBoolean()
    }

    fun makeSuccessResponseBody(): Response<ResponseBody> {
        return Response.success(SUCCESS.toResponseBody(MEDIA_TYPE.toMediaTypeOrNull()))
    }

    fun makeHttpFailureResponseBody(): Response<ResponseBody> {
        return Response.error(404, FAILURE.toResponseBody(MEDIA_TYPE.toMediaTypeOrNull()))
    }
}