package com.ryan.codigo1.domain.usecase

import com.ryan.codigo1.domain.model.ValidationResult
import javax.inject.Inject

class ValidateName @Inject constructor() {

    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Name cannot be empty"
            )
        }
        if (name.length < 2) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Name must be at least 2 characters"
            )
        }
        return ValidationResult(isValid = true)
    }
}