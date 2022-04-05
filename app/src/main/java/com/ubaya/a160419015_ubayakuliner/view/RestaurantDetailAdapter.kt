package com.ubaya.a160419015_ubayakuliner.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.a160419015_ubayakuliner.R
import com.ubaya.a160419015_ubayakuliner.model.Menu
import com.ubaya.a160419015_ubayakuliner.util.loadImage
import kotlinx.android.synthetic.main.menu_list_item.view.*
import kotlinx.android.synthetic.main.restaurant_list_item.view.cardViewMenuList

class RestaurantDetailAdapter (val menuList: ArrayList<Menu>) :
    RecyclerView.Adapter<RestaurantDetailAdapter.HomeViewholder>() {
    class HomeViewholder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewholder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.menu_list_item, parent, false)
        return HomeViewholder(view)
    }

    override fun onBindViewHolder(holder: HomeViewholder, position: Int) {
        val menu = menuList[position]
        with(holder.view) {
            textNameFav.text = menu.name
            textRatingFav.text = menu.rating + " / 5"
            textRestaurantNameFav.text = menu.description
            imageViewMenuList.loadImage(
                menuList[position].photoURL.toString(),
                holder.view.progressBarFav
            )
            cardViewMenuList.setOnClickListener {
                val action = HomeDetailFragmentDirections.actionMenuDetail(menu.id.toString())
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun getItemCount() = menuList.size

    fun updateMenuList(newMenuList: ArrayList<Menu>) {
        menuList.clear()
        menuList.addAll(newMenuList)
        notifyDataSetChanged()
    }
}
