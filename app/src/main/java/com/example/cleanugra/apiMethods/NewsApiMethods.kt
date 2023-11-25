package com.example.cleanugra.apiMethods

import com.example.cleanugra.models.news.NewsListModel
import com.example.cleanugra.models.receptions.ReceptionModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApiMethods {
    @GET("news")
    suspend fun getNews(): Response<NewsListModel>
}