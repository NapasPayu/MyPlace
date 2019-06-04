package com.napas.myplace.ui.main

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding2.view.clicks
import com.napas.myplace.R
import com.napas.myplace.base.BaseActivity
import com.napas.myplace.ui.location.LocationActivity
import com.napas.myplace.ui.places.PlacesViewModel
import com.napas.myplace.ui.places.PlacesViewModelFactory
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    companion object {
        private const val REQUEST_CODE_SELECT_LOCATION = 101
    }


    @Inject
    lateinit var mPlacesViewModelFactory: PlacesViewModelFactory
    @Inject
    lateinit var mSectionsPagerAdapter: SectionsPagerAdapter

    private val mPlaceListViewModel by lazy {
        ViewModelProviders.of(this, mPlacesViewModelFactory).get(PlacesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = view_pager.apply {
            adapter = mSectionsPagerAdapter
        }
        tabs.setupWithViewPager(viewPager)

        fab.clicks()
            .subscribeBy {
                startActivityForResult(Intent(this, LocationActivity::class.java), REQUEST_CODE_SELECT_LOCATION)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_LOCATION && resultCode == Activity.RESULT_OK) {
            val selectedAddress = data?.getParcelableExtra<Address>(LocationActivity.ARG_ADDRESS)
            selectedAddress?.let {
                view_pager.currentItem = 0
                mPlaceListViewModel.loadNearbyPlaces(it.latitude, it.longitude)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}