package com.jhlee.coronabusan.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jhlee.coronabusan.R
import com.jhlee.coronabusan.TreatDialog
import kotlinx.android.synthetic.main.fragment_treat.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class FragmentTreat : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        intent.data = Uri.parse("tel:051-120")
        startActivity(intent)
    }

}
