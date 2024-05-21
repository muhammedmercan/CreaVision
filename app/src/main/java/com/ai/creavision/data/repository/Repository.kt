package com.ai.creavision.data.repository

import com.ai.creavision.data.remote.Api
import com.ai.creavision.domain.model.ImageResponse
import com.ai.creavision.domain.model.LastResponse
import com.ai.creavision.domain.model.PromptRequest
import com.ai.creavision.domain.repository.RepositoryInterface
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val retrofitApi: Api,
    ) : RepositoryInterface{

    override suspend fun createImage(createImageModel: PromptRequest): Response<LastResponse> {
        return retrofitApi.createImage(createImageModel)
    }

    override suspend fun getUpdate(url: String): Response<ImageResponse> {
        return retrofitApi.getUpdate(url)
    }
}