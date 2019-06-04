package com.napas.myplace.ui.main

import androidx.annotation.StringRes
import com.napas.myplace.R

enum class FragmentType(@StringRes val titleRes: Int) {
    NEARBY(R.string.nearby),
    FAVORITE(R.string.favorite)
}