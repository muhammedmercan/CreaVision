package com.ai.creavision.data.local

import androidx.room.RoomDatabase
import com.ai.creavision.domain.model.Favorite

@androidx.room.Database(entities = arrayOf(Favorite::class),version = 1)
abstract class Database : RoomDatabase() {

    abstract fun dao() : Dao

}