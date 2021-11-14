package com.prj.coroutineex.mvvm.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prj.coroutineex.R
import com.prj.coroutineex.databinding.ActivityDbBinding
import com.prj.coroutineex.mvvm.adapter.UserListAdapter
import com.prj.coroutineex.mvvm.dialog.UserDialog
import com.prj.coroutineex.mvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_db.*

class DbActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels()
    private lateinit var binding : ActivityDbBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_db)
        binding.viewModel = viewModel

        val mAdapter = UserListAdapter(this)

        recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        viewModel.allUsers.observe(this, Observer { users ->
            users?.let {
                mAdapter.setUsers(it)
            }
        })

        btnAdd.setOnClickListener {
            val dialog = UserDialog(this)
            dialog.show()
        }

    }

}