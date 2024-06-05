package com.ai.creavision.domain.model

import com.google.gson.annotations.SerializedName

data class Input(
    @SerializedName("width") val width: Int = 1024,
    @SerializedName("height") val height: Int = 1024,
    @SerializedName("prompt") val prompt: String,
    @SerializedName("scheduler") val scheduler: String = "K_EULER",
    @SerializedName("num_outputs") val numOutputs: Int = 4 ,
    @SerializedName("negative_prompt") val negativePrompt: String = "worst quality, low quality",
    @SerializedName("num_inference_steps") val numInferenceSteps: Int = 4
)