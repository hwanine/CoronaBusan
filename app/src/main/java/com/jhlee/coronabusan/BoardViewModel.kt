package com.jhlee.coronabusan

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

class BoardViewModel(application: Application): AndroidViewModel(application){
    private val repo : CoronaRepository =
        CoronaRepository(application)
    @SuppressLint("SimpleDateFormat")
    val sdfFormat: SimpleDateFormat = SimpleDateFormat("MM월 dd일 (E) HH:mm:ss")
    var nowTimer: MutableLiveData<String> = MutableLiveData<String>("")

    var busanList: MutableLiveData<ArrayList<String>> = MutableLiveData<ArrayList<String>>()
    var koreaList: MutableLiveData<ArrayList<String>> = MutableLiveData<ArrayList<String>>()
    var worldList: MutableLiveData<ArrayList<String>> = MutableLiveData<ArrayList<String>>()

    init {
        getBoard()
    }
    @SuppressLint("CheckResult")
    fun getBoard() {
        busanList = repo.getBusan()
        koreaList = repo.getKorea()
        worldList = repo.getWorld()
    }

    fun getNowTime() {
        val time = sdfFormat.format(Date(System.currentTimeMillis()))
        Handler(Looper.getMainLooper()).post { nowTimer.value = time }
    }

}