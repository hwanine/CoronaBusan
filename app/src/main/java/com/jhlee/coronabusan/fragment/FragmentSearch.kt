package com.jhlee.coronabusan.fragment

import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.Typeface
import android.os.Bundle
import android.os.SystemClock
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jhlee.coronabusan.R
import com.jhlee.coronabusan.SearchViewModel
import com.jhlee.coronabusan.databinding.FragmentSearchBinding

class FragmentSearch : Fragment() {

    private lateinit var vm: SearchViewModel
    private val SEARCH_BUSAN = "코로나 부산 확진"
    private val SEARCH_NORMAL = "코로나 국내 확진"
    private val SEARCH_WORLD = "코로나 세계 확진"

    private var lastRendererTime: Long = SystemClock.elapsedRealtime()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        vm = ViewModelProvider(this).get(SearchViewModel::class.java)
        var myUri = vm.uri.value
            searchType(binding)
        clickListener(binding)
        addEvent(binding)

        vm.uri?.observe(viewLifecycleOwner, Observer { uri ->
            if(myUri != uri) {
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                myUri = "".toUri()
            }
        })

        binding.searchViewModel = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    fun clickListener(binding: FragmentSearchBinding) {
        binding.searchBusan.setOnClickListener {
            reset()
            vm.getNews(SEARCH_BUSAN)
        }
        binding.searchEvery.setOnClickListener {
            reset()
            vm.getNews(SEARCH_NORMAL)
        }
        binding.searchWorld.setOnClickListener {
            reset()
            vm.getNews(SEARCH_WORLD)
        }
        binding.searchRefrash.setOnClickListener {
            reset()
            getTypeNews()
        }
    }

    fun reset() {
        vm.newsItem.clear()
        vm.getAdapter().notifyDataSetChanged()
        vm.n = 1
        vm.max = 10
    }

    fun getTypeNews() {
        val str = vm.searchType.value
        when(str) {
            "부산" -> vm.getNews(SEARCH_BUSAN)
            "국내" -> vm.getNews(SEARCH_NORMAL)
            "세계" -> vm.getNews(SEARCH_WORLD)
        }
    }

    fun searchType(binding: FragmentSearchBinding) {
        vm.searchType.observe(viewLifecycleOwner, Observer { searchType ->
            if (vm.searchType.value.equals("부산")) {
                binding.searchBusan.setTypeface(null, Typeface.BOLD)
                binding.searchBusan.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
                binding.searchEvery.setTypeface(null, Typeface.NORMAL)
                binding.searchEvery.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
                binding.searchWorld.setTypeface(null, Typeface.NORMAL)
                binding.searchWorld.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
            } else if (vm.searchType.value.equals("세계")) {
                binding.searchBusan.setTypeface(null, Typeface.NORMAL)
                binding.searchBusan.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
                binding.searchEvery.setTypeface(null, Typeface.NORMAL)
                binding.searchEvery.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
                binding.searchWorld.setTypeface(null, Typeface.BOLD)
                binding.searchWorld.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
            } else {
                binding.searchBusan.setTypeface(null, Typeface.NORMAL)
                binding.searchBusan.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
                binding.searchEvery.setTypeface(null, Typeface.BOLD)
                binding.searchEvery.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22F)
                binding.searchWorld.setTypeface(null, Typeface.NORMAL)
                binding.searchWorld.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18F)
            }
        })
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
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //binding.appbar.setExpanded(true,true)
            }

        }
        binding.fragmentRecycleView.setOnScrollListener(onScrollListener)
    }
}
