package com.route.todo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todo.DataBase.MyDataBase
import com.route.todo.DataBase.Task
import com.route.todo.databinding.FragmentAddTaskBinding
import java.util.*

class AddTaskBottomSheet : BottomSheetDialogFragment() {

    lateinit var viewBinding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    var onDismissListener: OnDismissListener? = null

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        //dismiss for add task sheet
        onDismissListener?.onDismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDate()
        viewBinding.taskDate.setOnClickListener {
            showDatePicker()
        }
        viewBinding.submit.setOnClickListener {
            addTask()
        }

    }

    fun validate(): Boolean {
        var valid = true
        val title = viewBinding.taskTitle.editText?.text.toString()
        val desc = viewBinding.taskDesc.editText?.text.toString()
        if (title.isNullOrBlank()) {
            // setError
            viewBinding.taskTitle
                .error = "please enter title"
            valid = false
        } else {
            //remove error
            viewBinding.taskTitle.error = null
        }
        if (title.isNullOrBlank()) {
            // setError
            viewBinding.taskDesc
                .error = "please enter description"
            valid = false
        } else {
            //remove error
            viewBinding.taskDesc.error = null
        }
        return valid
    }

    fun addTask() {
        if (validate() == false) {
            return
        }
        val title = viewBinding.taskTitle.editText?.text.toString()
        val desc = viewBinding.taskDesc.editText?.text.toString()
        //add task to database
        MyDataBase.getDataBase(requireActivity())
            .tasksDao()
            .insertTask(
                Task(
                    title = title,
                    description = desc,
                    date = currentDate.timeInMillis
                )
            )
        showTaskInsertedDialog()
    }

    fun showTaskInsertedDialog() {
        val alertDialog = AlertDialog.Builder(activity)
            .setMessage("Task Inserted Successfully")
            .setPositiveButton(R.string.ok, { dialog, btnId ->
                dialog.dismiss()
                dismiss()
            }).setCancelable(false)
        alertDialog.show()
    }

    fun setDate() {
        // date formatter
        viewBinding.taskDateText.setText(
            "" + currentDate.get(Calendar.DAY_OF_MONTH)
                    + "/" + currentDate.get(Calendar.MONTH)
                    + "/" + currentDate.get(Calendar.YEAR)
        )
    }

    var currentDate = Calendar.getInstance()

    init {
        currentDate.set(Calendar.HOUR, 0)
        currentDate.set(Calendar.MINUTE, 0)
        currentDate.set(Calendar.SECOND, 0)
        currentDate.set(Calendar.MILLISECOND, 0)

    }

    fun showDatePicker() {

        val datePicker = DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { datepicker, year, month, day ->
                currentDate.set(Calendar.YEAR, year)
                currentDate.set(Calendar.MONTH, month)
                currentDate.set(Calendar.DAY_OF_MONTH, day)
                setDate()
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

}



