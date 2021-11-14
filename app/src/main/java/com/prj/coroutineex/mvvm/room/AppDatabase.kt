package com.prj.coroutineex.mvvm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
// https://ddangeun.tistory.com/82
@Database(entities = [UserEntity::class],version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao

    companion object{
        @Volatile
        private var INSTANCE : AppDatabase? = null

        // 싱글턴
        fun getDatabase(context: Context, scope:CoroutineScope) : AppDatabase{
           return INSTANCE ?: synchronized(this){
               val instance = Room.databaseBuilder(
                   context.applicationContext,
                   AppDatabase::class.java,
                   "app_database"
               )
                   .addCallback(AppDatabaseCallback(scope))
                   .fallbackToDestructiveMigration()
                   .build()
               INSTANCE = instance
               //return
               instance
           }
        }

    }

    // onCreate()를 오버라이드하여 db가 처음 생성될대 할 행동 처리
    // onOpen()을 오버라이딩하면 db가 열릴때마다 할 행동 처리 가능
    private class AppDatabaseCallback(private val scope : CoroutineScope) : RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.userDao())
                }
            }
        }

        suspend fun populateDatabase(userDao: UserDao){
            // userDao.deleteAll()
            userDao.insert(UserEntity("MyeongSeong","남","1994-05-04"))
        }

    }

}