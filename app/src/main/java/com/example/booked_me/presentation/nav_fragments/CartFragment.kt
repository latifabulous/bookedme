package com.example.booked_me.presentation.nav_fragments

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
import com.example.booked_me.presentation.CheckoutDetailFragment
import com.example.booked_me.R
import com.google.android.material.bottomsheet.BottomSheetBehavior

class CartFragment : Fragment(), View.OnClickListener {

    private lateinit var bottomSheet : LinearLayout
    private lateinit var gestureLayout : LinearLayout
    private lateinit var hargaBayar : LinearLayout
    private lateinit var sheetBehavior : BottomSheetBehavior<LinearLayout>

    private lateinit var addBook : ImageView
    private lateinit var minusBook : ImageView
    private lateinit var bottomSheetArrow : ImageView
    private lateinit var sumBook : TextView

    private lateinit var btnCheckout : Button
    private lateinit var btnCheckoutSwipe : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomSheet = view.findViewById(R.id.bottom_sheet)
        gestureLayout = view.findViewById(R.id.gesture_layout)
        addBook = view.findViewById(R.id.add_sum)
        minusBook = view.findViewById(R.id.minus_sum)
        bottomSheetArrow = view.findViewById(R.id.bottom_sheet_arrow)
        sumBook = view.findViewById(R.id.sum_book)
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        hargaBayar = view.findViewById(R.id.harga_bayar)
        btnCheckout = view.findViewById(R.id.btn_checkout)
        btnCheckoutSwipe = view.findViewById(R.id.btn_checkout_swipe)

        btnCheckout.setOnClickListener(this)
        btnCheckoutSwipe.setOnClickListener(this)

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

        addBook.setOnClickListener(this)
        minusBook.setOnClickListener(this)

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
            R.id.btn_checkout -> {
                val checkoutDetailFragment = CheckoutDetailFragment()

                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.fl_wrapper, checkoutDetailFragment, CheckoutDetailFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
            R.id.btn_checkout_swipe -> {
                val checkoutDetailFragment = CheckoutDetailFragment()

                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.fl_wrapper, checkoutDetailFragment, CheckoutDetailFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }
}