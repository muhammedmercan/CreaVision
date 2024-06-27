package com.ai.creavision.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "Favorites")
class Favorite(

    val imgUrl: String = "",
    val prompt: String,
    var imgPath: String = ""

) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
