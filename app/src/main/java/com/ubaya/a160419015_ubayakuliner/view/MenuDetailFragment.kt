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
import com.ubaya.a160419015_ubayakuliner.viewmodel.MenuDetailViewModel
import com.ubaya.a160419015_ubayakuliner.viewmodel.RestaurantDetailViewModel
import kotlinx.android.synthetic.main.fragment_home_detail.*
import kotlinx.android.synthetic.main.fragment_menu_detail.*

class MenuDetailFragment : Fragment() {
    private lateinit var viewModel: MenuDetailViewModel
    var menuId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MenuDetailViewModel::class.java)

        // Utk dapat id yg dikirim dari home fragment
        if(arguments != null) {
            menuId = MenuDetailFragmentArgs.fromBundle(requireArguments()).menuId
        }
        // Utk dapat detail restaurant spesifik
        viewModel.fetch(menuId)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.menuLiveData.observe(viewLifecycleOwner) {
            textNameMenuDetail.setText(it.name)
            textRatingMenuDetail.setText(it.rating + " / 5")
            textDescriptionMenuDetail.setText(it.description)
            imageViewMenuDetail.loadImage(it.photoURL.toString(), progressBarMenuDetail)
        }
    }
}