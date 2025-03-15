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
            is AuthEvent.FirstNameChanged -> {
                _state.update { it.copy(
                    firstName = event.firstName,
                    firstNameError = null,
                    // Update the legacy name field for backward compatibility
                    name = event.firstName + " " + it.lastName
                ) }
            }
            is AuthEvent.LastNameChanged -> {
                _state.update { it.copy(
                    lastName = event.lastName,
                    lastNameError = null,
                    // Update the legacy name field for backward compatibility
                    name = it.firstName + " " + event.lastName
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
            is AuthEvent.GenderChanged -> {
                _state.update { it.copy(
                    gender = event.gender,
                    genderError = null
                ) }
            }
            is AuthEvent.NationalityChanged -> {
                _state.update { it.copy(
                    nationality = event.nationality,
                    nationalityError = null
                ) }
            }
            is AuthEvent.CountryOfResidenceChanged -> {
                _state.update { it.copy(
                    countryOfResidence = event.countryOfResidence,
                    countryOfResidenceError = null
                ) }
            }
            is AuthEvent.CountryCodeChanged -> {
                _state.update { it.copy(
                    countryCode = event.countryCode
                ) }
            }
            // For backward compatibility
            is AuthEvent.NameChanged -> {
                val nameParts = event.name.split(" ", limit = 2)
                val firstName = nameParts[0]
                val lastName = if (nameParts.size > 1) nameParts[1] else ""
                _state.update { it.copy(
                    name = event.name,
                    firstName = firstName,
                    lastName = lastName,
                    firstNameError = null,
                    lastNameError = null
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
        // Validate first name
        val firstNameResult = validateName(_state.value.firstName)

        // Validate last name
        val lastNameResult = validateName(_state.value.lastName)

        // Validate email
        val emailResult = validateEmail(_state.value.email)

        // Validate password
        val passwordResult = validatePassword(_state.value.password)

        // Validate confirm password matches
        val confirmPasswordResult = validateConfirmPassword(_state.value.password, _state.value.confirmPassword)

        // Validate phone (optional)
        val phoneResult = validatePhone(_state.value.phone)

        // Validate date of birth
        val dateOfBirthResult = validateDateOfBirth(_state.value.dateOfBirth)

        // Validate required fields for nationality and country of residence
        val nationalityError = if (_state.value.nationality.isNullOrBlank()) {
            "Nationality is required"
        } else null

        val countryOfResidenceError = if (_state.value.countryOfResidence.isNullOrBlank()) {
            "Country of residence is required"
        } else null

        // Validate gender is selected
        val genderError = if (_state.value.gender.isBlank()) {
            "Please select a gender"
        } else null

        val hasError = !firstNameResult.isValid ||
                !lastNameResult.isValid ||
                !emailResult.isValid ||
                !passwordResult.isValid ||
                !confirmPasswordResult.isValid ||
                !phoneResult.isValid ||
                !dateOfBirthResult.isValid ||
                nationalityError != null ||
                countryOfResidenceError != null ||
                genderError != null

        if (hasError) {
            _state.update { it.copy(
                firstNameError = firstNameResult.errorMessage,
                lastNameError = lastNameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                confirmPasswordError = confirmPasswordResult.errorMessage,
                phoneError = phoneResult.errorMessage,
                dateOfBirthError = dateOfBirthResult.errorMessage,
                nationalityError = nationalityError,
                countryOfResidenceError = countryOfResidenceError,
                genderError = genderError
            ) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val user = User(
                name = "${_state.value.firstName} ${_state.value.lastName}",
                email = _state.value.email,
                password = _state.value.password,
                firstName = _state.value.firstName,
                lastName = _state.value.lastName,
                phone = _state.value.phone.takeIf { it.isNotBlank() },
                dateOfBirth = _state.value.dateOfBirth,
                gender = _state.value.gender,
                nationality = _state.value.nationality,
                countryOfResidence = _state.value.countryOfResidence,
                countryCode = _state.value.countryCode
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