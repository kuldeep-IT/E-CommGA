package com.krishna.e_commercega

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.krishna.e_commercega.adapter.CustomAdapter
import com.krishna.e_commercega.data.ItemsViewModel
import com.krishna.e_commercega.databinding.ActivityListItemsRvBinding

class ListItemsRV : AppCompatActivity() {

    lateinit var binding: ActivityListItemsRvBinding
    lateinit var linearLayoutManager: LinearLayoutManager
    var arrayBundle = ArrayList<Bundle>()

    var flag = 0

    var temp = 0

    var isFirstTime: Boolean = false
    var indexLastLog = true


    var data = ArrayList<ItemsViewModel>()

    lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_items_rv)

        isFirstTime = true

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        // this creates a vertical layout Manager

        firebaseAnalytics = Firebase.analytics

        // ArrayList of class ItemsViewModel


        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 0..49) {
            data.add(ItemsViewModel(R.drawable.ic_launcher_background, "Item " + i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        binding.recyclerview.apply {
            this.adapter = adapter
            layoutManager = linearLayoutManager
            addOnScrollListener(recyclerViewOnScrollListener)

        }

        /*binding.recyclerview.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(this@ListItemsRV)
            this.setOnRvScrollListener(customRVOnScrollListner)
        }*/

    }


    /*  private var customRVOnScrollListner: CustomRecyclerView.OnScrollListener = object : CustomRecyclerView.OnScrollListener() {
          override fun onGoUp() {

          }

          override fun onGoDown() {
              Log.d("CUSTOM_RV", "onGoDown: "+ "GA EVENT LOG")
          }
      }*/

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount: Int = linearLayoutManager.childCount /*- 1*/
                val totalItemCount: Int = linearLayoutManager.itemCount
                val firstVisibleItemPosition: Int =
                    linearLayoutManager.findFirstVisibleItemPosition()
                val lastItem = firstVisibleItemPosition + visibleItemCount - 2
                val lastIndexItem = firstVisibleItemPosition + visibleItemCount - 1


                Log.d("lastIndexItem", "Last Index::: ${lastIndexItem}" )


                Log.d(
                    "RecyclerView_Data",
                    "onScrolled: \n " + "visibleItemCount: ${visibleItemCount} \n" + "totalItemCount: ${totalItemCount} \n" + "firstVisibleItemPosition: ${firstVisibleItemPosition} \n" + "lastItem: ${lastItem}"
                )

                if (isFirstTime) {
                    isFirstTime = false
                    flag = lastItem
                    Log.d("GA_FIRED", "onScrolled:IF")

                    if (lastItem >= 0 && flag >= lastItem) {

//                        var item = arrayOf("abc1", "abc2", "abc3", "abc4")

                        for (i in 0..lastItem /*- 1*/) {

                            var bundle = Bundle().apply {
                                putString(FirebaseAnalytics.Param.ITEM_ID, data[i].text)
                                putString(FirebaseAnalytics.Param.ITEM_NAME, data[i].text)
                            }

                            arrayBundle.add(i,bundle)

                        }
                        Log.d("CREATED_ARRAY","array data::" + arrayBundle)

                        var viewItemListBundle = Bundle().apply {
                            putString(FirebaseAnalytics.Param.ITEM_LIST_ID,"list_1")
                            putString(FirebaseAnalytics.Param.ITEM_LIST_NAME,"FirstExecuteItem")
                            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS,arrayBundle)

                        }

                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, viewItemListBundle)

                        Log.d(
                            "GA_FIRED_COUNT",
                            "onScrolled: First Visible ${firstVisibleItemPosition} ${lastItem}"
                        )
                        Log.d("GA_FIRED", "onScrolled:ELSEEE::: GA REPORTS GET HERE:: FIRST TIME")
                    }
                } else if (flag < lastItem) {

                    arrayBundle.clear()
                    Log.d("GA_FIRED", "onScrolled:MAIN ELSEEE::: GA REPORTS GET HERE")
                    Log.d(
                        "GA_FIRED_COUNT",
                        "onScrolled: First Visible ${firstVisibleItemPosition} ${lastItem}"
                    )

                    for (i in flag+1..lastItem) {

                        Log.d("LAST_INDEX", "onScrolled: First Index ${flag} lastIndex ${lastItem}")

                        Log.d("I_AM_WATCHING_U", "onScrolled: ${i}")

                        var bundle = Bundle().apply {
                            putString(FirebaseAnalytics.Param.ITEM_ID, data[i].text)
                            putString(FirebaseAnalytics.Param.ITEM_NAME, data[i].text)
                        }

                        arrayBundle.add(bundle)

                    }
                    Log.d("CREATED_ARRAY","array data::" + arrayBundle)

                    var viewItemListBundle = Bundle().apply {
                        putString(FirebaseAnalytics.Param.ITEM_LIST_ID,"list_1")
                        putString(FirebaseAnalytics.Param.ITEM_LIST_NAME,"SecondScrollItem")
                        putParcelableArrayList(FirebaseAnalytics.Param.ITEMS,arrayBundle)
                    }

                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, viewItemListBundle)

                    //flag updated here
                    flag = lastItem
                } else if(data.lastIndex == lastIndexItem) {

                    if (indexLastLog == true) {

                        indexLastLog = false
                        arrayBundle.clear()

                        var bundle = Bundle().apply {
                            putString(FirebaseAnalytics.Param.ITEM_ID, data[data.lastIndex].text)
                            putString(FirebaseAnalytics.Param.ITEM_NAME, data[data.lastIndex].text)
                        }
                        arrayBundle.add(bundle)

                        var viewItemListBundle = Bundle().apply {
                            putString(FirebaseAnalytics.Param.ITEM_LIST_ID, "last_index")
                            putString(FirebaseAnalytics.Param.ITEM_LIST_NAME, "SecondScrollItem")
                            putParcelableArrayList(FirebaseAnalytics.Param.ITEMS, arrayBundle)
                        }

                        firebaseAnalytics.logEvent(
                            FirebaseAnalytics.Event.VIEW_ITEM_LIST,
                            viewItemListBundle
                        )
                    }
                }
            }
        }

    val itemJeggingsWithIndex = Bundle().apply {
        putString(FirebaseAnalytics.Param.ITEM_ID, "SKU_123")
    }
}