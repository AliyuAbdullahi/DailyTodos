package com.tutorial

/**
 @startuml

 package com.tutorial.domain {

    package com.tutorial.domain.models {
        class TaskTodo {
            id: String
            title: String
            description: String
            createdAt: String
        }
    }

    note bottom of TaskTodo
    This is represents the model for our TaskTodo.
    end note

    package com.tutorial.domain.data {
        class IOnlineTodoRepository <<(I, #4CAF50)>> {
            + {abstract} fun getTaskTodos(): List<TaskTodo>
        }

        package com.tutorial.domain.data.database <<Database>> {
            class OfflineData<<(I, #4CAF50)>> {
                + {abstract} fun saveTodo(taskTodo: TaskTodo)
                + {abstract} fun getAllTaskTodos(): List<TaskTodo>
            }
        }


        class Repository {
            + {abstract} fun saveTodo(taskTodo: TaskTodo)
            + {abstract} fun getAllTaskTodos(): List<TaskTodo>
            + {abstract} fun getTaskTodos(): List<TaskTodo>
        }

        Repository <-- OfflineData
        Repository <-- IOnlineTodoRepository
    }

    package com.tutorial.domain.coreViewModel {
        class BaseViewModel {
            repository: Repository
        }

        BaseViewModel <-- Repository
    }
 }

 package com.tutorial.presentation {
    class View {
        var viewModel: ViewModel
    }
 }

 View <-- BaseViewModel
 @enduml
 *
 *
 * **/