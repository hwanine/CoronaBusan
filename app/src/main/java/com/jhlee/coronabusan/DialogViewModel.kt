package com.jhlee.coronabusan

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhlee.coronabusan.Model.CoronaRepository
import com.jhlee.coronabusan.Model.PharmacyItems
import com.jhlee.coronabusan.adapter.PharmacyAdapter

class DialogViewModel(application: Application): AndroidViewModel(application){
    private val repo : CoronaRepository =
        CoronaRepository(application)
    var pharmacyList: MutableLiveData<ArrayList<PharmacyItems>> = MutableLiveData<ArrayList<PharmacyItems>>()
    private var pharmacyAdapter =
        PharmacyAdapter(this)
    var click: MutableLiveData<Int> = MutableLiveData<Int>(-1)
    var clickMap: MutableLiveData<Int> = MutableLiveData<Int>(-1)

    init {
        getPharmacy()
    }
    @SuppressLint("CheckResult")
    fun getPharmacy() {
        repo.getPharmacy().subscribe {
                list -> pharmacyList.value = list
        }
    }

    fun init(recyclerView: RecyclerView, adapter: PharmacyAdapter) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }

    fun getAdapter(): PharmacyAdapter = pharmacyAdapter
}