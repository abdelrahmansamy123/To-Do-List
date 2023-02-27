package com.route.todo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.route.todo.DataBase.Task
import com.route.todo.databinding.ItemTaskBinding
import com.zerobranch.layout.SwipeLayout

class TasksRecyclerAdapter(var items: List<Task>?) :
    RecyclerView.Adapter<TasksRecyclerAdapter.ViewHolder>() {
    var onItemClicked: OnItemClicked? = null
    var onItemDeleteClicked: OnItemDeleteClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewBinding.title.text = items?.get(position)?.title
        holder.viewBinding.desc.text = items?.get(position)?.description

        if (items!![position].isDone == true) {
            holder.viewBinding.doneBtn.setBackgroundColor(Color.GREEN)
            holder.viewBinding.title.setTextColor(Color.GREEN)
            holder.viewBinding.doneBtn.setBackgroundResource(R.drawable.makedone)

        }
        holder.viewBinding.swipe.setOnActionsListener(object : SwipeLayout.SwipeActionsListener {
            override fun onClose() {

            }

            override fun onOpen(direction: Int, isContinuous: Boolean) {

            }
        })
        if (onItemClicked != null) {
            holder.viewBinding.card.setOnLongClickListener {
                onItemClicked?.onItemClick(items!![position])
                true


            }
        }

        holder.viewBinding.delete.setOnClickListener {
            onItemDeleteClicked?.onItemDeleteClick(position, items!![position])
            holder.viewBinding.swipe.close()
        }

    }

    fun changeData(newListOfTasks: List<Task>?) {
        items = newListOfTasks
        notifyDataSetChanged()

    }

    class ViewHolder(val viewBinding: ItemTaskBinding) : RecyclerView.ViewHolder(viewBinding.root) {

    }

}

interface OnItemClicked {
    fun onItemClick(task: Task)
}

interface OnItemDeleteClicked {
    fun onItemDeleteClick(position: Int, task: Task)
}