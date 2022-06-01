package com.example.fishmarket.data.repository.menu.source.remote.model

data class MenuResponse(
    var id: String? = "",
    var name: String? = "",
    var price: Int? = 0,
    var id_category: String? = "",
    var created_date: Long? = 0
)