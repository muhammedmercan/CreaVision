package com.ai.creavision.data.remote

import com.ai.creavision.domain.model.ImageResponse
import com.ai.creavision.domain.model.LastResponse
import com.ai.creavision.domain.model.PromptRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path


interface Api {


    @Headers("Authorization: Bearer r8_OV5ryF3n0it9MZq4en93eJ7UPhNjrN90VrUsR")
    @POST("predictions")
    suspend fun createImage(
        @Body createImageModel : PromptRequest,
    ): Response<LastResponse>

    @Headers("Authorization: Bearer r8_OV5ryF3n0it9MZq4en93eJ7UPhNjrN90VrUsR")
    @GET("predictions/{predictionId}")
    suspend fun getUpdate(@Path("predictionId") predictionId: String): Response<ImageResponse>

    /*
    @Headers("Authorization: Bearer sk-proj-gmLSZXSxapUxmwIiuTKkT3BlbkFJHWn8oDeRt8GL00Ke5hce")
    @POST("generations")
    suspend fun createImage(
        @Body createImageModel : CreateImageModel,
    ): Response<ImageResponse>


     */

}