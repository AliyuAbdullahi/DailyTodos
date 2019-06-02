package com.tutorial.domain.repository

import com.tutorial.domain.testutils.EspressoIdlingResource
import com.tutorial.domain.models.TaskTodo
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import rx.subjects.PublishSubject

class Repository(private val onlineRepository: IOnlineTodoRepository, private val offlineRepository: IOfflineStore) {
    private val mySubject = PublishSubject.create<List<TaskTodo>>()
    val myObservable = mySubject.asObservable()

    fun loadAllTodos(): Disposable {
        EspressoIdlingResource.increment()
        val disposable = onlineRepository.getTodos().subscribeOn(Schedulers.io()).subscribe({
            EspressoIdlingResource.decrement()
            offlineRepository.saveTodos(it)
            mySubject.onNext(it)
        }, {
            mySubject.onError(it)
        })

        return disposable
    }

    fun saveTodo(todo: TaskTodo) = offlineRepository.saveTodo(todo)

    //Implement other data ops
}