package com.picpay.desafio.android.Data.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.Data.PicPayService
import com.picpay.desafio.android.Data.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiModule {
    fun providesRetrofit(): PicPayService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PicPayService::class.java)

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val gson: Gson by lazy { GsonBuilder().create() }
}