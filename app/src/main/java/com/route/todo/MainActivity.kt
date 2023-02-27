package com.route.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.route.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityMainBinding
    val tasksListFragment = TasksListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.bottomNav
            .setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_tasks_list -> {
                        viewBinding.screenTitle.setText(R.string.title_tasks_list)
                        showFragment(tasksListFragment)
                    }
                    R.id.nav_tasks_settings -> {
                        viewBinding.screenTitle.setText(R.string.title_settings)

                        showFragment(settingsFragment())
                    }
                }

                return@setOnItemSelectedListener true;
            }
        viewBinding.bottomNav.selectedItemId = R.id.nav_tasks_list
        viewBinding.addTask.setOnClickListener {
            showAddTaskBottomSheet()
        }

    }

    fun showAddTaskBottomSheet() {
        val addTaskBottomSheet = AddTaskBottomSheet()
        addTaskBottomSheet.onDismissListener = OnDismissListener {
            // add task fragment dismissed
            // get task list fragment
            // reload tasks
            tasksListFragment.loadTasks()
        }
        addTaskBottomSheet.show(supportFragmentManager, null)

    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()


    }
}