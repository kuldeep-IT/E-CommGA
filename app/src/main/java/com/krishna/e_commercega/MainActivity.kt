package com.krishna.e_commercega

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.krishna.e_commercega.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

   lateinit var binding: ActivityMainBinding

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    lateinit var promoParams: Bundle

    companion object{
        val itemJeggings = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, "SKU_123")
            putString(FirebaseAnalytics.Param.ITEM_NAME, "VICKS")
            putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "balm")
            putString(FirebaseAnalytics.Param.ITEM_VARIANT, "black")
            putString(FirebaseAnalytics.Param.ITEM_BRAND, "Google")
            putDouble(FirebaseAnalytics.Param.PRICE, 80.0)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics

        promoParams = Bundle().apply {
            putString(FirebaseAnalytics.Param.PROMOTION_ID, "SUMMER_FUN")
            putString(FirebaseAnalytics.Param.PROMOTION_NAME, "Ecomm Banner")
            putString(FirebaseAnalytics.Param.CREATIVE_NAME, "download.jpg")
            putString(FirebaseAnalytics.Param.CREATIVE_SLOT, "main_slot")
            putString(FirebaseAnalytics.Param.LOCATION_ID, "HERO_BANNER")
            putParcelableArray(FirebaseAnalytics.Param.ITEMS, arrayOf(itemJeggings))
        }

        // Promotion displayed
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION, promoParams)


        binding.imgButton.setOnClickListener(this)
        binding.rvBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgButton -> {
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION, promoParams)
                startActivity(Intent(this@MainActivity, ProductListActivity::class.java))
            }

            R.id.rvBtn -> {
                startActivity(Intent(this@MainActivity, ListItemsRV::class.java))
            }
        }
    }


}