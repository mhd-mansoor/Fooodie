package com.example.fooodie

sealed class UiError {
    data object NetworkError : UiError()
    data object ServerError : UiError()
    data object NoResultsError : UiError()
    data object GenericError : UiError()
}