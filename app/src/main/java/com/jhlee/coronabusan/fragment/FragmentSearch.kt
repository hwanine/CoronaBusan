package com.jhlee.coronabusan.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jhlee.coronabusan.CoronaViewModel
import com.jhlee.coronabusan.R
import com.jhlee.coronabusan.databinding.FragmentSearchBinding

class FragmentSearch : Fragment() {

    private lateinit var vm: CoronaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        vm = ViewModelProvider(this).get(CoronaViewModel::class.java)
        vm.viewInit(binding.fragmentRecycleView)
        vm.getNews()
        vm.uri?.observe(viewLifecycleOwner, Observer { uri ->
            val intent = Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        })

        // Inflate the layout for this fragment

        /*binding.fragmentRecycleView.adapter = newsAdapter
        binding.fragmentRecycleView.layoutManager = LinearLayoutManager(context)
        newsAdapter.setList(listOf(NewsItems("11","22,","33","44")))*/


        binding.coronaViewModel = vm
        binding.lifecycleOwner = this
        return binding.root
    }
}
