package com.napas.myplace.ui.places.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.jakewharton.rxrelay2.PublishRelay
import com.napas.myplace.ui.places.model.PlaceItem
import com.napas.myplace.ui.places.relay.PlaceItemRelay
import io.reactivex.Observable
import javax.inject.Inject

class PlacesController @Inject constructor() : TypedEpoxyController<List<PlaceItem>>() {

    private val mPlaceItemRelay by lazy { PublishRelay.create<PlaceItemRelay>() }

    override fun buildModels(data: List<PlaceItem>?) {
        if (!data.isNullOrEmpty()) {
            data.forEach {
                placeItem {
                    id(it.id)
                    placeItem(it)
                    placeItemRelay(mPlaceItemRelay)
                }
            }
        }
    }

    fun bindPlaceItemRelay(): Observable<PlaceItemRelay> = mPlaceItemRelay
}