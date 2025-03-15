package com.ryan.codigo1.presentation.auth.viewmodel

sealed class AuthEvent {
    data class FirstNameChanged(val firstName: String) : AuthEvent()
    data class LastNameChanged(val lastName: String) : AuthEvent()
    data class EmailChanged(val email: String) : AuthEvent()
    data class PasswordChanged(val password: String) : AuthEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : AuthEvent()

    data class PhoneChanged(val phone: String) : AuthEvent()
    data class DateOfBirthChanged(val dateOfBirth: String) : AuthEvent()
    data class GenderChanged(val gender: String) : AuthEvent()
    data class NationalityChanged(val nationality: String) : AuthEvent()
    data class CountryOfResidenceChanged(val countryOfResidence: String) : AuthEvent()
    data class CountryCodeChanged(val countryCode: String) : AuthEvent()

    data class NameChanged(val name: String) : AuthEvent()

    data object RegisterClicked : AuthEvent()
    data object ErrorShown : AuthEvent()
}