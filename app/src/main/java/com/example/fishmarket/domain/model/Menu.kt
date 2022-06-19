package com.example.fishmarket.domain.model

data class Menu(
    val id: String,
    val name: String,
    val price: Int,
    val unit: String,
    val image: String,
    val id_category: String,
    val created_date: Long
)