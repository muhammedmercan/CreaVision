package com.ai.creavision.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ai.creavision.domain.model.Favorite


@androidx.room.Dao
interface Dao {


    @Query("SELECT * FROM Favorites")
    suspend fun getAllFavorites(): List<Favorite>

    @Insert
    suspend fun addFavorite(favorite: Favorite): Long

    @Query("DELETE FROM favorites WHERE imgUrl = :imgUrl")
    suspend fun deleteProduct(imgUrl: String): Int

    @Query("SELECT EXISTS(SELECT 1 FROM Favorites WHERE imgUrl = :imgUrl)")
    suspend fun isFavoriteExists(imgUrl: String): Boolean






}