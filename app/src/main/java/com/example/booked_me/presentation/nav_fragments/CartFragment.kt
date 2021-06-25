package com.example.booked_me.presentation.nav_fragments

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booked_me.presentation.CheckoutDetailFragment
import com.example.booked_me.R
import com.example.booked_me.data.Transaksi
import com.example.booked_me.presentation.nav_fragments.adapter.CartAdapter
import com.example.booked_me.utils.Preference
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import org.w3c.dom.Text

class CartFragment : Fragment() {

    private lateinit var bottomSheet : LinearLayout
    private lateinit var gestureLayout : LinearLayout
    private lateinit var hargaBayar : LinearLayout
    private lateinit var sheetBehavior : BottomSheetBehavior<LinearLayout>

    private lateinit var bottomSheetArrow : ImageView
    private lateinit var harga : TextView
    private lateinit var totalHarga : TextView
    private lateinit var subOder : TextView
    private lateinit var subHarga : TextView

    private lateinit var btnCheckout : Button
    private lateinit var btnCheckoutSwipe : Button
    private lateinit var btnDelete : Button

    private lateinit var rvCart : RecyclerView

    private lateinit var database : DatabaseReference
    private lateinit var preference: Preference

    private lateinit var listCart : ArrayList<Transaksi>
    private var totalBayar : Int = 0

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preference = Preference(view.context)
        database = FirebaseDatabase.getInstance().getReference("user")

        listCart = ArrayList()

        subHarga = view.findViewById(R.id.sub_harga)
        harga = view.findViewById(R.id.harga)
        totalHarga = view.findViewById(R.id.total_harga)
        subOder = view.findViewById(R.id.sub_order)
        btnDelete = view.findViewById(R.id.btn_delete)

        bottomSheet = view.findViewById(R.id.bottom_sheet)
        gestureLayout = view.findViewById(R.id.gesture_layout)
        bottomSheetArrow = view.findViewById(R.id.bottom_sheet_arrow)
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        hargaBayar = view.findViewById(R.id.harga_bayar)
        btnCheckout = view.findViewById(R.id.btn_checkout)
        btnCheckoutSwipe = view.findViewById(R.id.btn_checkout_swipe)
        rvCart = view.findViewById(R.id.rv_cart)

        btnDelete.setOnClickListener{
            deleteBookmark()
        }

        getDataCart()
        rvCart.layoutManager = LinearLayoutManager(view.context)
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

    private fun getDataCart() {
        database.child(preference.getValue("username").toString()).child("cart_user")
                .addValueEventListener( object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        listCart.clear()
                        for (carts in snapshot.children){
                            val cart = carts.getValue(Transaksi::class.java)
                            listCart.add(cart!!)

                            totalBayar += cart.price!!.toInt()
                        }

                        val adminFee = 2000
                        var price : Int = adminFee + totalBayar


                        subHarga.text = "Rp. $totalBayar"
                        subOder.text = "Rp. $adminFee"
                        totalHarga.text = "Rp. $price"
                        harga.text = "Rp. $price"

                        rvCart.adapter = CartAdapter(listCart)



                        btnCheckout.setOnClickListener {
                            val checkoutFragment = CheckoutDetailFragment()

                            val bundle = Bundle()
                            bundle.putString("EXTRA_PRICE", price.toString())
                            bundle.putString("EXTRA_SUB_PRICE", totalBayar.toString())
                            Log.d("CartFragment", totalBayar.toString())
                            checkoutFragment.arguments = bundle

                            fragmentManager?.beginTransaction()
                                    ?.replace(R.id.fl_wrapper, checkoutFragment, CheckoutDetailFragment::class.java.simpleName)
                                    ?.commit()
                        }
                        btnCheckoutSwipe.setOnClickListener {
                            val checkoutFragment = CheckoutDetailFragment()
                            val bundle = Bundle()
                            bundle.putString("EXTRA_PRICE", price.toString())
                            bundle.putString("EXTRA_SUB_PRICE", totalBayar.toString())
                            checkoutFragment.arguments = bundle

                            fragmentManager?.beginTransaction()
                                    ?.replace(R.id.fl_wrapper, checkoutFragment, CheckoutDetailFragment::class.java.simpleName)
                                    ?.commit()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("CartFragment", error.message)
                    }
                })
    }

    private fun deleteBookmark() {
        val alertDialog = AlertDialog.Builder(view?.context)
        alertDialog.setTitle("Are you sure to delete this?")
        alertDialog.setPositiveButton("Yes") { dialog, which ->
            database.child(preference.getValue("username").toString())
                    .child("cart_user").removeValue()
        }
        alertDialog.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.create().show()
    }
}