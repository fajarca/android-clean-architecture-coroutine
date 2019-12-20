package io.fajarca.todo.domain.model

sealed class ValidationResult {
    object VALID : ValidationResult()
    class INVALID(val errorMessage: String) : ValidationResult()
}