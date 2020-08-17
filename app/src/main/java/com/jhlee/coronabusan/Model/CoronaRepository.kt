package com.jhlee.coronabusan.Model

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import com.jhlee.coronabusan.api.MaskAPI
import com.jhlee.coronabusan.api.NaverAPI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException


class CoronaRepository() {

    private val newsApi = NaverAPI.create()
    private val maskApi = MaskAPI.create()
    private var pharmacyList: ArrayList<PharmacyItems> = arrayListOf()

    private val busanURL = "http://www.busan.go.kr/corona19/index"
    private val koreaURL = "https://www.gg.go.kr/contents/contents.do?ciIdx=1150&menuId=2909"
    private val worldURL = "https://en.wikipedia.org/wiki/Template:COVID-19_pandemic_data"


    fun getPharmacy(): Observable<ArrayList<PharmacyItems>> = Observable.just(pharmacyList)
        .doOnNext { list ->
            list.add(
                PharmacyItems(
                    "강서구 보건소",
                    "051-970-3415",
                    "https://m.place.naver.com/hospital/36477562/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "금정구 보건소",
                    "051-519-5051",
                    "https://m.place.naver.com/hospital/11629456/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "기장군 보건소",
                    "051-709-4791",
                    "https://m.place.naver.com/hospital/12433892/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "남구 보건소",
                    "051-607-6460",
                    "https://m.place.naver.com/hospital/12166396/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "동구 보건소",
                    "051-440-6501",
                    "https://m.place.naver.com/hospital/12166397/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "동래구 보건소",
                    "051-555-4000",
                    "https://m.place.naver.com/hospital/11667956/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "부산진구 보건소",
                    "051-645-4000",
                    "https://m.place.naver.com/hospital/11667955/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "북구 보건소",
                    "051-309-4500",
                    "https://m.place.naver.com/hospital/11667949/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "사상구 보건소",
                    "051-310-4791",
                    "https://m.place.naver.com/hospital/19514661/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "사하구 보건소",
                    "051-220-5701",
                    "https://m.place.naver.com/hospital/11667953/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "서구 보건소",
                    "051-240-4791",
                    "https://m.place.naver.com/hospital/11667948/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "수영구 보건소",
                    "051-752-4000",
                    "https://m.place.naver.com/hospital/11667950/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "연제구 보건소",
                    "051-665-4781",
                    "https://m.place.naver.com/hospital/11668832/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "영도구 보건소",
                    "051-419-4901",
                    "https://m.place.naver.com/hospital/11667952/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "중구 보건소",
                    "051-600-4741",
                    "https://m.place.naver.com/hospital/11667946/location?entry=ple&subtab=location"
                )
            )
            list.add(
                PharmacyItems(
                    "해운대구 보건소",
                    "051-746-4000",
                    "https://m.place.naver.com/hospital/11667951/location?entry=ple&subtab=location"
                )
            )
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getNews(n: Int, str: String): Observable<ResultGetSearchNews> = newsApi
        .getSearchNews(str, 10, n)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getMaskLatlng(lat: Double, lng: Double): Observable<ResultGetMaskData>? = maskApi
        .getMaskLatlng(lat, lng, 4000)
        .onErrorReturn { t -> ResultGetMaskData(0, arrayListOf(MaskLatlon("","","","","",0.0,0.0,"",""))) }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getBusan(): MutableLiveData<ArrayList<String>> {
        var list = MutableLiveData<ArrayList<String>>()
        JsoupAsyncTask(busanURL, list, 0).execute()
        return list
    }

    fun getKorea(): MutableLiveData<ArrayList<String>> {
        var list = MutableLiveData<ArrayList<String>>()
        JsoupAsyncTask(koreaURL, list, 1).execute()
        return list
    }

    fun getWorld(): MutableLiveData<ArrayList<String>> {
        var list = MutableLiveData<ArrayList<String>>()
        JsoupAsyncTask(worldURL, list, 2).execute()
        return list
    }

    fun getBusanNum(): MutableLiveData<ArrayList<CoronaPeople>> {
        var list = MutableLiveData<ArrayList<CoronaPeople>>()
        //list.value.add()
        JsoupAsyncTask2(busanURL, list).execute()
        return list
    }
}

class JsoupAsyncTask(url: String, list: MutableLiveData<ArrayList<String>>, ck: Int) : AsyncTask<Void?, Void?, Void?>() {
    private var url = url
    private var list = list
    private var ck = ck
    private var templist = arrayListOf<String>()

    override fun doInBackground(vararg params: Void?): Void? {
        try {
            val doc: Document = Jsoup.connect(url).get()

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
                var titles: Elements = doc.select("tr.sorttop")
                System.out.println("title: " + titles.text().split(" "))
                templist.addAll(titles.text().split(" "))
                for(i in 0..2)
                    templist[i+1] = templist[i+1] + " 명"

                val doc: Document = Jsoup.connect("https://www.google.com/search?q=%EC%A0%84%EC%84%B8%EA%B3%84+%EC%BD%94%EB%A1%9C%EB%82%98+%ED%98%84%ED%99%A9%ED%8C%90&oq=%EC%A0%84%EC%84%B8%EA%B3%84+%EC%BD%94%EB%A1%9C%EB%82%98+%ED%98%84%ED%99%A9%ED%8C%90&aqs=chrome..69i57.438j0j7&sourceid=chrome&ie=UTF-8").get() // 세계 일일 코로나 확진자 수
                titles = doc.select("div.h5Hgwe")
                val trimStr = titles.text().substring(1, titles.text().indexOf(" ")) + " 명"
                templist.add(trimStr)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            templist = arrayListOf<String>("","","","","")
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        list.value = templist
    }
}

class JsoupAsyncTask2(url: String, list: MutableLiveData<ArrayList<CoronaPeople>>) : AsyncTask<Void?, Void?, Void?>() {
    private var htmlContentInStringFormat = ""
    private var url = url
    private var list = list
    private val templist = arrayListOf<CoronaPeople>()


    override fun doInBackground(vararg params: Void?): Void? {
        try {
            val doc: Document = Jsoup.connect(url).get()
            var temp = 0
            var data: Elements = doc.select("div.list_body li:nth-child(1)")

            for(j in 1..data.size) {
                val tempdata = CoronaPeople()
                for (i in 1..5) {
                    var element: Elements =
                        doc.select("div.list_body ul:nth-child(${j}) li:nth-child(${i})")
                    when(i) {
                        1 -> tempdata.name = element[0].text()
                        2 -> tempdata.route = element[0].text()
                        3 -> tempdata.num = element[0].text()
                        4 -> tempdata.hospital = element[0].text()
                        5 -> tempdata.date = element[0].text()
                    }
                }
                templist.add(tempdata)
            }
            //테스트1
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
