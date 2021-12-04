package com.example.movieflix.data.model.series


import com.google.gson.annotations.SerializedName

data class SerieResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val series: List<Serie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)