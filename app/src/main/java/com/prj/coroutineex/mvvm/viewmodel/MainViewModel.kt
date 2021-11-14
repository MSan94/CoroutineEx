package com.prj.coroutineex.mvvm.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.prj.coroutineex.mvvm.room.AppDatabase
import com.prj.coroutineex.mvvm.room.Repository
import com.prj.coroutineex.mvvm.room.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application : Application) : AndroidViewModel(application) {
    var main_text : ObservableField<String> = ObservableField("Main")

    val repository : Repository = Repository(AppDatabase.getDatabase(application,viewModelScope))
    var allUsers : LiveData<List<UserEntity>> = repository.allUsers

    fun insert(userEntity: UserEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(userEntity)
    }

}