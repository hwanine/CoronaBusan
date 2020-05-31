package com.jhlee.coronabusan.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.jhlee.coronabusan.CustomDialog.PharmacyDialog

import com.jhlee.coronabusan.R
import com.jhlee.coronabusan.CustomDialog.TreatDialog
import com.jhlee.coronabusan.DialogViewModel
import kotlinx.android.synthetic.main.fragment_treat.view.*

class FragmentTreat : Fragment() {
    private lateinit var vm: DialogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = ViewModelProvider(this).get(DialogViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_treat, container, false)

        view.card_view6.setOnClickListener {
            openCall("tel:1339")
        }
        view.card_view7.setOnClickListener {
            openCall("tel:051-120")
        }
        view.card_view1.setOnClickListener {
            openDlg(R.drawable.coronabusan1)
        }
        view.card_view2.setOnClickListener {
            openDlg(R.drawable.coronabusan2)
        }
        view.card_view3.setOnClickListener {
            openDlg(R.drawable.coronabusan3)
        }
        view.card_view4.setOnClickListener {
            openDlg(R.drawable.coronabusan4)
        }
        view.card_view5.setOnClickListener {
            val pharmacyDlg: View = layoutInflater.inflate(R.layout.list_dialog, null)
            val dlg =
                PharmacyDialog(pharmacyDlg, vm)
            dlg.show(childFragmentManager, "pharmacyDlg")
        }
        return view.rootView
    }

    fun openDlg(ck: Int) {
        val treatDlg: View = layoutInflater.inflate(R.layout.treat_dialog, null)
        val dlg = TreatDialog(treatDlg, ck)
        dlg.show(childFragmentManager, "treatDlg")
    }

    fun openCall(str: String) {
        var intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(str)
        startActivity(intent)
    }
}
