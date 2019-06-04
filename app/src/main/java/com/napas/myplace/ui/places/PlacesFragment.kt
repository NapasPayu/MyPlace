package com.napas.myplace.ui.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.napas.myplace.R
import com.napas.myplace.base.BaseFragment
import com.napas.myplace.ui.main.FragmentType
import com.napas.myplace.ui.places.epoxy.PlacesController
import com.napas.myplace.ui.places.relay.PlaceItemRelay
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_places.*
import javax.inject.Inject

class PlacesFragment : BaseFragment() {

    companion object {

        private const val ARG_FRAGMENT_TYPE = "fragment_type"

        @JvmStatic
        fun newInstance(fragmentType: FragmentType): PlacesFragment {
            return PlacesFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_FRAGMENT_TYPE, fragmentType)
                }
            }
        }
    }

    @Inject
    lateinit var mPlacesViewModelFactory: PlacesViewModelFactory
    @Inject
    lateinit var mController: PlacesController

    private val mPlaceListViewModel by lazy {
        ViewModelProviders.of(activity, mPlacesViewModelFactory).get(PlacesViewModel::class.java)
    }
    private lateinit var mFragmentType: FragmentType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentType = arguments?.getSerializable(ARG_FRAGMENT_TYPE) as FragmentType? ?: FragmentType.NEARBY
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_places, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        epoxy_recycler_view.setController(mController)

        when (mFragmentType) {
            FragmentType.NEARBY -> mPlaceListViewModel.bindNearbyPlacesLiveData()
            FragmentType.FAVORITE -> mPlaceListViewModel.bindFavoritePlacesLiveData()
        }
            .observe(this, Observer {
                it?.let {
                    mController.setData(it)
                }
            })

        subscriptions += mController.bindPlaceItemRelay()
            .map(PlaceItemRelay::placeItem)
            .subscribeOn(schedulersFacade.computation)
            .observeOn(schedulersFacade.ui)
            .subscribeBy {
                mPlaceListViewModel.updateFavoritePlace(it, !it.isFavorite)
            }
    }

}