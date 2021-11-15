package com.prj.coroutineex.mvvm.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.prj.coroutineex.mvvm.room.AppDatabase
import com.prj.coroutineex.mvvm.room.Entity
import com.prj.coroutineex.mvvm.room.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application : Application) : AndroidViewModel(application) {

    val Repository : Repository = Repository(AppDatabase.getDatabase(application, viewModelScope))

    var _allUsers : LiveData<List<Entity>> = Repository.allUsers
    val allUsers : LiveData<List<Entity>>
        get() = _allUsers

    fun insert(entity : Entity) = viewModelScope.launch(Dispatchers.IO){
        Log.d("insertTest","호출")
        Repository.insert(entity)
    }

    fun deleteAll(entity: Entity) = viewModelScope.launch(Dispatchers.IO) {
        Repository.delete(entity)
    }

    fun getAll(): LiveData<List<Entity>>{
        Log.d("insertTest","getAll호출")
        return allUsers
    }

}