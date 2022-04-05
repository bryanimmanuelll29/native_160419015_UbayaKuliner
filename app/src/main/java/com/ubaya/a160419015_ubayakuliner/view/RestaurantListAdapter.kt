package com.ubaya.a160419015_ubayakuliner.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.a160419015_ubayakuliner.R
import com.ubaya.a160419015_ubayakuliner.model.Restaurant
import com.ubaya.a160419015_ubayakuliner.util.loadImage
import kotlinx.android.synthetic.main.restaurant_list_item.view.*

class RestaurantListAdapter (val restaurantList: ArrayList<Restaurant>) :
    RecyclerView.Adapter<RestaurantListAdapter.HomeViewholder>() {
    class HomeViewholder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewholder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.restaurant_list_item, parent, false)
        return HomeViewholder(view)
    }

    override fun onBindViewHolder(holder: HomeViewholder, position: Int) {
        val restaurant = restaurantList[position]
        with (holder.view) {
            textName.text = restaurant.name
            textRating.text = restaurant.rating + " / 5"
            textAddress.text = restaurant.address
            imageView.loadImage(restaurantList[position].photoURL.toString(), holder.view.progressBar)
            cardViewMenuList.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeDetail(restaurant.id.toString())
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount() = restaurantList.size

    fun updateRestaurantList(newRestaurantList: ArrayList<Restaurant>) {
        restaurantList.clear()
        restaurantList.addAll(newRestaurantList)
        notifyDataSetChanged()
    }
}