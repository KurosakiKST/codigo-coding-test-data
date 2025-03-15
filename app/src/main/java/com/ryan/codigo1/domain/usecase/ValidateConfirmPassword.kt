package com.ryan.codigo1.domain.usecase

import com.ryan.codigo1.domain.model.ValidationResult
import javax.inject.Inject

class ValidateConfirmPassword @Inject constructor() {

    operator fun invoke(password: String, confirmPassword: String): ValidationResult {
        if (confirmPassword.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Please confirm your password"
            )
        }

        if (password != confirmPassword) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Passwords do not match"
            )
        }

        return ValidationResult(isValid = true)
    }
}