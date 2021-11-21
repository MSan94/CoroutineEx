package com.prj.coroutineex.mvvm.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.prj.coroutineex.R
import com.prj.coroutineex.databinding.ActivityNewsBinding
import com.prj.coroutineex.mvvm.adapter.NewsAdapter
import com.prj.coroutineex.mvvm.data.Article
import com.prj.coroutineex.mvvm.viewmodel.NewsViewModel
import com.prj.coroutineex.mvvm.viewmodel.NewsViewModelFactory
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity(){

    private val newsViewModel : NewsViewModel by viewModels()
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = NewsViewModelFactory()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        binding.viewModel = newsViewModel

        btn_refresh.setOnClickListener {
            newsViewModel.getLatestNews()
        }




        newsViewModel.getLatestNews()
        newsViewModel.newsLiveData.observe(this, Observer {
            Log.e("news count", it.size.toString())
            Log.e("author", it[0].author)
            Log.e("title", it[0].title)
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = NewsAdapter( //MutableList<Article>
                it
            )
        })


    }

}