package com.ai.creavision.domain.repository

import com.ai.creavision.domain.model.Favorite
import com.ai.creavision.domain.model.ImageResponse
import com.ai.creavision.domain.model.LastResponse
import com.ai.creavision.domain.model.PromptRequest
import retrofit2.Response

interface RepositoryInterface {

    suspend fun createImage(createImageModel: PromptRequest) : Response<LastResponse>

    suspend fun getUpdate(url: String) : Response<ImageResponse>

    suspend fun getAllFavorites() : List<Favorite>

    suspend fun addFavorite(favorite: Favorite) : Long
    suspend fun deleteFavorite(imgUrl: String) : Int


    suspend fun isFavoriteExists(imgUrl: String) : Boolean


}