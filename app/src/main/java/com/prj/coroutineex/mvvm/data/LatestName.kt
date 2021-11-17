package com.prj.coroutineex.mvvm.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LatestName(
    @SerializedName("status") @Expose val status: String,
    @SerializedName("totalResults") @Expose val totalResults: Int,
    @SerializedName("articles") @Expose val articles: List<Article>


)
