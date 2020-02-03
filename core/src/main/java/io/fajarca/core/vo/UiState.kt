package io.fajarca.core.vo

sealed class UiState {
    object Loading : UiState()
    object Success: UiState()
    object Complete : UiState()
    object Error: UiState()
}
