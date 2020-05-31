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

data class ResultGetMaskData(
    var count: Int = 0,
    var stores: List<MaskLatlon>
)

data class MaskLatlon(
    var code: String = "",
    var created_at: String = "",
    var remain_stat: String = "",
    var stock_at: String = "",
    var addr: String = "",
    var lat: Double = 0.0,
    var lng: Double= 0.0,
    var name: String = "",
    var type: String = ""
)