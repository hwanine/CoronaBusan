package com.jhlee.coronabusan

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.widget.Toast
import androidx.core.text.toSpanned
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhlee.coronabusan.adapter.NewsAdapter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CoronaViewModel(application: Application): AndroidViewModel(application){
    private var newsResult: MutableLiveData<ResultGetSearchNews> = MutableLiveData()
    var newsItem: ArrayList<NewsItems> = arrayListOf()
    private val repo : CoronaRepository = CoronaRepository(application)
    private var newsAdapter = NewsAdapter(this)
    var n = 1
    var max = 10

    var searchType: String = ""
    var uri: MutableLiveData<Uri> = MutableLiveData<Uri>()

    @SuppressLint("CheckResult")
    fun getNews(str: String) {

        searchType = when {
            str.contains("부산") -> {
                "부산"
            }
            str.contains("세계") -> {
                "세계"
            }
            else -> {
                ""
            }
        }

        //newsResult = repo.getNews()
        repo.getNews(n, str).subscribe(
            { ResultGetSearchNews ->
                for(i in ArrayList(ResultGetSearchNews.items).indices) {
                    if(ResultGetSearchNews.items[i].title.contains(searchType)) {
                        Log.d("왔어?","왔")
                        newsItem.add(ResultGetSearchNews.items[i])
                        newsAdapter.notifyDataSetChanged()
                    }
                        n++
                        if(newsItem.size < max && i == ResultGetSearchNews.items.size - 1) {
                            getNews(str)
                        } else if(newsItem.size >= max) {
                            max += 10
                            break
                        }
                    }
                }

            , { throwable -> Log.d("Error!"," ") })

    }

    fun getTitle(pos: Int): Spanned {
        try {
            return Html.fromHtml(newsItem.get(pos).title)
        } catch(e: Exception) {
            return "".toSpanned()
        }
    }

    fun getDate(pos: Int): String {
        try {
            return dateFormat(newsItem.get(pos).pubDate)
        } catch(e: Exception) {
            return ""
        }
    }

    fun getNewsItem(): List<NewsItems> {
        return newsItem
    }

    fun toUri(pos: Int) {
        uri.setValue(Uri.parse(newsItem.get(pos).link))
    }

    fun viewInit(recyclerView: RecyclerView) {
        recyclerView.adapter = newsAdapter
        recyclerView.layoutManager = LinearLayoutManager(getApplication())
    }

    fun dateFormat(str: String): String {

        val formatterCal = SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.US)
        val date: Date = formatterCal.parse(str) // all done

        val formatterStr = SimpleDateFormat("yyyy년 MM월 dd일 (E) / HH:mm", Locale.KOREAN)
        val strDate = formatterStr.format(date)
        return strDate
    }

}


