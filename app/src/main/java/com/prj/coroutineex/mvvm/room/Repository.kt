package com.prj.coroutineex.mvvm.room

import android.app.Application
import android.database.Observable
import androidx.lifecycle.LiveData

class Repository(mDatabase: AppDatabase) {

    private val dao = mDatabase.dao()
    val allUsers: LiveData<List<Entity>> = dao.getAll()

    companion object {
        private var sInstance: Repository? = null
        fun getInstance(database: AppDatabase): Repository {
            return sInstance
                ?: synchronized(this) {
                    val instance = Repository(database)
                    sInstance = instance
                    instance
                }
        }
    }

    suspend fun insert(entity: Entity) {
        dao.insert(entity)
    }

    suspend fun delete(entity: Entity) {
        dao.delete(entity)
    }

}