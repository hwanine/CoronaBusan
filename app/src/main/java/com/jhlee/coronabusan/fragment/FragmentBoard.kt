package com.jhlee.coronabusan.fragment

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
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
        val uri = "http://www.busan.go.kr/corona19/index#travelhist".toUri()
        vm = ViewModelProvider(this).get(BoardViewModel::class.java)
        clickListener(binding)

        var clickCheck = vm.clickPeople.value
        vm.clickPeople.observe(viewLifecycleOwner, androidx.lifecycle.Observer { clickPeople ->
            if(clickCheck != clickPeople) {
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
                clickCheck = -1
            }
        })

        timer(period = 1000) {
            vm.getNowTime()
        }
        binding.boardViewModel = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    fun clickListener(binding: FragmentBoardBinding) {
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
            val dlg = BoardDialog(boardDlg, vm)
            dlg.show(childFragmentManager, "boardDlg")
        }
    }

}
