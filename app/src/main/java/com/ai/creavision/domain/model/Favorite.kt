package com.ai.creavision.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Favorites")
data class Favorite(

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0 ,
    val string: String,
    val prompt: String
)
