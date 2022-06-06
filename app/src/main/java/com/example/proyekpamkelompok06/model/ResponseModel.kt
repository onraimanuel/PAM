package com.example.proyekpamkelompok06.model

import java.io.Serializable

open class ResponseModel {
    var success: Int = 0
    var message: String = ""
    var user: User = User()
    var itemMenuKantin: List<Produk>? = null
    var itemMenuPulsa: List<Pulsa>? = null
}

