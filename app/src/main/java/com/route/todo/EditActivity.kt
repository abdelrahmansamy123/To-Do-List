package com.route.todo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.route.todo.Constant.Constant.Companion.TASK
import com.route.todo.DataBase.MyDataBase
import com.route.todo.DataBase.Task
import com.route.todo.databinding.ActivityEditBinding
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var task: Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        task = ((intent.getSerializableExtra(TASK) as? Task)!!)

        showData(task)

        binding.submit.setOnClickListener {
            updateTOdo()
        }

    }

    private fun isDateValid(): Boolean {
        var isValid = true
        if (binding.titleContainer.editText?.text.toString().isNullOrBlank()) {
            binding.titleContainer.error = "Please Enter Title"
            isValid = false
        } else
            binding.titleContainer.error = null

        if (binding.descContainer.editText?.text.toString().isBlank()) {
            binding.descContainer.error = "Please Enter your Description"
            isValid = false
        } else
            binding.titleContainer.error = null

        if (binding.date.text.isNullOrBlank()) {
            binding.date.error = "Please Select Date"
            isValid = false
        } else
            binding.dateContainer.error = null

        return isValid

    }

    private fun updateTOdo() {
        if (isDateValid()) {
            task.title = binding.titleContainer.editText?.text.toString()
            task.description = binding.descContainer.editText?.text.toString()
            //    task.date = binding.dateContainer.editText!!.text.toString().toLong()


            MyDataBase.getDataBase(this).tasksDao().updateTask(task)
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }
    }


    private fun showData(task: Task) {
        binding.titleContainer.editText?.setText(task.title)
        val date = convertLongToTime(task.date)
        binding.date.text = date
        binding.descContainer.editText?.setText(task.description)

    }

    private fun convertLongToTime(date: Long?): String {
        val date = Date(date!!)
        val formate = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return formate.format(date)
    }
}