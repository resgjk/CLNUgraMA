package com.example.cleanugra.apiMethods

import com.example.cleanugra.models.message.MessageModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageApiMethods {
    @POST("message")
    suspend fun getMessage(@Body message: MessageModel): Response<MessageModel>
}