package com.prj.coroutineex.mvvm.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.prj.coroutineex.R
import com.prj.coroutineex.databinding.ActivityDbBinding
import com.prj.coroutineex.mvvm.adapter.RecyclerViewAdapter
import com.prj.coroutineex.mvvm.room.Entity
import com.prj.coroutineex.mvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//https://fre2-dom.tistory.com/149
class DbActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels()
    private lateinit var binding: ActivityDbBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_db)
        binding.viewModel = viewModel

        val mAdapter = RecyclerViewAdapter(this, viewModel)
        recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        viewModel.allUsers.observe(this, Observer { users ->
            users?.let {
                mAdapter.setUsers(it)
            }
        })

        button.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.insert(
                    Entity(0, edit.text.toString())
                )
            }
        }


    }

}