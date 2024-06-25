package com.ai.creavision.data.local

import androidx.room.Insert
import androidx.room.Query
import com.ai.creavision.domain.model.Favorite


@androidx.room.Dao
interface Dao {


    @Query("SELECT * FROM Favorites")
    suspend fun getAllFavorites(): List<Favorite>

    @Insert
    suspend fun addFavorite(favorite: Favorite): Long

    @Query("DELETE FROM favorites WHERE imgPath = :imgPath")
    suspend fun deleteProduct(imgPath: String): Int

    @Query("DELETE FROM favorites WHERE imgUrl = :imgUrl")
    suspend fun deleteProductWithUrl(imgUrl: String): Int

    @Query("SELECT EXISTS(SELECT 1 FROM Favorites WHERE imgPath = :imgPath)")
    suspend fun isFavoriteExists(imgPath: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM Favorites WHERE imgUrl = :imgUrl)")
    suspend fun isFavoriteExistsWithUrl(imgUrl: String): Boolean

    @Query("SELECT imgPath FROM favorites WHERE imgUrl = :imgUrl")
    suspend fun getImgPathWithUrl(imgUrl: String): String






}