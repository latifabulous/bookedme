package com.example.booked_me.tab_fragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.booked_me.R
import com.example.booked_me.tab_fragments.OwnerFragment
import com.example.booked_me.tab_fragments.ReviewFragment
import com.example.booked_me.tab_fragments.SummaryFragment

//class ViewPagerAdapter(supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//
//    private val mFragmentList = ArrayList<Fragment>()
//    private val mFragmentTitleList = ArrayList<String>()
//
//    override fun getCount(): Int {
//        return mFragmentList.size
//    }
//
//    override fun getItem(position: Int): Fragment {
//        return mFragmentList[position]
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return mFragmentTitleList[position]
//    }
//
//    fun addFragment(fragment: Fragment, title : String) {
//        mFragmentList.add(fragment)
//        mFragmentTitleList.add(title)
//    }
//}

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object{

        val TAB_TITLE = intArrayOf(
            R.string.summary,
            R.string.owner,
            R.string.review
        )
    }

    override fun getItemCount(): Int {
        return TAB_TITLE.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SummaryFragment()
            1 -> OwnerFragment()
            2 -> ReviewFragment()
            else -> Fragment()
        }
    }

}