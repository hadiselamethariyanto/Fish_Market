package com.example.fishmarket.domain.usecase.login

import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    fun login(email:String, password:String): Flow<Resource<User>>

    fun logout(): Flow<Resource<Boolean>>

    fun getCurrentUser(): Flow<Resource<User>>

}