package com.example.cleanugra.apiMethods

import com.example.cleanugra.models.categories.CategoriesListModel
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesApiMethods {
    @GET("categories")
    suspend fun getAllCategories(): Response<CategoriesListModel>
}