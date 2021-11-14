package com.prj.coroutineex.mvvm.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * from user_table ORDER BY name ASC")
    fun getAlphabetizedUsers() : LiveData<List<UserEntity>>

    // 같은 값이 들어오면 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userEntity: UserEntity)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}