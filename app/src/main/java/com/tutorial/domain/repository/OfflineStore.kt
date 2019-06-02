package com.tutorial.domain.repository

import com.tutorial.domain.models.TaskTodo
import io.reactivex.subjects.BehaviorSubject
import java.lang.IllegalStateException

/**
 * [OfflineStore] is a local database simulation. This class should be supplied as a singleton
 */
class OfflineStore : IOfflineStore {
    override val onTodoSaved: BehaviorSubject<List<TaskTodo>> = BehaviorSubject.create()

    val mutableStore = ArrayList<TaskTodo>()

    override fun saveTodo(todo: TaskTodo): Boolean {
        if (todo in mutableStore) return false
        mutableStore.add(todo)
        return true
    }

    override fun getTodo(id: Int): TaskTodo? {
        return mutableStore.firstOrNull { it.id == id }
            ?: throw IllegalStateException("There is no TaskTodo with id $id")
    }

    override fun getTodos(ids: List<Int>): List<TaskTodo> {
        return mutableStore
    }

    override fun deleteTodo(id: Int): Boolean {
        if (mutableStore.map { it.id }.contains(id).not()) {
            return false
        }
        mutableStore.remove(mutableStore.find { it.id == id })
        return true
    }

    override fun getAllTodos() = mutableStore

    override fun saveTodos(todos: List<TaskTodo>): Boolean {
        mutableStore.addAll(todos)
        onTodoSaved.onNext(todos)
        return true
    }
}