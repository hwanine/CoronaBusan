package com.jhlee.coronabusan.ViewModel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhlee.coronabusan.Model.CoronaPeople
import com.jhlee.coronabusan.Model.CoronaRepository
import com.jhlee.coronabusan.Model.PharmacyItems
import com.jhlee.coronabusan.adapter.BoardAdapter

class BoardDialogViewModel(application: Application): AndroidViewModel(application){
    /*private val repo : CoronaRepository =
        CoronaRepository(application)
    var peopleList: MutableLiveData<ArrayList<CoronaPeople>> = MutableLiveData<ArrayList<CoronaPeople>>()
    private var pharmacyAdapter =
        BoardAdapter(this)

    init {
        getBusanNum()
    }
    @SuppressLint("CheckResult")
    fun getBusanNum() {
        repo.getBusanNum()
    }

    fun init(recyclerView: RecyclerView, adapter: BoardAdapter) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }

    fun getAdapter(): BoardAdapter = pharmacyAdapter*/
}