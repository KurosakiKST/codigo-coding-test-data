package com.ryan.codigo1.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryan.codigo1.domain.model.User
import com.ryan.codigo1.domain.usecase.RegisterUser
import com.ryan.codigo1.domain.usecase.ValidateConfirmPassword
import com.ryan.codigo1.domain.usecase.ValidateDateOfBirth
import com.ryan.codigo1.domain.usecase.ValidateEmail
import com.ryan.codigo1.domain.usecase.ValidateName
import com.ryan.codigo1.domain.usecase.ValidatePassword
import com.ryan.codigo1.domain.usecase.ValidatePhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val validateName: ValidateName,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword,
    private val validatePhone: ValidatePhone,
    private val validateDateOfBirth: ValidateDateOfBirth,
    private val registerUser: RegisterUser
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.NameChanged -> {
                _state.update { it.copy(
                    name = event.name,
                    nameError = null
                ) }
            }
            is AuthEvent.EmailChanged -> {
                _state.update { it.copy(
                    email = event.email,
                    emailError = null
                ) }
            }
            is AuthEvent.PasswordChanged -> {
                _state.update { it.copy(
                    password = event.password,
                    passwordError = null
                ) }
            }
            is AuthEvent.ConfirmPasswordChanged -> {
                _state.update { it.copy(
                    confirmPassword = event.confirmPassword,
                    confirmPasswordError = null
                ) }
            }
            is AuthEvent.PhoneChanged -> {
                _state.update { it.copy(
                    phone = event.phone,
                    phoneError = null
                ) }
            }
            is AuthEvent.DateOfBirthChanged -> {
                _state.update { it.copy(
                    dateOfBirth = event.dateOfBirth,
                    dateOfBirthError = null
                ) }
            }
            is AuthEvent.RegisterClicked -> {
                submitData()
            }
            is AuthEvent.ErrorShown -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun submitData() {
        val nameResult = validateName(_state.value.name)
        val emailResult = validateEmail(_state.value.email)
        val passwordResult = validatePassword(_state.value.password)
        val confirmPasswordResult = validateConfirmPassword(_state.value.password, _state.value.confirmPassword)
        val phoneResult = validatePhone(_state.value.phone)
        val dateOfBirthResult = validateDateOfBirth(_state.value.dateOfBirth)

        val hasError = listOf(
            nameResult,
            emailResult,
            passwordResult,
            confirmPasswordResult,
            phoneResult,
            dateOfBirthResult
        ).any { !it.isValid }

        if (hasError) {
            _state.update { it.copy(
                nameError = nameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                confirmPasswordError = confirmPasswordResult.errorMessage,
                phoneError = phoneResult.errorMessage,
                dateOfBirthError = dateOfBirthResult.errorMessage
            ) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // Create an enhanced user with additional fields
            val user = User(
                name = _state.value.name,
                email = _state.value.email,
                password = _state.value.password,
                phone = _state.value.phone.takeIf { it.isNotBlank() },
                dateOfBirth = _state.value.dateOfBirth.takeIf { it.isNotBlank() }
            )

            registerUser(user).collect { result ->
                result.fold(
                    onSuccess = { success ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isSuccess = true
                            )
                        }
                    },
                    onFailure = { error ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = error.message ?: "An unknown error occurred"
                            )
                        }
                    }
                )
            }
        }
    }
}