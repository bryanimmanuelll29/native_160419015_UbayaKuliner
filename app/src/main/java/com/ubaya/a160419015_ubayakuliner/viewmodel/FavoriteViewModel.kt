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

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    val favoriteLiveData = MutableLiveData<ArrayList<Menu>>()
    val favoriteLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {
        favoriteLoadErrorLiveData.value = false
        loadingLiveData.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.18.43/anmp/Project_UTS/favorite.php"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Menu>>() {}.type
                val result = Gson().fromJson<ArrayList<Menu>>(it, sType)
                favoriteLiveData.value = result
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