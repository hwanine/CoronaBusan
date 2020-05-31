package com.jhlee.coronabusan.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jhlee.coronabusan.fragment.FragmentBoard
import com.jhlee.coronabusan.fragment.FragmentMap
import com.jhlee.coronabusan.fragment.FragmentSearch
import com.jhlee.coronabusan.fragment.FragmentTreat
import java.util.*


class VPAdapter(fm: FragmentManager?) :
    FragmentPagerAdapter(fm!!) {
    private val items: ArrayList<Fragment> = ArrayList()
    private val itext: ArrayList<String> = ArrayList()
    override fun getItem(position: Int): Fragment {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }

    init {
        items.add(FragmentSearch())
        items.add(FragmentBoard())
        items.add(FragmentTreat())
        items.add(FragmentMap())

        itext.add("관련 기사")
        itext.add("현황판")
        itext.add("대처법")
        itext.add("마스크 맵")
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return itext.get(position)
    }
}
