package com.example.booked_me.presentation.tab_fragments.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.booked_me.R
import com.example.booked_me.data.Book
import com.example.booked_me.presentation.tab_fragments.OwnerFragment
import com.example.booked_me.presentation.tab_fragments.ReviewFragment
import com.example.booked_me.presentation.tab_fragments.SummaryFragment

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

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val buku : Book)
    : FragmentStateAdapter(fragmentActivity) {

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
            0 -> {
                val bundle = Bundle()
                val summaryFragment = SummaryFragment()
                bundle.putString("EXTRA_DATA", buku.deskripsi)
                summaryFragment.arguments = bundle
                summaryFragment
            }
            1 -> {
                val bundle = Bundle()
                val ownerFragment = OwnerFragment()
                bundle.putString("EXTRA_DATA", buku.userInput)
                ownerFragment.arguments = bundle
                ownerFragment
            }
            2 -> {
                val bundle = Bundle()
                var reviewFragment = ReviewFragment()
                bundle.putString("EXTRA_DATA", buku.rating)
                reviewFragment.arguments = bundle
                reviewFragment
            }
            else -> Fragment()
        }
    }

}