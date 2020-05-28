package com.jhlee.coronabusan.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object CustomBind {

        @JvmStatic
        @BindingAdapter("bind:verAdapter")
        fun searchAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        }

}
