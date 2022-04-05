package com.ubaya.a160419015_ubayakuliner.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.a160419015_ubayakuliner.R
import com.ubaya.a160419015_ubayakuliner.model.Message
import kotlinx.android.synthetic.main.message_list_item.view.*
import kotlinx.android.synthetic.main.restaurant_list_item.view.*

class MessageListAdapter(val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<MessageListAdapter.HomeViewholder>() {
    class HomeViewholder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewholder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.message_list_item, parent, false)
        return HomeViewholder(view)
    }

    override fun onBindViewHolder(holder: HomeViewholder, position: Int) {
        val message = messageList[position]
        with (holder.view) {
            textTitle.text = message.title
            cardViewMessageList.setOnClickListener {
                val action = MessageFragmentDirections.actionMessageDetail(message.id.toString())
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount() = messageList.size

    fun updateMessageList(newMessageList: ArrayList<Message>) {
        messageList.clear()
        messageList.addAll(newMessageList)
        notifyDataSetChanged()
    }
}