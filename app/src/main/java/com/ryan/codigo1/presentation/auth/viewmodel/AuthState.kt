package com.ryan.codigo1.presentation.auth.viewmodel

data class AuthState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val phone: String = "",
    val dateOfBirth: String = "",

    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val phoneError: String? = null,
    val dateOfBirthError: String? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)