package com.ai.creavision.data.local

import androidx.room.Insert
import androidx.room.Query
import com.ai.creavision.domain.model.Favorite

interface Dao {

    @Insert
    suspend fun addFavorite(favorite: Favorite): Long

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteProduct(id:Int)

}