package io.fajarca.todo.vo

sealed class ValidationResult {
    object VALID : ValidationResult()
    class INVALID(val errorMessage: String) : ValidationResult()
}