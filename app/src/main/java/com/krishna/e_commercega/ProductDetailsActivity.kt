package com.krishna.e_commercega

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.krishna.e_commercega.MainActivity.Companion.itemJeggings
import com.krishna.e_commercega.databinding.ActivityProductDetailsBinding


/*
* Created By Kuldeep
* Date: 01/01/2023
* Last Modified: 02/01/2023
* Project Name: E-Commerce GA
* Description: The ProductDetailsActivity class is a demonstrate for the Product Details
* */

class ProductDetailsActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityProductDetailsBinding
    var qty = 0
    var productName: String = ""
    var productPrice: Int = 0
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    //itemJeggingsWishlist

        private lateinit var itemAddRemoveFromCart: Bundle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)

        title = "Product Details"
        firebaseAnalytics = Firebase.analytics

        var bundle: Bundle? = intent?.extras
        productName = bundle?.getString("PRO_NAME").toString()
        productPrice = bundle?.getInt("PRO_PRICE")!!
        Log.d("Result_data", "onCreate: ${productName + "  ::" + productPrice}")

        binding.tvProductName.text = productName.toString()
        binding.tvProductPrice.text = productPrice.toString()


        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM) {
            param(FirebaseAnalytics.Param.CURRENCY, "INR")
            param(FirebaseAnalytics.Param.VALUE, productPrice.toString())
            param(FirebaseAnalytics.Param.ITEM_NAME, productName)
            param(FirebaseAnalytics.Param.ITEMS, "Health Care")
//            param(FirebaseAnalytics.Param.QUANTITY,qty.toString())
            param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemJeggings))
        }

        binding.btnAddToCart.setOnClickListener(this)
        binding.btnMinus.setOnClickListener(this)
        binding.btnPlus.setOnClickListener(this)

    }

    /**
     * Calls the specified function [block] with `this` value as its argument and returns its result.
     */
    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.btnAddToCart -> {
//                startActivity(Intent(this@ProductDetailsActivity, ViewCart::class.java))
                var bundle = Bundle()
                bundle.putInt("PRO_QTY", qty)
                bundle.putString("PRO_NAME", productName)
                bundle.putInt("PRO_PRICE", productPrice)

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST) {
                    param(FirebaseAnalytics.Param.CURRENCY, "USD")
                    param(FirebaseAnalytics.Param.VALUE, qty * productPrice.toLong())
                    param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemAddRemoveFromCart))
                }

                sendToViewCart(bundle)
            }

            //Add or remove a product from a shopping cart
            R.id.btnMinus -> {
                if (qty > 0) {
                    qty -= 1
                } else {
                    qty = 0
                }

                addOrRemoveItemGA(qty)

                binding.tvQuantity.text = qty.toString()
            }
            R.id.btnPlus -> {
                qty += 1

                addOrRemoveItemGA(qty)

                binding.tvQuantity.text = qty.toString()
            }

        }

    }


    private fun addOrRemoveItemGA(qty: Int) {
        itemAddRemoveFromCart = Bundle(itemJeggings).apply {
            putLong(FirebaseAnalytics.Param.QUANTITY, qty.toLong())
            putInt(FirebaseAnalytics.Param.ITEM_CATEGORY2, productPrice)
        }
    }

    private fun sendToViewCart(bundle: Bundle) {
        var intent = Intent(this@ProductDetailsActivity, ViewCart::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }


}