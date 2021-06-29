package com.gg.exercisetimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tab_layout.addTab(tab_layout.newTab().setText("Timer"))
        tab_layout.addTab(tab_layout.newTab().setText("Calendar"))
        tab_layout.addTab(tab_layout.newTab().setText("Total"))

        val pagerAdapter = PagerAdapter(supportFragmentManager,3)
        view_pager.adapter=pagerAdapter

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                view_pager.currentItem=tab!!.position
            }
        })
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
    }
}
class PagerAdapter(
        fragmentManager: FragmentManager,
        val tabCount: Int
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return timer()
            }
            1->{
                return Calendar()
            }
            else -> {
                return Total()
            }
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

}