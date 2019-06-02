package com.tutorial.domain.models


import com.google.gson.annotations.SerializedName

data class TaskTodo(
    @SerializedName("userId")
    val userId: Int = -1,
    @SerializedName("completed")
    val completed: Boolean = false,
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("title")
    val title: String = ""
)