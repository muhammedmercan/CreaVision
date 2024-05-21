package com.ai.creavision.domain.model


import com.google.gson.annotations.SerializedName

data class LastResponse(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("error")
    val error: Any?,
    @SerializedName("id")
    val id: String?,
    //@SerializedName("input")
    //val input: İnput?,
    @SerializedName("logs")
    val logs: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("urls")
    val urls: Urls?,
    @SerializedName("version")
    val version: String?,
    @SerializedName("webhook")
    val webhook: String?,
    @SerializedName("webhook_events_filter")
    val webhookEventsFilter: List<String?>?
)