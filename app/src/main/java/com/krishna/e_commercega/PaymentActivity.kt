package com.krishna.e_commercega

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.krishna.e_commercega.MainActivity.Companion.itemJeggings
import com.krishna.e_commercega.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    lateinit var binding: ActivityPaymentBinding
    var productName: String = ""
    var address: String = ""
    var productPrice: Int = 0
    var qty: Int = 0

    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)

        firebaseAnalytics = Firebase.analytics

        title = "Payment Methods"

        var bundle: Bundle? = intent?.extras
        productName = bundle?.getString("PRO_NAME").toString()
        productPrice = bundle?.getInt("PRO_PRICE")!!
        qty = bundle?.getInt("PRO_QTY")!!
        address = bundle?.getString("ADDRESS")!!

        binding.btnPayment.setOnClickListener {

            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO) {
                param(FirebaseAnalytics.Param.PAYMENT_TYPE, "UPI")
                param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemJeggings))
            }

            var bundle = Bundle()
            bundle.putInt("PRO_QTY", qty)
            bundle.putString("PRO_NAME", productName)
            bundle.putString("ADDRESS", address)
            bundle.putInt("PRO_PRICE", productPrice)
            bundle.putString("PAYMENT", "UPI")
            sendToOrderSummary(bundle)
        }
    }

    private fun sendToOrderSummary(bundle: Bundle) {
        var intent = Intent(this@PaymentActivity, OrderSummaryActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}