package com.ai.creavision.domain.repository

import com.ai.creavision.domain.model.ImageResponse
import com.ai.creavision.domain.model.LastResponse
import com.ai.creavision.domain.model.PromptRequest
import retrofit2.Response

interface RepositoryInterface {

    suspend fun createImage(createImageModel: PromptRequest) : Response<LastResponse>

    suspend fun getUpdate(url: String) : Response<ImageResponse>


}