package com.ryan.codigo1.presentation.auth.viewmodel

sealed class AuthEvent {
    data class NameChanged(val name: String) : AuthEvent()
    data class EmailChanged(val email: String) : AuthEvent()
    data class PasswordChanged(val password: String) : AuthEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : AuthEvent()
    data class PhoneChanged(val phone: String) : AuthEvent()
    data class DateOfBirthChanged(val dateOfBirth: String) : AuthEvent()

    data object RegisterClicked : AuthEvent()
    data object ErrorShown : AuthEvent()
}