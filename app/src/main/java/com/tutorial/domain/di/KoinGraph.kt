package com.tutorial.domain.di

import com.tutorial.domain.interceptors.ConnectivityInterceptor
import com.tutorial.domain.interceptors.ConnectivityInterceptorImp
import com.tutorial.domain.repository.IOfflineStore
import com.tutorial.domain.repository.IOnlineTodoRepository
import com.tutorial.domain.repository.OfflineStore
import com.tutorial.domain.repository.Repository
import com.tutorial.presentation.TodoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * [koinModule] is the central Module for Koin DI library. It defines the project dependency graph.
 */
val koinModule: Module = module {
    single { ConnectivityInterceptorImp(androidContext()) as ConnectivityInterceptor }
    single { IOnlineTodoRepository(get()) }
    single { OfflineStore() as IOfflineStore }
    single { Repository(get(), get()) }
    viewModel { TodoViewModel(get()) }
}