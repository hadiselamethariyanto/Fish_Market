package com.example.fishmarket.data.repository.login

import com.example.fishmarket.data.repository.login.source.local.LoginLocalDataSource
import com.example.fishmarket.data.repository.login.source.local.entity.UserEntity
import com.example.fishmarket.data.source.remote.Resource
import com.example.fishmarket.domain.repository.ILoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class LoginRepository(
    private val firebaseAuth: FirebaseAuth,
    private val localDataSource: LoginLocalDataSource
) : ILoginRepository {

    override fun login(email: String, password: String): Flow<Resource<UserEntity>> = flow {
        try {
            emit(Resource.Loading())
            val auth = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = auth.user
            if (user != null) {
                val userEntity = UserEntity(
                    id = user.uid,
                    email = user.email.toString(),
                    display_name = user.displayName.toString(),
                    photo_url = user.photoUrl.toString()
                )
                localDataSource.insertUser(userEntity)
                emit(Resource.Success(userEntity))
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

    override fun getCurrentUser(): Flow<Resource<FirebaseUser>> = flow {
        try {
            val user = firebaseAuth.currentUser
            if (user != null) {
                emit(Resource.Success(user))
            } else {
                emit(Resource.Error("empty"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


}