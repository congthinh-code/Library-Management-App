package com.example.library_management.data.model


data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val category: String,
    val quantity: Int,
    val description: String
)
