package com.jhlee.coronabusan.CustomDialog

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jhlee.coronabusan.BoardViewModel
import com.jhlee.coronabusan.DialogViewModel
import com.jhlee.coronabusan.ViewModel.BoardDialogViewModel
import kotlinx.android.synthetic.main.list_dialog.view.*
import kotlinx.android.synthetic.main.listboard_dialog.view.*

class BoardDialog(v: View, vm: BoardViewModel): DialogFragment() {
    private val v = v
    private val vm = vm

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val maindlgBuilder: androidx.appcompat.app.AlertDialog.Builder =
            androidx.appcompat.app.AlertDialog.Builder(    // 메인 다이얼로그
                context!!
            )
        maindlgBuilder.setView(v)

        val dlg = maindlgBuilder.create()
        v.board_people_ok.setOnClickListener {
            dlg.cancel()
        }
        vm.init(v.board_RecycleView, vm.getAdapter())
        vm.peopleList.observe(this, Observer { list ->
            vm.peopleList.value?.size
            vm.getAdapter().addItem(vm.peopleList.value!!)
        })

        /*vm.click.observe(this, Observer { click ->
            if(clickCheck != click) {
                var intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${vm.pharmacyList.value?.get(click)?.pnum}")
                startActivity(intent)
                clickCheck = -1
            }
        })
        vm.clickMap.observe(this, Observer { clickMap ->
            if(clickCheckMap != clickMap) {
                val intent = Intent(Intent.ACTION_VIEW,
                    vm.pharmacyList.value?.get(clickMap)?.link?.toUri()
                )
                startActivity(intent)
                clickCheckMap = -1
            }
        })*/
        return dlg
    }

}
