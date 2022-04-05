package com.ubaya.a160419015_ubayakuliner.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.a160419015_ubayakuliner.model.Menu
import com.ubaya.a160419015_ubayakuliner.model.Restaurant

class RestaurantDetailViewModel(application: Application) : AndroidViewModel(application) {
    val restaurantLiveData = MutableLiveData<Restaurant>()
    val menuLiveData = MutableLiveData<ArrayList<Menu>>()
    val restaurantsLoadErrorLiveData = MutableLiveData<Boolean>()
    val menuLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveDataRestaurant = MutableLiveData<Boolean>()
    val loadingLiveDataMenu = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun getRestaurantDetail(restaurantId: String) {
        restaurantsLoadErrorLiveData.value = false
        loadingLiveDataRestaurant.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.18.43/anmp/Project_UTS/restaurant.php?id=$restaurantId"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val result = Gson().fromJson<Restaurant>(it, Restaurant::class.java)
                restaurantLiveData.value = result
                loadingLiveDataRestaurant.value = false
                Log.d("showvolley", it)
            },
            {
                loadingLiveDataRestaurant.value = true
                Log.d("errorvolley", it.toString())
            }
        ).apply {
            tag = "TAG"
        }
        queue?.add(stringRequest)
    }

    fun getRestaurantMenus(restaurantId: String) {
        menuLoadErrorLiveData.value = false
        loadingLiveDataMenu.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.18.43/anmp/Project_UTS/menu.php?restaurantId=$restaurantId"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Menu>>() {}.type
                val result = Gson().fromJson<ArrayList<Menu>>(it, sType)
                menuLiveData.value = result
                loadingLiveDataMenu.value = false
                Log.d("showvolley", it)
            },
            {
                loadingLiveDataMenu.value = true
                Log.d("errorvolley", it.toString())
            }
        ).apply {
            tag = "TAG"
        }
        queue?.add(stringRequest)
    }
}