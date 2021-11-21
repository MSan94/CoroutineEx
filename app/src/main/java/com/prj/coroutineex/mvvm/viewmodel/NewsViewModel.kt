package com.prj.coroutineex.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prj.coroutineex.mvvm.data.Article
import com.prj.coroutineex.mvvm.retrofit.NewsApiService
import com.prj.coroutineex.mvvm.retrofit.NewsRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class NewsViewModel : ViewModel() {

    private val parentJob = Job()
    private val coroutineContext : CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)
    private val newsRepository : NewsRepo = NewsRepo(NewsApiService.newsApi)
    val newsLiveData = MutableLiveData<MutableList<Article>>()

    fun getLatestNews(){
        scope.launch {
            val latestNews = newsRepository.getLatestNews()
            newsLiveData.postValue(latestNews)
        }
    }

    fun cancelRequests() = coroutineContext.cancel()

}