package com.napas.myplace.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.napas.myplace.ui.places.PlacesFragment
import th.co.officemate.di.qualifier.ActivityContext
import javax.inject.Inject

class SectionsPagerAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return PlacesFragment.newInstance(FragmentType.values()[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(FragmentType.values()[position].titleRes)
    }

    override fun getCount(): Int {
        return FragmentType.values().size
    }
}