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
import com.krishna.e_commercega.databinding.ActivityProductListBinding

/*
* Created By Kuldeep
* Date: 01/01/2023
* Last Modified: 02/01/2023
* Project Name: E-Commerce GA
* Description: The ProductListActivity class is a demonstrate for the Product List
* */

class ProductListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityProductListBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        firebaseAnalytics = Firebase.analytics

        setTitle("Product List")

        //set all the product with index
        val itemJeggingsWithIndex = Bundle(itemJeggings).apply {
            putLong(FirebaseAnalytics.Param.INDEX, 1)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST) {
            param(FirebaseAnalytics.Param.ITEM_LIST_ID, "list_1")
            param(FirebaseAnalytics.Param.ITEM_LIST_NAME, "product_list")
            param(FirebaseAnalytics.Param.ITEMS,
                arrayOf(itemJeggingsWithIndex))
        }


        binding.layoutVicks.setOnClickListener(this)
        binding.layoutChocolate.setOnClickListener(this)
        binding.layoutInhaler.setOnClickListener(this)
    }


    /**
     * Function Purpose: Navigate one class to another class. via https://stackoverflow.com/questions/2091465/how-do-i-pass-data-between-activities-in-android-application.
     * @param bundle -> pass extra data with activity
     * @return void
     */
    private fun sendToProductDetails(bundle: Bundle) {
        var intent = Intent(this@ProductListActivity, ProductDetailsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.layoutVicks -> {

                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                    param(FirebaseAnalytics.Param.ITEM_LIST_ID, "list_1")
                    param(FirebaseAnalytics.Param.ITEM_LIST_NAME, "product_list")
                    param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemJeggings))
                }

                var bundle = Bundle()
                bundle.putString("PRO_NAME", "Vicks")
                bundle.putInt("PRO_PRICE", 80)
                sendToProductDetails(bundle)
            }
        }
    }
}