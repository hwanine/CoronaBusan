package com.jhlee.coronabusan.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter {

    fun searchAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        val linearLayoutManager =
            LinearLayoutManager(recyclerView.getContext())
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL)
        recyclerView.setLayoutManager(linearLayoutManager)
        recyclerView.setAdapter(adapter)
    }
}