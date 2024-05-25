package com.ai.creavision.domain.model

data class ArtStylePrompt(

    val image: Int,
    val prompt: String,
    val negativePrompt: String
)