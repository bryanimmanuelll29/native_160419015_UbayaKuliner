package com.ubaya.a160419015_ubayakuliner.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.a160419015_ubayakuliner.R
import com.ubaya.a160419015_ubayakuliner.model.Menu
import com.ubaya.a160419015_ubayakuliner.model.Restaurant
import com.ubaya.a160419015_ubayakuliner.util.loadImage
import kotlinx.android.synthetic.main.favorite_list_item.view.*
import kotlinx.android.synthetic.main.menu_list_item.view.*
import kotlinx.android.synthetic.main.menu_list_item.view.progressBarFav
import kotlinx.android.synthetic.main.menu_list_item.view.textNameFav
import kotlinx.android.synthetic.main.menu_list_item.view.textRatingFav
import kotlinx.android.synthetic.main.menu_list_item.view.textRestaurantNameFav
import kotlinx.android.synthetic.main.restaurant_list_item.view.*
import kotlinx.android.synthetic.main.restaurant_list_item.view.cardViewMenuList

class FavoriteAdapter(val favoriteList: ArrayList<Menu>) :
    RecyclerView.Adapter<FavoriteAdapter.HomeViewholder>() {
    class HomeViewholder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.HomeViewholder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.favorite_list_item, parent, false)
        return FavoriteAdapter.HomeViewholder(view)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.HomeViewholder, position: Int) {
        val favorite = favoriteList[position]
        with (holder.view) {
            textNameFav.text = favorite.name
            textRatingFav.text = favorite.rating + " / 5"
            textRestaurantNameFav.text = favorite.restaurantName
            imageViewFav.loadImage(favoriteList[position].photoURL.toString(), holder.view.progressBarFav)
            cardViewFav.setOnClickListener {
                val action = FavoriteFragmentDirections.actionMenuDetailFromFavorite(favorite.id.toString())
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount() = favoriteList.size

    fun updateFavoriteList(newFavoriteList: ArrayList<Menu>) {
        favoriteList.clear()
        favoriteList.addAll(newFavoriteList)
        notifyDataSetChanged()
    }
}