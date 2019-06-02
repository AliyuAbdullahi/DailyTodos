package com.tutorial.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tutorial.R
import com.tutorial.domain.models.TaskTodo
import com.tutorial.domain.repository.OfflineStore
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private val myViewModel: TodoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel.loadTodos()

        observeViewModelState()

        //TODO implement adding to the list
        add.setOnClickListener {

        }
    }

    private fun observeViewModelState() {
        myViewModel.state.observe(this, Observer { state ->
            state?.let { currentState ->
                if (currentState.todos.isNotEmpty()) {
                    val twenty = currentState.todos.take(20)
                    val text = StringBuilder().append("\n\n")
                        .append(twenty.joinToString("\n\n---------------------------\n\n") { it.title })
                    todoList.text = text
                }

                loading.visibility = if(currentState.loading) View.VISIBLE else View.GONE
            }
        })
    }
}
