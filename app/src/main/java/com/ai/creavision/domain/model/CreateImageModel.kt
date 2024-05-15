package com.ai.creavision.domain.model

import com.google.gson.annotations.SerializedName

class CreateImageModel(

    @SerializedName("model")
    val model : String = "dall-e-3",

    @SerializedName("prompt")
    val prompt : String,

    @SerializedName("n")
    val n : Int = 1,

    @SerializedName("size")
    val size : String = "1024x1024",

) {


}