package com.prj.coroutineex.mvvm.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class Entity(
    @PrimaryKey(autoGenerate = true)// PrimaryKey 를 자동적으로 생성
    val id: Int,
    var number1: String,
)