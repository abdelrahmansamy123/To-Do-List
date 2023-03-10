package com.route.todo.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Task::class], version = 2,
    exportSchema = false
)
abstract class MyDataBase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        private val dataBaseName = "RouteTasksDataBase";
        private var myDataBase: MyDataBase? = null
        fun getDataBase(context: Context): MyDataBase {
            if (myDataBase == null) {
                //initialization
                myDataBase = Room.databaseBuilder(
                    context,
                    MyDataBase::class.java,
                    dataBaseName
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return myDataBase!!;
        }

    }

}