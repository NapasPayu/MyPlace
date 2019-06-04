package com.napas.myplace.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.jakewharton.rxbinding2.view.clicks
import com.napas.myplace.R
import com.napas.myplace.base.BaseActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_location.*
import javax.inject.Inject

class LocationActivity : BaseActivity() {

    companion object {
        private const val REQUEST_CODE_ENABLE_GPS = 201
        private const val DEFAULT_ZOOM = 15f
        const val ARG_ADDRESS = "address"
    }

    @Inject
    lateinit var mRxPermission: RxPermissions
    @Inject
    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    @Inject
    lateinit var mLocationRequestTask: Task<LocationSettingsResponse>
    @Inject
    lateinit var mLocationRequest: LocationRequest
    @Inject
    lateinit var mGeocoder: Geocoder

    private var isDetectLocationFirstTime: Boolean = true
    private var mMap: GoogleMap? = null
    private var mAddress: Address? = null
    private val locationCallBack: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (isDetectLocationFirstTime) {
                    locationResult?.locations?.firstOrNull()?.let {
                        val currentLatLng = LatLng(it.latitude, it.longitude)
                        mMap?.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                currentLatLng,
                                DEFAULT_ZOOM
                            )
                        )
                        isDetectLocationFirstTime = false
                        mFusedLocationProviderClient.removeLocationUpdates(locationCallBack)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        initMap()

        subscriptions += card_view_address.clicks()
            .subscribeBy {
                mAddress?.let {
                    setResult(Activity.RESULT_OK, Intent().apply { putExtra(ARG_ADDRESS, it) })
                    finish()
                }
            }
    }

    @SuppressLint("MissingPermission")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_ENABLE_GPS -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallBack, null)
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun initMap() {
        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            map?.let {
                mMap = it
                subscriptions += mRxPermission
                    .request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribeBy(
                        onNext = {
                            mMap?.apply {
                                if (it) {
                                    enableGPS()
                                    setOnCameraIdleListener {
                                        this.clear()
                                        showAddress(cameraPosition.target)
                                    }
                                }
                            }
                        }
                    )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableGPS() {
        mLocationRequestTask.addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)
                mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallBack, null)
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        val resolvable = e as ResolvableApiException
                        startIntentSenderForResult(
                            resolvable.resolution.intentSender,
                            REQUEST_CODE_ENABLE_GPS,
                            null,
                            0,
                            0,
                            0,
                            null
                        )
                    }
                }
            }
        }
    }

    private fun showAddress(latLng: LatLng) {
        mAddress = mGeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).firstOrNull()
        text_view_address.text = mAddress?.getAddressLine(0) ?: ""
    }
}