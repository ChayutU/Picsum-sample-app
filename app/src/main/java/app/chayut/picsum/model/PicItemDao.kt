package app.chayut.picsum.model

import com.google.gson.annotations.SerializedName

data class PicItemDao(

    @field:SerializedName("author")
    val author: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("download_url")
    val url: String? = null
)