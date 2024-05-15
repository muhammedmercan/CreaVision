package com.ai.creavision.domain.repository

import com.ai.creavision.domain.model.CreateImageModel
import com.ai.creavision.domain.model.ImageResponse
import com.ai.creavision.utils.Resource
import retrofit2.Response

interface RepositoryInterface {

    suspend fun createImage(createImageModel: CreateImageModel) : Response<ImageResponse>

}