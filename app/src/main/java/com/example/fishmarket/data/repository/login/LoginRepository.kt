package com.example.fishmarket.data.repository.login

import com.example.fishmarket.data.repository.login.source.local.LoginLocalDataSource
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.model.User
import com.example.fishmarket.domain.repository.ILoginRepository
import com.example.fishmarket.utilis.DataMapper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class LoginRepository(
    private val firebaseAuth: FirebaseAuth,
    private val localDataSource: LoginLocalDataSource
) : ILoginRepository {

    override fun login(email: String, password: String): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val auth = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = auth.user
            if (user != null) {
                val userEntity = DataMapper.mapFirebaseUserToUserEntity(user)
                val userDomain = DataMapper.mapFirebaseUserToUser(user)
                localDataSource.insertUser(userEntity)
                emit(Resource.Success(userDomain))
            } else {
                emit(Resource.Error("User not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun logout(): Flow<Resource<Boolean>> = flow {
        try {
            firebaseAuth.signOut()
            localDataSource.deleteUser()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getCurrentUser(): Flow<Resource<User>> = flow {
        try {
            val user = firebaseAuth.currentUser
            if (user != null) {
                val userDomain = DataMapper.mapFirebaseUserToUser(user)
                emit(Resource.Success(userDomain))
            } else {
                emit(Resource.Error("empty"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


}