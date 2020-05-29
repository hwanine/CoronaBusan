package com.jhlee.coronabusan.Model

data class ResultGetSearchNews(

    var start: Int = 0,
    var display: Int = 0,
    var items: List<NewsItems>
)

data class NewsItems(
    var title: String = "",
    var link: String = "",
    var description: String = "",
    var pubDate: String = ""
)

data class PharmacyItems(
    var title: String = "",
    var pnum: String = "",
    var link: String = ""
)

data class CoronaPeople(
    var name: String = "",
    var route: String = "",
    var num: String = "",
    var hospital: String = "",
    var date: String = ""
)