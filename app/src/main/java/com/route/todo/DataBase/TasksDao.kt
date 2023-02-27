package com.route.todo.DataBase

import androidx.room.*

@Dao
interface TasksDao {
    @Insert
    fun insertTask(Task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("select * from Tasks")
    fun getAllTasks(): List<Task>

    @Query("select * from Tasks where date=:selectedDate")
    fun getTasksByDate(selectedDate: Long): List<Task>


}