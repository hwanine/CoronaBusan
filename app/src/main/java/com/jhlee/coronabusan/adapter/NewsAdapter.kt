package com.jhlee.coronabusan.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jhlee.coronabusan.CoronaViewModel
import com.jhlee.coronabusan.NewsItems
import com.jhlee.coronabusan.R
import com.jhlee.coronabusan.databinding.NewsItemviewBinding
import kotlinx.android.synthetic.main.news_itemview.view.*


class NewsAdapter(viewModel: CoronaViewModel) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val viewModel = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val binding: NewsItemviewBinding = NewsItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int = viewModel.getNewsItem().size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, position)
    }

    inner class ViewHolder(binding: NewsItemviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding = binding

        fun bind(viewModel: CoronaViewModel, pos: Int) {
            binding.pos = pos
            binding.coronaViewModel = viewModel
            binding.executePendingBindings()
        }
    }
}
