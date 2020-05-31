package com.jhlee.coronabusan.CustomDialog

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.jhlee.coronabusan.DialogViewModel
import kotlinx.android.synthetic.main.list_dialog.view.*

class PharmacyDialog(v: View, vm: DialogViewModel): DialogFragment() {
    private val v = v
    private val vm= vm

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var clickCheck = vm.click.value
        var clickCheckMap = vm.clickMap.value

        val maindlgBuilder: androidx.appcompat.app.AlertDialog.Builder =
            androidx.appcompat.app.AlertDialog.Builder(    // 메인 다이얼로그
                context!!
            )
        maindlgBuilder.setView(v)
        val dlg = maindlgBuilder.create()
        v.pharmacy_ok.setOnClickListener {
            dlg.cancel()
        }
        vm.init(v.pharmacy_RecycleView, vm.getAdapter())
        vm.pharmacyList.observe(this, Observer { list ->
           vm.getAdapter().addItem(vm.pharmacyList.value!!)
        })

        vm.click.observe(this, Observer { click ->
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
        })
        return dlg
    }

}
