package com.ai.creavision.domain.model

import com.google.gson.annotations.SerializedName

data class PromptRequest(
    @SerializedName("version") val version: String = "5f24084160c9089501c1b3545d9be3c27883ae2239b6f412990e82d4a6210f8f",
    @SerializedName("input") val input: Input
)