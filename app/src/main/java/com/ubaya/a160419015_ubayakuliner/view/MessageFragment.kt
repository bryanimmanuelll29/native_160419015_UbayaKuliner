package com.ubaya.a160419015_ubayakuliner.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.a160419015_ubayakuliner.R
import com.ubaya.a160419015_ubayakuliner.viewmodel.MessageListViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recView
import kotlinx.android.synthetic.main.fragment_home.refreshLayout
import kotlinx.android.synthetic.main.fragment_message.*

class MessageFragment : Fragment() {
    private lateinit var viewModel: MessageListViewModel
    private val messageListAdapter = MessageListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MessageListViewModel::class.java)
        viewModel.refresh()

        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = messageListAdapter

        observeViewModel()

        refreshLayout.setOnRefreshListener {
            recView.visibility = View.GONE
            textErrorMessageList.visibility = View.GONE
            progressBarMessageList.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewModel.messageLiveData.observe(viewLifecycleOwner) {
            messageListAdapter.updateMessageList(it)
        }

        viewModel.messageLoadErrorLiveData.observe(viewLifecycleOwner) {
            textErrorMessageList.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if (it) { // sedang loading
                recView.visibility = View.GONE
                progressBarMessageList.visibility = View.VISIBLE
            } else {
                recView.visibility = View.VISIBLE
                progressBarMessageList.visibility = View.GONE
            }
        }
    }
}