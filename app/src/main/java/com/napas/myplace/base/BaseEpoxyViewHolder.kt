package com.napas.myplace.base

import android.view.View
import com.airbnb.epoxy.EpoxyHolder

class BaseEpoxyViewHolder : EpoxyHolder() {

    lateinit var itemView: View

    override fun bindView(itemView: View) {
        this.itemView = itemView
    }
}