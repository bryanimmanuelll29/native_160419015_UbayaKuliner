package com.ubaya.a160419015_ubayakuliner.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.a160419015_ubayakuliner.model.Restaurant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RestaurantListViewModel(application: Application) : AndroidViewModel(application) {
    val restaurantLiveData = MutableLiveData<ArrayList<Restaurant>>()
    val restaurantsLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        restaurantsLoadErrorLiveData.value = false
        loadingLiveData.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.18.43/anmp/Project_UTS/restaurant.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Restaurant>>() {}.type
                val result = Gson().fromJson<ArrayList<Restaurant>>(it, sType)
                restaurantLiveData.value = result
                loadingLiveData.value = false
                Log.d("showvolley", it)
            },
            {
                loadingLiveData.value = true
                Log.d("errorvolley", it.toString())
            }
        ).apply {
            tag = "TAG"
        }
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}
