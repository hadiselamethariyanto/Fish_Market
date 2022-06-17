package com.example.fishmarket.domain.repository

import com.example.fishmarket.data.repository.login.source.local.entity.UserEntity
import com.example.fishmarket.data.source.remote.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface ILoginRepository {

    fun login(email: String, password: String): Flow<Resource<UserEntity>>

    fun logout(): Flow<Resource<Boolean>>

    fun getCurrentUser(): Flow<Resource<FirebaseUser>>
}