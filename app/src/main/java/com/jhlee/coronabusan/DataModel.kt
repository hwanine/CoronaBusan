package com.jhlee.coronabusan

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