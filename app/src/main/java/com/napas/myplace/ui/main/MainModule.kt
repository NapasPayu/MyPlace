package com.napas.myplace.ui.main

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.napas.myplace.di.scope.PerActivity
import dagger.Module
import dagger.Provides
import th.co.officemate.di.qualifier.ActivityContext

@Module
class MainModule {
    @Provides
    @ActivityContext
    fun provideContext(mainActivity: MainActivity): Context = mainActivity

    @Provides
    @PerActivity
    fun provideFragmentManager(mainActivity: MainActivity): FragmentManager = mainActivity.supportFragmentManager
}