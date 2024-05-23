package com.ai.creavision.domain.model


import com.google.gson.annotations.SerializedName

data class ImageResponse(

    @SerializedName("output")
    val imageUrl: List<String>,
    val status: String,
    val error: String
)