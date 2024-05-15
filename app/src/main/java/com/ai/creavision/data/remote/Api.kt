package com.ai.creavision.data.remote

import com.ai.creavision.domain.model.CreateImageModel
import com.ai.creavision.domain.model.ImageResponse
import com.ai.creavision.utils.Resource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface Api {


    @Headers("Authorization: Bearer sk-proj-gmLSZXSxapUxmwIiuTKkT3BlbkFJHWn8oDeRt8GL00Ke5hce")
    @POST("generations")
    suspend fun createImage(
        @Body createImageModel : CreateImageModel,
    ): Response<ImageResponse>


}