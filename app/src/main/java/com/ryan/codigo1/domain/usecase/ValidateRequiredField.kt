package com.ryan.codigo1.domain.usecase

import com.ryan.codigo1.domain.model.ValidationResult
import javax.inject.Inject

class ValidateRequiredField @Inject constructor() {

    operator fun invoke(value: String?, fieldName: String): ValidationResult {
        if (value.isNullOrBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "$fieldName is required"
            )
        }

        return ValidationResult(isValid = true)
    }
}