package com.example.movieflix.data.model.video


import com.google.gson.annotations.SerializedName

data class VideoResult(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val videos: List<Video>
)