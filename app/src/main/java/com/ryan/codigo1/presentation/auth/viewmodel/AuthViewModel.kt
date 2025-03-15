package com.ryan.codigo1.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryan.codigo1.domain.model.User
import com.ryan.codigo1.domain.usecase.RegisterUser
import com.ryan.codigo1.domain.usecase.ValidateEmail
import com.ryan.codigo1.domain.usecase.ValidateName
import com.ryan.codigo1.domain.usecase.ValidatePassword
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

        val hasError = listOf(
            nameResult,
            emailResult,
            passwordResult
        ).any { !it.isValid }

        // Check if confirm password matches
        val passwordsMatch = _state.value.password == _state.value.confirmPassword

        if (hasError || !passwordsMatch) {
            _state.update { it.copy(
                nameError = nameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                confirmPasswordError = if (!passwordsMatch) "Passwords do not match" else null
            ) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            registerUser(
                User(
                    name = _state.value.name,
                    email = _state.value.email,
                    password = _state.value.password
                )
            ).collect { result ->
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