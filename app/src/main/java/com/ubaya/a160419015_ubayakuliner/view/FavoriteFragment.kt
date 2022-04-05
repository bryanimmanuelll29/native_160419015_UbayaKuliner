package com.ubaya.a160419015_ubayakuliner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.a160419015_ubayakuliner.R
import com.ubaya.a160419015_ubayakuliner.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recView
import kotlinx.android.synthetic.main.fragment_home.refreshLayout

class FavoriteFragment : Fragment() {
    private lateinit var viewModel: FavoriteViewModel
    private val favoriteListAdapter = FavoriteAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.refresh()

        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = favoriteListAdapter

        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recView.visibility = View.GONE
            textErrorFav.visibility = View.GONE
            progressLoadFav.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.favoriteLiveData.observe(viewLifecycleOwner) {
            favoriteListAdapter.updateFavoriteList(it)
        }

        viewModel.favoriteLoadErrorLiveData.observe(viewLifecycleOwner) {
            textErrorFav.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if (it) { // sedang loading
                recView.visibility = View.GONE
                progressLoadFav.visibility = View.VISIBLE
            } else {
                recView.visibility = View.VISIBLE
                progressLoadFav.visibility = View.GONE
            }
        }
    }
}