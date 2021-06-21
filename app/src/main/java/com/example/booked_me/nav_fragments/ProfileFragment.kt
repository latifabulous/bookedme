
package com.example.booked_me.nav_fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.booked_me.CheckoutDetailFragment
import com.example.booked_me.R
import com.example.booked_me.order.OrderActivity
import com.example.booked_me.profile.SettingProfileActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ProfileFragment : Fragment() {

    private lateinit var bottomSheet : LinearLayout
    private lateinit var gestureLayout : LinearLayout
    private lateinit var sheetBehavior : BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetArrow : ImageView

    private lateinit var llOrder: LinearLayout
    private lateinit var llFeed: LinearLayout
    private lateinit var llSetting: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheet = view.findViewById(R.id.bottom_sheet)
        gestureLayout = view.findViewById(R.id.gesture_layout)
        bottomSheetArrow = view.findViewById(R.id.bottom_sheet_arrow)
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)

        llFeed = view.findViewById(R.id.ll_myfeed)
        llOrder = view.findViewById(R.id.ll_order)
        llSetting = view.findViewById(R.id.ll_setting)

        llOrder.setOnClickListener {
            val orderIntent = Intent(activity, OrderActivity::class.java)
            startActivity(orderIntent)
        }

        llSetting.setOnClickListener {
            val settingIntent = Intent(activity, SettingProfileActivity::class.java)
            startActivity(settingIntent)
        }

        gestureLayout.viewTreeObserver.also {
            it.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            gestureLayout.viewTreeObserver.removeGlobalOnLayoutListener(this)
                        } else {
                            gestureLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                        //                int width = bottomSheetLayout.getMeasuredWidth();
                        val height = gestureLayout.measuredHeight
                        sheetBehavior.peekHeight = height
                    }
                })
        }

        sheetBehavior.isHideable = false


        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottomSheetArrow.setImageResource(R.drawable.ic_swipe)
//                        hargaBayar.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomSheetArrow.setImageResource(R.drawable.ic_swipe)
//                        hargaBayar.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        bottomSheetArrow.setImageResource(R.drawable.ic_swipe)
//                        hargaBayar.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

        })
    }
}