package com.napas.myplace.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val disposables by lazy { CompositeDisposable() }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}