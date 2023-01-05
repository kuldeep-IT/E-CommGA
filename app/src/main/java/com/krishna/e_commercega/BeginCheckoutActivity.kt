package com.krishna.e_commercega

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.krishna.e_commercega.MainActivity.Companion.itemJeggings
import com.krishna.e_commercega.databinding.ActivityBeginCheckoutBinding

class BeginCheckoutActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityBeginCheckoutBinding
    var productName: String = ""
    var address: String = ""
    var productPrice: Int = 0
    var qty: Int = 0
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_begin_checkout)
        firebaseAnalytics = Firebase.analytics

        var bundle: Bundle? = intent?.extras
        productName = bundle?.getString("PRO_NAME").toString()
        productPrice = bundle?.getInt("PRO_PRICE")!!
        qty = bundle?.getInt("PRO_QTY")!!

        address = "123, abc cuidshbf"
        binding.btnAddress.setOnClickListener(this)

        setTitle("Begin Checkout")
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnAddress -> {

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT) {
                    param(FirebaseAnalytics.Param.CURRENCY, "INR")
                    param(FirebaseAnalytics.Param.VALUE, (qty * productPrice).toLong())
                    param(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN")
                    param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemJeggings))
                }

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_SHIPPING_INFO) {
                    param(FirebaseAnalytics.Param.CURRENCY, "INR")
                    param(FirebaseAnalytics.Param.VALUE,(qty * productPrice).toLong())
                    param(FirebaseAnalytics.Param.COUPON, "ABC123")
                    param(FirebaseAnalytics.Param.SHIPPING_TIER, "Ground")
                    param(FirebaseAnalytics.Param.SHIPPING, address)
                    param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemJeggings))
                }

                var bundle = Bundle()
                bundle.putInt("PRO_QTY", qty)
                bundle.putString("PRO_NAME", productName)
                bundle.putString("ADDRESS", address)
                bundle.putInt("PRO_PRICE", productPrice)

                sendToPayment(bundle)
            }
        }
    }

    private fun sendToPayment(bundle: Bundle) {
        var intent = Intent(this@BeginCheckoutActivity, PaymentActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}