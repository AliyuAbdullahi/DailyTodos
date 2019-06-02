package com.tutorial.domain.repository

import com.tutorial.domain.models.TaskTodo
import io.reactivex.subjects.BehaviorSubject

interface IOfflineStore {
    val onTodoSaved: BehaviorSubject<List<TaskTodo>>
    fun saveTodo(todo: TaskTodo): Boolean
    fun getAllTodos(): List<TaskTodo>
    fun getTodo(id: Int): TaskTodo?
    fun getTodos(ids: List<Int>):List<TaskTodo>
    fun deleteTodo(id: Int): Boolean
    fun saveTodos(todos: List<TaskTodo>): Boolean
}