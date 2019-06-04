package com.napas.myplace.ui.places.epoxy

import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxrelay2.Relay
import com.napas.myplace.R
import com.napas.myplace.base.BaseEpoxyViewHolder
import com.napas.myplace.ui.places.model.PlaceItem
import com.napas.myplace.ui.places.relay.PlaceItemRelay
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.list_item_place.view.*

@EpoxyModelClass(layout = R.layout.list_item_place)
abstract class PlaceItemModel : EpoxyModelWithHolder<BaseEpoxyViewHolder>() {

    @EpoxyAttribute
    lateinit var placeItem: PlaceItem

    @EpoxyAttribute
    lateinit var placeItemRelay: Relay<PlaceItemRelay>

    override fun bind(holder: BaseEpoxyViewHolder) {
        holder.itemView.apply {
            with(placeItem) {
                if (icon.isNotEmpty()) {
                    Glide.with(context).load(icon).fitCenter().into(image_view_icon)
                }
                text_view_name.text = name
                text_view_website.apply {
                    text = website
                    isVisible = website.isNotEmpty()
                }
                image_view_favorite.isSelected = isFavorite
            }

            image_view_favorite.clicks()
                .map { PlaceItemRelay(placeItem) }
                .subscribeBy(
                    onNext = placeItemRelay::accept
                )
        }
    }
}