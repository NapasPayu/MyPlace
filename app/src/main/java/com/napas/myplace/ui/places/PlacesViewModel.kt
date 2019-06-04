package com.napas.myplace.ui.places

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.napas.myplace.base.BaseViewModel
import com.napas.myplace.base.SchedulersFacade
import com.napas.myplace.ui.places.model.PlaceItem
import com.napas.myplace.ui.places.usecase.LoadFavoritePlacesUseCase
import com.napas.myplace.ui.places.usecase.LoadNearbyPlacesUseCase
import com.napas.myplace.ui.places.usecase.UpdateFavoritePlaceUseCase
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class PlacesViewModel(
    private val loadNearbyPlacesUseCase: LoadNearbyPlacesUseCase,
    private val loadFavoritePlacesUseCase: LoadFavoritePlacesUseCase,
    private val updateFavoritePlaceUseCase: UpdateFavoritePlaceUseCase,
    private val schedulersFacade: SchedulersFacade
) : BaseViewModel() {

    companion object {
        @JvmStatic
        private val TAG = PlacesViewModel::class.java.simpleName
    }

    private val mNearbyPlacesLiveData by lazy { MutableLiveData<List<PlaceItem>>() }
    private val mFavoritePlacesLiveData by lazy { MutableLiveData<List<PlaceItem>>() }

    fun bindNearbyPlacesLiveData() = mNearbyPlacesLiveData

    fun bindFavoritePlacesLiveData(): LiveData<List<PlaceItem>> {
        if (mFavoritePlacesLiveData.value == null) {
            loadFavoritePlaces()
        }
        return mFavoritePlacesLiveData
    }

    fun loadNearbyPlaces(latitude: Double, longitude: Double) {
        disposables += loadNearbyPlacesUseCase.execute(latitude, longitude)
            .subscribeOn(schedulersFacade.io)
            .observeOn(schedulersFacade.ui)
            .subscribeBy(
                onError = {
                    Log.d(TAG, it.message)
                },
                onSuccess = {
                    mNearbyPlacesLiveData.value = it
                }
            )
    }

    fun loadFavoritePlaces() {
        disposables += loadFavoritePlacesUseCase.execute()
            .subscribeOn(schedulersFacade.io)
            .observeOn(schedulersFacade.ui)
            .subscribeBy(
                onError = {
                    Log.d(TAG, it.message)
                },
                onSuccess = {
                    mFavoritePlacesLiveData.value = it
                }
            )
    }

    fun updateFavoritePlace(placeItem: PlaceItem, isFavorite: Boolean) {
        disposables += updateFavoritePlaceUseCase.execute(placeItem, isFavorite)
            .subscribeOn(schedulersFacade.io)
            .observeOn(schedulersFacade.ui)
            .subscribeBy(
                onError = {
                    Log.d(TAG, it.message)
                },
                onComplete = {
                    onFavoritePlaceUpdated(placeItem, isFavorite)
                }
            )
    }

    private fun onFavoritePlaceUpdated(placeItem: PlaceItem, isFavorite: Boolean) {
        mFavoritePlacesLiveData.value = mFavoritePlacesLiveData.value?.toMutableList()?.apply {
            if (isFavorite) {
                add(placeItem.copy(isFavorite = isFavorite))
            } else {
                remove(placeItem)
            }
        }
        mNearbyPlacesLiveData.value = mNearbyPlacesLiveData.value?.toMutableList()?.apply {
            set(indexOf(placeItem), placeItem.copy(isFavorite = isFavorite))
        }
    }
}