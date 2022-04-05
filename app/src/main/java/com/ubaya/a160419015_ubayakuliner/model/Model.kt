package com.ubaya.a160419015_ubayakuliner.model

data class Restaurant(val id: String?,
                      val name: String?,
                      val address: String?,
                      val rating: String?,
                      val photoURL: String? )

data class Menu(val id: String?,
                val name: String?,
                val rating: String?,
                val description: String?,
                val restaurantId: String?,
                val restaurantName: String?,
                val photoURL: String?)

data class Message(val id: String?,
                val title: String?,
                val description: String?)


