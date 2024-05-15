package com.ai.creavision.data.repository

import com.ai.creavision.data.remote.Api
import com.ai.creavision.domain.model.CreateImageModel
import com.ai.creavision.domain.model.ImageResponse
import com.ai.creavision.domain.repository.RepositoryInterface
import com.ai.creavision.utils.Resource
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val retrofitApi: Api,
    ) : RepositoryInterface{

    override suspend fun createImage(createImageModel: CreateImageModel): Response<ImageResponse> {
        return retrofitApi.createImage(createImageModel)
    }
}