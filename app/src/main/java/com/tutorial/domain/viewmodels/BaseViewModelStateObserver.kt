package com.tutorial.domain.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * [BaseViewModelStateObserver] provide the base class for ViewModels.
 * It post values on it's data change via state update function.
 * This value can be observed by Fragments or Activities.
 */
class BaseViewModelStateObserver<V : ViewState>(initialViewState: V) {
    var viewState: V = initialViewState
        private set

    abstract class ViewStateViewModel<V : ViewState, E : ViewEvents> : BaseViewModel() {

        private val viewStateManager by lazy {
            BaseViewModelStateObserver(initialState)
        }

        abstract val initialState: V

        /**
         * @param block is lambda called on current [ViewState] before it's posted
         */
        fun updateState(block: V.() -> V){
            viewStateManager.viewState = viewStateManager.viewState.block()
            (state as MutableLiveData).postValue(viewStateManager.viewState)
        }

        /**
         * @param event is the current event to be posted
         */
        fun emitEvent(event: E) {
            (events as MutableLiveData).postValue(event)
        }

        val state: LiveData<V> = MutableLiveData()
        private val events: LiveData<E> = MutableLiveData()
    }
}

interface ViewState
interface ViewEvents