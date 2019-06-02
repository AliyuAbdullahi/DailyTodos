package com.tutorial.presentation

import com.tutorial.domain.exceptions.NoConnectivityException
import com.tutorial.domain.models.TaskTodo
import com.tutorial.domain.repository.Repository
import com.tutorial.domain.viewmodels.BaseViewModelStateObserver
import com.tutorial.domain.viewmodels.ViewEvents
import com.tutorial.domain.viewmodels.ViewState

class TodoViewModel(private val repository: Repository) :
    BaseViewModelStateObserver.ViewStateViewModel<TodoViewModel.State, TodoViewModel.Event>() {

    override val initialState = State()

    fun saveTodo(todo: TaskTodo) {
        updateState { showLoading() }
        val todoSaved = repository.saveTodo(todo)
        updateState { onTodoSaved(todoSaved, todo) }
    }

    fun loadTodos() {
        repository.myObservable.subscribe(
            {
                updateState { showTaskTodos(it) }
            }, {
                if (it is NoConnectivityException) {
                    updateState { hasNoNetwork(true) }
                } else {
                    updateState { showErrorMessage("${it.message}") }
                }
            }
        )
        disposable.add(repository.loadAllTodos())
    }

    data class State(
        val loading: Boolean = true,
        val todos: List<TaskTodo> = listOf(),
        val error: String = "",
        val hasNoConnectivity: Boolean = false,
        val isEmpty: Boolean = true,
        val currentTodo: TaskTodo = TaskTodo(),
        val onTodoSaved: Boolean = false
    ) : ViewState {
        fun showTaskTodos(todos: List<TaskTodo>) = copy(todos = todos, isEmpty = false, loading = false)
        fun onTodoSaved(saved: Boolean, todo: TaskTodo) = copy(onTodoSaved = saved, loading = false, currentTodo = todo)
        fun showErrorMessage(message: String) = copy(error = message)
        fun showLoading() = copy(loading = true)
        fun hasNoNetwork(noNetwork: Boolean) = copy(hasNoConnectivity = noNetwork)
    }

    sealed class Event : ViewEvents
}