package com.jhlee.coronabusan.api

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

interface CoronaAPI {
    @GET("getCovid19SidoInfStateJson")
    fun getCorona(
        @Query("ServiceKey") query: String,
        @Query("pageNo") display: Int? = null,
        @Query("numOfRows") start: Int? = null
    ): Observable<ResultGetSearchNews>

    companion object {
        private const val BASE_URL_CORONA_API = "http://openapi.data.go.kr/openapi/service/rest/Covid19/"
        private const val SECRET_KEY = "qU%2FRcD2liz0yuptI0vDQhZ4PqpY5LqGACqvUxwBaz2F%2FfDgUVMtdDUiVeyg6jeKDQyLPxQYgmOMQ2XIi%2FdgNUw%3D%3D"


        fun create(): CoronaAPI {
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
                .baseUrl(BASE_URL_CORONA_API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CoronaAPI::class.java)
        }
    }
}