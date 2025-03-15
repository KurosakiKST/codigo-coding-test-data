package com.ryan.codigo1.domain.usecase

import com.ryan.codigo1.domain.model.User
import com.ryan.codigo1.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUser @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(user: User): Flow<Result<Boolean>> {
        return repository.registerUser(user)
    }
}