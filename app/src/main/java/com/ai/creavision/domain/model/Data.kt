package com.ai.creavision.domain.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("revised_prompt")
    val revisedPrompt: String?,
    @SerializedName("url")
    val url: String?
)