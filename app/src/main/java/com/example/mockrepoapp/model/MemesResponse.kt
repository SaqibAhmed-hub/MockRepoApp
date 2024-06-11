package com.example.mockrepoapp.model

import com.google.gson.annotations.SerializedName

data class MemesResponse(
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("data")
    val data : MemesDataModel
)

data class MemesDataModel(
    @SerializedName("memes")
    val memes: List<MemesData>
)

data class MemesData(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width : Int,
    @SerializedName("height")
    val height : Int,
    @SerializedName("box_count")
    val boxCount: Int,
    @SerializedName("captions")
    val captions: Long
)
