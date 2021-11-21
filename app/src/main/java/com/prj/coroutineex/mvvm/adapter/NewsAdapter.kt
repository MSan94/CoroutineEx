package com.prj.coroutineex.mvvm.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prj.coroutineex.R
import com.prj.coroutineex.databinding.NewsItemBinding
import com.prj.coroutineex.mvvm.data.Article

class NewsAdapter(
    private var mydataSet : MutableList<Article>,
): RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(val binding : NewsItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        Log.d("RecyclerTest","들어옴")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        return ArticleViewHolder(NewsItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val data = mydataSet[position]
        holder.binding.textViewAuthor.text = data.author
        holder.binding.textViewTitle.text = data.title
        holder.binding.textViewContent.text = data.content
        holder.binding.textViewDescription.text = data.description
        holder.binding.textViewUrl.text = data.url
    }

    override fun getItemCount(): Int {
        return mydataSet.size
    }


}