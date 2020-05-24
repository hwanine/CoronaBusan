package com.jhlee.coronabusan

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jhlee.coronabusan.api.NaverAPI
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoronaRepository(application: Application) {

    private val api = NaverAPI.create()
    private var newsData: MutableLiveData<ResultGetSearchNews> = MutableLiveData()


    /*fun getNews(): MutableLiveData<ResultGetSearchNews>{
        api.getSearchNews("코로나 확진 부산").enqueue(object : Callback<ResultGetSearchNews> {
            override fun onResponse(
                call: Call<ResultGetSearchNews>,
                response: Response<ResultGetSearchNews>
            ) {
                newsData.setValue(response.body())
                //eturnNews(newsData)
                println("이거걱aaaaa")
                println("이값음" + newsData.value)
                //response.body()
            }

            override fun onFailure(call: Call<ResultGetSearchNews>, t: Throwable) {
                newsData.setValue(null)
            }

        })
        println("이거걱bbbbb")
        return newsData
    }*/


    fun getNews(n: Int): Observable<ResultGetSearchNews> = api
        .getSearchNews("코로나 부산", 100, n)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}