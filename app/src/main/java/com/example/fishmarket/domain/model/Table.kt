package com.example.fishmarket.domain.model

data class Table(
    val id: String,
    val name: String,
    val status: Boolean,
    val createdDate: Long
)