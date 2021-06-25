package com.example.booked_me.presentation

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booked_me.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CartActivity : AppCompatActivity(),
    View.OnClickListener {

    private lateinit var bottomSheet : LinearLayout
    private lateinit var gestureLayout : LinearLayout
    private lateinit var hargaBayar : LinearLayout
    private lateinit var sheetBehavior : BottomSheetBehavior<LinearLayout>

    private lateinit var bottomSheetArrow : ImageView
    private lateinit var sumBook : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        bottomSheet = findViewById(R.id.bottom_sheet)
        gestureLayout = findViewById(R.id.gesture_layout)
        bottomSheetArrow = findViewById(R.id.bottom_sheet_arrow)
        sumBook = findViewById(R.id.sum_book)
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        hargaBayar = findViewById(R.id.harga_bayar)

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
                        hargaBayar.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomSheetArrow.setImageResource(R.drawable.ic_swipe)
                        hargaBayar.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        bottomSheetArrow.setImageResource(R.drawable.ic_swipe)
                        hargaBayar.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

        })

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.add_sum -> {
                var count = sumBook.text.toString().trim()
                var numberBook = Integer.parseInt(count)
                if (numberBook >= 9) return
                numberBook++
                sumBook.text = numberBook.toString()
            }
            R.id.minus_sum -> {
                var count = sumBook.text.toString().trim()
                var numberBook = Integer.parseInt(count)
                if (numberBook == 1) return
                numberBook--
                sumBook.text = numberBook.toString()
            }
        }
    }


}