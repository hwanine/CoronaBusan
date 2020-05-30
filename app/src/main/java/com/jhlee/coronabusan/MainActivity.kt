package com.jhlee.coronabusan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jhlee.coronabusan.R
import com.jhlee.coronabusan.adapter.VPAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vpAdapter: VPAdapter =
            VPAdapter(supportFragmentManager)
        viewpager.adapter = vpAdapter
        tab.setupWithViewPager(viewpager)
    }
}
