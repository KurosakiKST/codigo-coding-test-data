package com.ryan.codigo1.presentation.auth.viewmodel

sealed class AuthEvent {
    data class NameChanged(val name: String) : AuthEvent()
    data class EmailChanged(val email: String) : AuthEvent()
    data class PasswordChanged(val password: String) : AuthEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : AuthEvent()
    object RegisterClicked : AuthEvent()
    object ErrorShown : AuthEvent() // To clear error after showing
}