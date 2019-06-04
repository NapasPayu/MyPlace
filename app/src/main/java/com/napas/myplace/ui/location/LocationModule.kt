package com.napas.myplace.ui.location

import android.content.Context
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides
import th.co.officemate.di.qualifier.ActivityContext

@Module
class LocationModule {
    @Provides
    @ActivityContext
    fun provideContext(locationActivity: LocationActivity): Context = locationActivity

    @Provides
    fun provideRxPermission(locationActivity: LocationActivity) = RxPermissions(locationActivity)

    @Provides
    fun provideFusedLocationProviderClient(@ActivityContext context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun provideStoreLocationRequest(): LocationRequest =
        LocationRequest.create().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .apply {
                interval = 1 * 1000
                fastestInterval = 1 * 1000
            }

    @Provides
    fun provideLocationSettingsRequest(locationRequest: LocationRequest): LocationSettingsRequest =
        LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()

    @Provides
    fun provideLocationSettingResponse(
        locationActivity: LocationActivity,
        locationSettingsRequest: LocationSettingsRequest
    ): Task<LocationSettingsResponse> =
        LocationServices.getSettingsClient(locationActivity).checkLocationSettings(locationSettingsRequest)
}