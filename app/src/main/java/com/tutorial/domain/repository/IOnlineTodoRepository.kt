package com.tutorial.domain.repository

import com.tutorial.BuildConfig
import com.tutorial.domain.interceptors.ConnectivityInterceptor
import com.tutorial.domain.models.TaskTodo
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

interface IOnlineTodoRepository {
    @GET("todos")
    fun getTodos(): Single<List<TaskTodo>>

    @POST("post")
    fun createTodo(taskTodo: TaskTodo): Single<Response<Any>>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): IOnlineTodoRepository {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .addInterceptor(connectivityInterceptor).build()

            return Retrofit.Builder().client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build().create(IOnlineTodoRepository::class.java)
        }
    }
}