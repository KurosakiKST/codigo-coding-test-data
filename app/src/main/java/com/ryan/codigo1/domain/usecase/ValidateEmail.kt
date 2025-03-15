package com.ryan.codigo1.domain.usecase

import com.ryan.codigo1.domain.model.ValidationResult
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateEmail @Inject constructor() {

    private val emailRegex = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Email cannot be empty"
            )
        }
        if (!emailRegex.matcher(email).matches()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Please enter a valid email address"
            )
        }
        return ValidationResult(isValid = true)
    }
}