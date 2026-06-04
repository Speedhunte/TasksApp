package com.example.tasksapp.presenation.common

sealed interface State <out T> {

    data class Content <T> (val data :T ): State<T>
    data object Loading: State<Nothing>
    data class Error(val message: String): State<Nothing>
}