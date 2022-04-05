package com.ubaya.a160419015_ubayakuliner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.a160419015_ubayakuliner.R
import com.ubaya.a160419015_ubayakuliner.viewmodel.RestaurantListViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var viewModel: RestaurantListViewModel
    private val restaurantListAdapter = RestaurantListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(RestaurantListViewModel::class.java)
        viewModel.refresh()

        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = restaurantListAdapter

        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recView.visibility = View.GONE
            textErrorHome.visibility = View.GONE
            progressLoadHome.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.restaurantLiveData.observe(viewLifecycleOwner) {
            restaurantListAdapter.updateRestaurantList(it)
        }

        viewModel.restaurantsLoadErrorLiveData.observe(viewLifecycleOwner) {
            textErrorHome.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if (it) { // sedang loading
                recView.visibility = View.GONE
                progressLoadHome.visibility = View.VISIBLE
            } else {
                recView.visibility = View.VISIBLE
                progressLoadHome.visibility = View.GONE
            }
        }
    }
}