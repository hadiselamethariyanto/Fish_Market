package com.example.fishmarket.domain.usecase.login

import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.User
import com.example.fishmarket.domain.repository.ILoginRepository
import kotlinx.coroutines.flow.Flow

class LoginInteractor(private val repository: ILoginRepository) : LoginUseCase {

    override fun login(email: String, password: String): Flow<Resource<User>> =
        repository.login(email, password)

    override fun logout(): Flow<Resource<Boolean>> = repository.logout()

    override fun getCurrentUser(): Flow<Resource<User>> = repository.getCurrentUser()
}