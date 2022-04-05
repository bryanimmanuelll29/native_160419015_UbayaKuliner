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
import com.ubaya.a160419015_ubayakuliner.model.Message

class MessageDetailViewModel(application: Application) : AndroidViewModel(application) {
    val messageLiveData = MutableLiveData<Message>()
    val messageLoadErrorLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(messageId: String) {
        messageLoadErrorLiveData.value = false
        loadingLiveData.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://192.168.18.43/anmp/Project_UTS/message.php?id=$messageId"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val result = Gson().fromJson<Message>(it, Message::class.java)
                messageLiveData.value = result
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
}