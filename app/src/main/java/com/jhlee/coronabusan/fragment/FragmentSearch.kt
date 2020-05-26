package com.jhlee.coronabusan.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhlee.coronabusan.CoronaViewModel
import com.jhlee.coronabusan.R
import com.jhlee.coronabusan.databinding.FragmentSearchBinding

class FragmentSearch : Fragment() {

    private lateinit var vm: CoronaViewModel
    private val SEARCH_BUSAN = "코로나 부산 확진"
    private val SEARCH_NORMAL = "코로나 확진"
    private val SEARCH_WORLD = "코로나 세계 확진"
    private var myUri: Uri = "".toUri()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        vm = ViewModelProvider(this).get(CoronaViewModel::class.java)
        vm.viewInit(binding.fragmentRecycleView)
        vm.getNews(SEARCH_BUSAN)
        clickListener(binding)
        addEvent(binding)

        vm.uri?.observe(viewLifecycleOwner, Observer { uri ->
            if(myUri != uri) {
                myUri = uri
                val intent = Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        })

        binding.coronaViewModel = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    fun clickListener(binding: FragmentSearchBinding) {
        binding.searchBusan.setOnClickListener {
            binding.searchBusan.setTypeface(null, Typeface.BOLD)
            binding.searchBusan.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
            binding.searchEvery.setTypeface(null, Typeface.NORMAL)
            binding.searchEvery.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
            binding.searchWorld.setTypeface(null, Typeface.NORMAL)
            binding.searchWorld.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
            refresh()
            vm.getNews(SEARCH_BUSAN)
        }
        binding.searchEvery.setOnClickListener {
            binding.searchBusan.setTypeface(null, Typeface.NORMAL)
            binding.searchBusan.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
            binding.searchEvery.setTypeface(null, Typeface.BOLD)
            binding.searchEvery.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
            binding.searchWorld.setTypeface(null, Typeface.NORMAL)
            binding.searchWorld.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
            refresh()
            vm.getNews(SEARCH_NORMAL)
        }
        binding.searchWorld.setOnClickListener {
            binding.searchBusan.setTypeface(null, Typeface.NORMAL)
            binding.searchBusan.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
            binding.searchEvery.setTypeface(null, Typeface.NORMAL)
            binding.searchEvery.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
            binding.searchWorld.setTypeface(null, Typeface.BOLD)
            binding.searchWorld.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
            refresh()
            vm.getNews(SEARCH_WORLD)
        }
        binding.searchRefrash.setOnClickListener {
            refresh()
            getTypeNews()
        }
    }

    fun refresh() {
        vm.newsItem.clear()
        vm.n = 1
        vm.max = 10
    }

    fun getTypeNews() {
        val str = vm.searchType
        when(str) {
            "부산" -> vm.getNews(SEARCH_BUSAN)
            "" -> vm.getNews(SEARCH_NORMAL)
            "세계" -> vm.getNews(SEARCH_WORLD)
        }
    }


    fun addEvent(binding: FragmentSearchBinding) {
        val onScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                val total = layoutManager?.itemCount
                val lastVisible = layoutManager?.findLastCompletelyVisibleItemPosition()

                if (lastVisible != null && total != null && total >= 10) {
                    if(lastVisible >= total -1) {
                        getTypeNews()
                    }
                }
            }

        }
        binding.fragmentRecycleView.setOnScrollListener(onScrollListener)
    }
}
