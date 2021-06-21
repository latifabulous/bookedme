package com.example.booked_me.order.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.booked_me.R
import com.example.booked_me.order.InProgressFragment
import com.example.booked_me.order.PastOrderFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object{

        val TAB_TITLE = intArrayOf(
            R.string.in_progress,
            R.string.past_order
        )
    }

    override fun getItemCount(): Int {
        return TAB_TITLE.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> InProgressFragment()
            1 -> PastOrderFragment()
            else -> Fragment()
        }
    }

}