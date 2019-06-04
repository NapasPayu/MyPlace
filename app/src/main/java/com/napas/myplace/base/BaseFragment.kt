package com.napas.myplace.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment : Fragment(), HasSupportFragmentInjector {

    val context: Context
        @JvmName("getNonNullContext")
        get() = getContext()!!

    val activity: FragmentActivity
        @JvmName("getNonNullActivity")
        get() = getActivity()!!

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var schedulersFacade: SchedulersFacade
    protected var subscriptions = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        subscriptions = CompositeDisposable()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDestroyView() {
        subscriptions.clear()
        super.onDestroyView()
    }

    override fun supportFragmentInjector() = childFragmentInjector
}