package com.jhlee.coronabusan

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhlee.coronabusan.Model.CoronaPeople
import com.jhlee.coronabusan.Model.CoronaRepository
import com.jhlee.coronabusan.adapter.BoardAdapter
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
    var peopleList: MutableLiveData<ArrayList<CoronaPeople>> = MutableLiveData<ArrayList<CoronaPeople>>()
    var clickPeople: MutableLiveData<Int> = MutableLiveData<Int>(-1)
    var checkGetPeople: Int = 0

    private var boardAdapter =
        BoardAdapter(this)

    init {
        getBoard()
        getBusanNum()
    }
    @SuppressLint("CheckResult")
    fun getBoard() {
        val temp = arrayListOf<String>("","","","","")
        busanList.value = temp
        koreaList.value = temp
        worldList.value = temp

        busanList = repo.getBusan()
        koreaList = repo.getKorea()
        worldList = repo.getWorld()
    }

    fun getNowTime() {
        val time = sdfFormat.format(Date(System.currentTimeMillis()))
        Handler(Looper.getMainLooper()).post { nowTimer.value = time }
    }

    @SuppressLint("CheckResult")
    fun getBusanNum() {
        peopleList = repo.getBusanNum()
    }

    fun init(recyclerView: RecyclerView, adapter: BoardAdapter) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }

    fun getAdapter(): BoardAdapter = boardAdapter

}