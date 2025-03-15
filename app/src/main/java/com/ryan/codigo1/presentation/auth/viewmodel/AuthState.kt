package com.ryan.codigo1.presentation.auth.viewmodel

data class AuthState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val phone: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val nationality: String? = null,
    val countryOfResidence: String? = null,
    val countryCode: String? = "+65",

    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val phoneError: String? = null,
    val dateOfBirthError: String? = null,
    val genderError: String? = null,
    val nationalityError: String? = null,
    val countryOfResidenceError: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,

    val name: String = "" // Will be composed of firstName + lastName
)