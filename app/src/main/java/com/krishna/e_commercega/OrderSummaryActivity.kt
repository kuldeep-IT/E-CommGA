package com.krishna.e_commercega

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.krishna.e_commercega.MainActivity.Companion.itemJeggings
import com.krishna.e_commercega.databinding.ActivityOrderSummaryBinding

class OrderSummaryActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrderSummaryBinding
    var productName: String = ""
    var address: String = ""
    var payment: String = ""
    var productPrice: Int = 0
    var qty: Int = 0

    lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_summary)

        firebaseAnalytics = Firebase.analytics

        var bundle: Bundle? = intent?.extras
        productName = bundle?.getString("PRO_NAME").toString()
        productPrice = bundle?.getInt("PRO_PRICE")!!
        qty = bundle?.getInt("PRO_QTY")!!
        address = bundle?.getString("ADDRESS")!!
        payment = bundle?.getString("PAYMENT")!!


        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE) {
            param(FirebaseAnalytics.Param.TRANSACTION_ID, "T12345")
            param(FirebaseAnalytics.Param.AFFILIATION, "FaceBook Ads")
            param(FirebaseAnalytics.Param.CURRENCY, "INR")
            param(FirebaseAnalytics.Param.VALUE, (qty * productPrice).toLong())
            param(FirebaseAnalytics.Param.TAX, 20.58)
            param(FirebaseAnalytics.Param.SHIPPING, 50.34)
            param(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN")
            param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemJeggings))
        }

        Log.d("ORDER_SUMM_FINAL", "onCreate: ${productName + " " +productPrice + " "+ productPrice + " "+qty+" "+address+" "+payment}")

        binding.tv2.text = " ${productName + " \n Base price: " +productPrice + " \n Total price: "+ productPrice * qty + " \n Qty: "+qty+"  \n Address: "+address+"  \n Payment: "+payment}"
    }



}