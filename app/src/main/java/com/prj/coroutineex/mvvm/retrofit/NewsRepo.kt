package com.prj.coroutineex.mvvm.retrofit

import com.prj.coroutineex.mvvm.data.Article

class NewsRepo(private val apiInterface: NewsApiInterface) : BaseRepository(){

    suspend fun getLatestNews() : MutableList<Article>?{
        return safeApiCall(
            call = {apiInterface.fetchLatestNewsAsync("Nigeria","publisheAt").await()},
            error = "Error fetching news"
        )?.articles?.toMutableList()
    }

}