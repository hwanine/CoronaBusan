package com.jhlee.coronabusan.fragment

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jhlee.coronabusan.BoardViewModel
import com.jhlee.coronabusan.CustomDialog.BoardDialog

import com.jhlee.coronabusan.R
import com.jhlee.coronabusan.databinding.FragmentBoardBinding
import io.reactivex.Completable.timer
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.ThreadPoolExecutor
import kotlin.concurrent.timer


class FragmentBoard : Fragment() {

    private lateinit var vm: BoardViewModel
    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentBoardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)
        vm = ViewModelProvider(this).get(BoardViewModel::class.java)
        timer(period = 1000) {
            vm.getNowTime()
        }
        binding.boardRefrash.setOnClickListener {
            if(SystemClock.elapsedRealtime() - mLastClickTime > 3000) {
                vm.getBoard()
                mLastClickTime = SystemClock.elapsedRealtime()
            } else {
                Toast.makeText(context, "데이터를 불러오고 있습니다. 잠시만 기다려 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        binding.boardPeople.setOnClickListener {
            val boardDlg: View = layoutInflater.inflate(R.layout.listboard_dialog, null)
            val dlg = BoardDialog(boardDlg)
            dlg.show(childFragmentManager, "boardDlg")
        }
        binding.boardViewModel = vm
        binding.lifecycleOwner = this
        return binding.root
    }


}
