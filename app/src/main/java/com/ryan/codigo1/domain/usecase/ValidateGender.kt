package com.ryan.codigo1.domain.usecase

import com.ryan.codigo1.domain.model.ValidationResult
import javax.inject.Inject

class ValidateGender @Inject constructor() {

    operator fun invoke(gender: String?): ValidationResult {
        if (gender.isNullOrBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Please select a gender"
            )
        }

        // Validate that the gender is one of the acceptable values
        if (gender != "Male" && gender != "Female") {
            return ValidationResult(
                isValid = false,
                errorMessage = "Please select a valid gender"
            )
        }

        return ValidationResult(isValid = true)
    }
}