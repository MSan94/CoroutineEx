package com.prj.coroutineex.mvvm.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name="gender")
    val gender : String?,

    @ColumnInfo(name="birth")
    val birth : String?
)