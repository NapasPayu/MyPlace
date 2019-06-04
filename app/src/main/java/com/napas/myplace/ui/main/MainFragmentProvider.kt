package com.napas.myplace.ui.main

import androidx.appcompat.app.AppCompatActivity
import com.napas.myplace.di.scope.PerFragment
import com.napas.myplace.ui.places.PlacesFragment
import com.napas.myplace.ui.places.PlacesModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {
    @Binds
    abstract fun bindActivity(mainActivity: MainActivity): AppCompatActivity

    @PerFragment
    @ContributesAndroidInjector(modules = [PlacesModule::class])
    abstract fun bindPlaceListFragment(): PlacesFragment
}