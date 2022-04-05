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
import com.ubaya.a160419015_ubayakuliner.model.Menu

class MenuDetailViewModel (application: Application) : AndroidViewModel(application) {
    val menuLiveData = MutableLiveData<Menu>()
    val menuLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveDataMenu = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(menuId: String) {
        menuLoadErrorLiveData.value = false
        loadingLiveDataMenu.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.18.43/anmp/Project_UTS/menu.php?id=$menuId"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val result = Gson().fromJson<Menu>(it, Menu::class.java)
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