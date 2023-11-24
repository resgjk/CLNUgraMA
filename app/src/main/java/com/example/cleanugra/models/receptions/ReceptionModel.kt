package com.example.cleanugra.models.receptions

data class ReceptionModel(
    val id: Int,
    val title: String,
    val coord_x: String,
    val coord_y: String,
    val addres: String,
    val category: Int,
    val img: String,
    val time: String,
    val description: String,
    val phone_number: String,
    val vk_ref: String,
    val tg_ref: String
)
