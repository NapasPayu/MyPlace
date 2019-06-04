package com.napas.myplace.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulersFacade @Inject constructor() {
    val io: Scheduler
        get() = Schedulers.io()

    val computation: Scheduler
        get() = Schedulers.computation()

    val ui: Scheduler
        get() = AndroidSchedulers.mainThread()

}