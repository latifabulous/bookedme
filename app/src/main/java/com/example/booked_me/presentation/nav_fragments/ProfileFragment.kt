
package com.example.booked_me.presentation.nav_fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.booked_me.R
import com.example.booked_me.data.User
import com.example.booked_me.databinding.FragmentProfileBinding
import com.example.booked_me.presentation.feeds.MyFeedsActivity
import com.example.booked_me.presentation.login_register.LoginActivity
import com.example.booked_me.presentation.order.OrderActivity
import com.example.booked_me.presentation.profile.SettingProfileActivity
import com.example.booked_me.utils.Preference
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private lateinit var bottomSheet : LinearLayout
    private lateinit var gestureLayout : LinearLayout
    private lateinit var sheetBehavior : BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetArrow : ImageView

    private lateinit var llOrder: LinearLayout
    private lateinit var llFeed: LinearLayout
    private lateinit var llSetting: LinearLayout

    private lateinit var btnLogout : LinearLayout

    private lateinit var binding : FragmentProfileBinding
    private lateinit var database : DatabaseReference
    private lateinit var pref : Preference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = Preference(requireContext())
        database = FirebaseDatabase.getInstance().getReference("user")

        btnLogout = view.findViewById(R.id.btn_logout)
        bottomSheet = view.findViewById(R.id.bottom_sheet)
        gestureLayout = view.findViewById(R.id.gesture_layout)
        bottomSheetArrow = view.findViewById(R.id.bottom_sheet_arrow)
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)

        llFeed = view.findViewById(R.id.ll_myfeed)
        llOrder = view.findViewById(R.id.ll_order)
        llSetting = view.findViewById(R.id.ll_setting)

        getData()

        btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            activity?.finish()
            view.context.startActivity(Intent(view.context, LoginActivity::class.java))
            Log.d("ProfileFragment", "Logout Success")
        }

        llOrder.setOnClickListener {
            val orderIntent = Intent(activity, OrderActivity::class.java)
            startActivity(orderIntent)
        }

        llFeed.setOnClickListener {
            val feedIntent = Intent(activity, MyFeedsActivity::class.java)
            startActivity(feedIntent)
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

    private fun getData() {
        val username = pref.getValue("username")
        database.child(username.toString()).addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)

                with(binding){
                    Glide.with(requireContext())
                            .load(user?.photo)
                            .circleCrop()
                            .into(imgUserPp)

                    tvStore.setText(user?.store)
                    tvUsername.setText(user?.username)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}