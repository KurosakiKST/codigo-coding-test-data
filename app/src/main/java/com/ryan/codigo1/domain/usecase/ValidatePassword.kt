package com.ryan.codigo1.domain.usecase

import com.ryan.codigo1.domain.model.ValidationResult
import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    operator fun invoke(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Password must be at least 8 characters"
            )
        }
        val containsLettersAndDigits = password.any { it.isLetter() } &&
                password.any { it.isDigit() }
        if (!containsLettersAndDigits) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Password must contain at least one letter and one digit"
            )
        }
        return ValidationResult(isValid = true)
    }
}