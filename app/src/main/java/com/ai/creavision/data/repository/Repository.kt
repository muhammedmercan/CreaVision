package com.ai.creavision.data.repository

import com.ai.creavision.data.local.Dao
import com.ai.creavision.data.remote.Api
import com.ai.creavision.domain.model.Favorite
import com.ai.creavision.domain.model.ImageResponse
import com.ai.creavision.domain.model.LastResponse
import com.ai.creavision.domain.model.PromptRequest
import com.ai.creavision.domain.repository.RepositoryInterface
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val retrofitApi: Api,
    private val dao: Dao
    ) : RepositoryInterface{

    override suspend fun createImage(createImageModel: PromptRequest): Response<LastResponse> {
        return retrofitApi.createImage(createImageModel)
    }

    override suspend fun getUpdate(url: String): Response<ImageResponse> {
        return retrofitApi.getUpdate(url)
    }

    override suspend fun getAllFavorites(): List<Favorite> {
        return dao.getAllFavorites()
    }

    override suspend fun addFavorite(favorite: Favorite): Long {
        return dao.addFavorite(favorite)
    }

    override suspend fun deleteFavorite(imgPath: String): Int {
        return dao.deleteProduct(imgPath)
    }

    override suspend fun deleteProductWithUrl(imgUrl: String): Int {
        return dao.deleteProductWithUrl(imgUrl)
    }

    override suspend fun isFavoriteExists(imgPath: String): Boolean {
        return dao.isFavoriteExists(imgPath)
    }

    override suspend fun isFavoriteExistsWithUrl(imgUrl: String): Boolean {
        return dao.isFavoriteExistsWithUrl(imgUrl)
    }

    override suspend fun getImgPathWithUrl(imgUrl: String): String {
        return dao.getImgPathWithUrl(imgUrl)
    }
}