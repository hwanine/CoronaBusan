package com.jhlee.coronabusan.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jhlee.coronabusan.BoardViewModel

import com.jhlee.coronabusan.R
import com.jhlee.coronabusan.databinding.FragmentBoardBinding
import io.reactivex.Completable.timer
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.ThreadPoolExecutor
import kotlin.concurrent.timer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FragmentBoard : Fragment() {

    private lateinit var vm: BoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentBoardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)
        vm = ViewModelProvider(this).get(BoardViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_board, container, false)
        timer(period = 1000) {
            vm.getNowTime()
        }

        binding.boardViewModel = vm
        binding.lifecycleOwner = this
        return binding.root
    }


}
