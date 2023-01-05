package com.krishna.e_commercega

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.krishna.e_commercega.databinding.ActivityViewCartBinding

class ViewCart : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityViewCartBinding
    var productName: String = ""
    var productPrice: Int = 0
    var qty: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_cart)

        setTitle("View Cart")

        var bundle: Bundle? = intent?.extras
        productName = bundle?.getString("PRO_NAME").toString()
        productPrice = bundle?.getInt("PRO_PRICE")!!
        qty = bundle?.getInt("PRO_QTY")!!

        binding.tvNameView.text = productName.toString()
        binding.tvQuantityView.text = qty.toString()
        binding.tvPriceView.text = "${productPrice} x ${qty}"


        binding.btnContinueView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnContinueView -> {

                var bundle = Bundle()
                bundle.putInt("PRO_QTY", qty)
                bundle.putString("PRO_NAME", productName)
                bundle.putInt("PRO_PRICE", productPrice)

                sendToBeginCheck(bundle)
            }
        }
    }

    private fun sendToBeginCheck(bundle: Bundle) {
        var intent = Intent(this@ViewCart, BeginCheckoutActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}