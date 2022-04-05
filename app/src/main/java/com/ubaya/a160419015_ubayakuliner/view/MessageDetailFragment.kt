package com.ubaya.a160419015_ubayakuliner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ubaya.a160419015_ubayakuliner.R
import com.ubaya.a160419015_ubayakuliner.util.loadImage
import com.ubaya.a160419015_ubayakuliner.viewmodel.MessageDetailViewModel
import kotlinx.android.synthetic.main.fragment_menu_detail.*
import kotlinx.android.synthetic.main.fragment_message_detail.*

class MessageDetailFragment : Fragment() {
    private lateinit var viewModel: MessageDetailViewModel
    var messageId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MessageDetailViewModel::class.java)

        // Utk dapat id yg dikirim dari home fragment
        if(arguments != null) {
            messageId = MessageDetailFragmentArgs.fromBundle(requireArguments()).messageId
        }
        // Utk dapat detail restaurant spesifik
        viewModel.fetch(messageId)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.messageLiveData.observe(viewLifecycleOwner) {
            textTitleMessageDetail.setText(it.title)
            textDescriptionMessageDetail.setText(it.description)
        }
    }
}