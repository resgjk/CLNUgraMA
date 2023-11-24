package com.example.cleanugra.models.categories

data class CategoryModel(
    val id: Int,
    val title: String,
    val rules: String,
    val not_take: String?,
    val img: String
)
