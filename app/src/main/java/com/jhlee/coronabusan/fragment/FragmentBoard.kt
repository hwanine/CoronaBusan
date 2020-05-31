package com.jhlee.coronabusan.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FirebaseMessaging
import com.jhlee.coronabusan.BoardViewModel
import com.jhlee.coronabusan.CustomDialog.BoardDialog

import com.jhlee.coronabusan.R
import com.jhlee.coronabusan.SharePreferences
import com.jhlee.coronabusan.databinding.FragmentBoardBinding
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
        val binding: FragmentBoardBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_board, container, false)
        val uri = "http://www.busan.go.kr/corona19/index#travelhist".toUri()
        vm = ViewModelProvider(this).get(BoardViewModel::class.java)
        clickListener(binding)

        var clickCheck = vm.clickPeople.value
        vm.clickPeople.observe(viewLifecycleOwner, androidx.lifecycle.Observer { clickPeople ->
            if (clickCheck != clickPeople) {
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
            if (SystemClock.elapsedRealtime() - mLastClickTime > 3000) {
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

        binding.alertFcm.setOnClickListener {
            alertDialog()
        }
    }

    fun alertDialog() {
        Toast.makeText(context, "부산시 코로나 확진자 발표 알림 수신 여부를 선택하세요", Toast.LENGTH_SHORT).show()
        val selectitem = arrayOf<String>("알림 수신 허용", "알림 수신 거부")
        var select = SharePreferences.prefs.getAlert("alert", 0)
        val dlg: AlertDialog.Builder = AlertDialog.Builder(context)
        dlg.setTitle("코로나19 알림 설정")
        dlg.setSingleChoiceItems(selectitem, select) { dialog, i ->
            when (i) {
                0 -> select = 0
                1 -> select = 1
            }
        }
        dlg.setIcon(R.mipmap.ic_launcher_round)
        dlg.setPositiveButton("확인") { _, _ ->
            SharePreferences.prefs.setAlert("alert", select)
            if (select == 0) {
                FirebaseMessaging.getInstance().subscribeToTopic("alert")
                    .addOnCompleteListener { task ->
                    }
                Toast.makeText(context, "알림 수신을 동의하셨습니다.", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic("alert")
                    .addOnCompleteListener { task ->
                    }
                Toast.makeText(context, "알림 수신을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
            }

        }
        dlg.setNegativeButton("취소") { _, _ ->
        }
        dlg.show()
    }
}