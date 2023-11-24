package com.example.cleanugra.apiMethods

import com.example.cleanugra.models.categories.CategoriesListModel
import com.example.cleanugra.models.receptions.ReceptionModel
import com.example.cleanugra.models.receptions.ReceptionShortModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ReceptionsApiMethods {
    @GET("reception/categories/{title}")
    suspend fun getPointsByTitleCategory(@Path("title") title: String): Response<List<ReceptionShortModel>>

    @GET("reception/{title}")
    suspend fun getPointByTitle(@Path("title") title: String): Response<ReceptionModel>
}