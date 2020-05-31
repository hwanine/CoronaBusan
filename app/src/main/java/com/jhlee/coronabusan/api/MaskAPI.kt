package com.jhlee.coronabusan.api

import com.jhlee.coronabusan.Model.ResultGetMaskData
import com.jhlee.coronabusan.Model.ResultGetSearchNews
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MaskAPI {
    @GET("storesByGeo/json")
    fun getMaskLatlng(
        @Query("lat") lat: Double,
        @Query("lng") display: Double,
        @Query("m") start: Int
    ): Observable<ResultGetMaskData>

    companion object {
        private const val BASE_URL_MASK_API = "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/"

        fun create(): MaskAPI {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val headerInterceptor = Interceptor {
                val request = it.request()
                    .newBuilder()
                    .build()
                return@Interceptor it.proceed(request)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL_MASK_API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MaskAPI::class.java)
        }
    }
}