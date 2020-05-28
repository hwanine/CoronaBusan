package com.jhlee.coronabusan

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import com.jhlee.coronabusan.api.NaverAPI
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException


class CoronaRepository(application: Application) {

    private val api = NaverAPI.create()
    private var newsData: MutableLiveData<ResultGetSearchNews> = MutableLiveData()

    private val busanURL = "http://www.busan.go.kr/corona19/index"
    private val koreaURL = "https://www.gg.go.kr/contents/contents.do?ciIdx=1150&menuId=2909"
    private val worldURL = "https://news.google.com/covid19/map?hl=ko&gl=KR&ceid=KR:ko"

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


    fun getNews(n: Int, str: String): Observable<ResultGetSearchNews> = api
        .getSearchNews(str, 10, n)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getBusan(): MutableLiveData<ArrayList<String>>{
        var list = MutableLiveData<ArrayList<String>>()
        JsoupAsyncTask(busanURL, list, 0).execute()
        return list
    }

    fun getKorea(): MutableLiveData<ArrayList<String>>{
        var list = MutableLiveData<ArrayList<String>>()
        JsoupAsyncTask(koreaURL, list, 1).execute()
        return list
    }

    fun getWorld(): MutableLiveData<ArrayList<String>>{
        var list = MutableLiveData<ArrayList<String>>()
        //list.value.add()
        JsoupAsyncTask(worldURL, list, 2).execute()
        return list
    }

/*     fun getBusan2(): Single<ArrayList<String>> {
        return Single.fromObservable(
            Observable.create {
                val busanList: ArrayList<String> = ArrayList()
                val doc: Document =
                    Jsoup.connect(busanURL)
                        .get() // Base Url
                for (i in 2..6) {
                    val contentElements: Elements =
                        doc.select("span.item$i") // title, link
                    val titles: Elements = doc.select("span.item$i")
                    val index = titles.text().indexOf("명")
                    val str = titles.text().substring(0, index) + " 명"
                    busanList.add(str)
                }
                it.onNext(busanList)
                it.onComplete()
            }
        )
    }*/
}

class JsoupAsyncTask(url: String, list: MutableLiveData<ArrayList<String>>, ck: Int) : AsyncTask<Void?, Void?, Void?>() {
    private var htmlContentInStringFormat = ""
    private var url = url
    private var list = list
    private var ck = ck
    private val templist = arrayListOf<String>()

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        try {
            val doc: Document = Jsoup.connect(url).get()

            //테스트1
            if(ck == 0) {
                for (i in 2..6) {
                    val titles: Elements = doc.select("span.item$i")
                    val index = titles.text().indexOf("명")
                    val str = titles.text().substring(0, index) + " 명"
                    templist.add(str)
                }
            } else if(ck == 1) {
                val tempArray = arrayOf("strong#a-total","strong#a-increase","strong#a-release","strong#a-isolate","strong#a-dead")
                for (i in 0..4) {
                    val titles: Elements = doc.select(tempArray[i])
                    templist.add(titles.text() + " 명")

                }
            } else {
                var titles: Elements = doc.select("div.UvMayb")
                System.out.println("title: " + titles.text().split(" "))
                templist.addAll(titles.text().split(" "))
                for(i in 0..2)
                    templist[i] = templist[i] + " 명"

                val doc: Document = Jsoup.connect("https://www.google.com/search?q=%EC%A0%84%EC%84%B8%EA%B3%84+%EC%BD%94%EB%A1%9C%EB%82%98+%ED%98%84%ED%99%A9%ED%8C%90&oq=%EC%A0%84%EC%84%B8%EA%B3%84+%EC%BD%94%EB%A1%9C%EB%82%98+%ED%98%84%ED%99%A9%ED%8C%90&aqs=chrome..69i57.438j0j7&sourceid=chrome&ie=UTF-8").get() // 세계 일일 코로나 확진자 수
                titles = doc.select("div.h5Hgwe")
                val trimStr = titles.text().substring(1, titles.text().indexOf(" ")) + " 명"
                templist.add(trimStr)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            templist.clear()
            list.value?.clear()
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        list.value = templist
    }

}
