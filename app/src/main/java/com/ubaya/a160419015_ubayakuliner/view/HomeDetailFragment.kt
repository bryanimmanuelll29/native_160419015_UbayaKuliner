package com.ubaya.a160419015_ubayakuliner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.a160419015_ubayakuliner.R
import com.ubaya.a160419015_ubayakuliner.util.loadImage
import com.ubaya.a160419015_ubayakuliner.viewmodel.RestaurantDetailViewModel
import com.ubaya.a160419015_ubayakuliner.viewmodel.RestaurantListViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_detail.*
import kotlinx.android.synthetic.main.fragment_home_detail.recView
import kotlinx.android.synthetic.main.fragment_home_detail.refreshLayout
import kotlinx.android.synthetic.main.restaurant_list_item.*

class HomeDetailFragment : Fragment() {
    private lateinit var viewModel: RestaurantDetailViewModel
    private val restaurantDetailAdapter = RestaurantDetailAdapter(arrayListOf())
    var restaurantId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(RestaurantDetailViewModel::class.java)

        // Utk dapat id yg dikirim dari home fragment
        if(arguments != null) {
            restaurantId = HomeDetailFragmentArgs.fromBundle(requireArguments()).restaurantId
        }
        // Utk dapat detail restaurant spesifik
        viewModel.getRestaurantDetail(restaurantId)

        // Function utk dapatkan list menu restaurant x
        viewModel.getRestaurantMenus(restaurantId)

        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = restaurantDetailAdapter

        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recView.visibility = View.GONE
            textErrorHomeDetail.visibility = View.GONE
            progressBarHomeDetail.visibility = View.VISIBLE
            viewModel.getRestaurantMenus(restaurantId)
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.restaurantLiveData.observe(viewLifecycleOwner) {
            textNameHomeDetail.setText(it.name)
            textRatingHomeDetail.setText(it.rating + " / 5")
            textAddressHomeDetail.setText(it.address)
            imageView2.loadImage(it.photoURL.toString(), progressBar3)
        }

        viewModel.menuLiveData.observe(viewLifecycleOwner) {
            restaurantDetailAdapter.updateMenuList(it)
        }

        viewModel.menuLoadErrorLiveData.observe(viewLifecycleOwner) {
            textErrorHomeDetail.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.loadingLiveDataMenu.observe(viewLifecycleOwner) {
            if (it) { // sedang loading
                recView.visibility = View.GONE
                progressBarHomeDetail.visibility = View.VISIBLE
            } else {
                recView.visibility = View.VISIBLE
                progressBarHomeDetail.visibility = View.GONE
            }
        }
    }
}