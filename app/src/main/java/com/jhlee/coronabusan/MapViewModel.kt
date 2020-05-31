package com.jhlee.coronabusan

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.jhlee.coronabusan.Model.CoronaRepository
import com.jhlee.coronabusan.Model.MaskLatlon

import java.util.*

class MapViewModel(application: Application): AndroidViewModel(application) {

    private val repo: CoronaRepository =
        CoronaRepository(application)
    var maskLatlngList: MutableLiveData<ArrayList<MaskLatlon>> =
        MutableLiveData<ArrayList<MaskLatlon>>()
    var List: ArrayList<MaskLatlon> = arrayListOf()

    @SuppressLint("CheckResult")
    fun getMaskLatlng(lat: Double, lng: Double) {
        List.clear()
        maskLatlngList.value = arrayListOf(MaskLatlon("","","","","",0.0,0.0,"",""))
        repo.getMaskLatlng(lat, lng).subscribe { ResultGetMaskData ->
            for (i in ArrayList(ResultGetMaskData.stores).indices) {
                if(ResultGetMaskData.stores[i].remain_stat != "break") {
                    List.add(ResultGetMaskData.stores[i])
                    maskLatlngList.value = List
                    Log.d("값값2", maskLatlngList.value?.size.toString())
                }
            }
        }

    }
}