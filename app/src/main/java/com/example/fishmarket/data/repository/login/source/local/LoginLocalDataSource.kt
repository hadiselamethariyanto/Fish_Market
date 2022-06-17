package com.example.fishmarket.data.repository.login.source.local

import com.example.fishmarket.data.repository.login.source.local.dao.UserDao
import com.example.fishmarket.data.repository.login.source.local.entity.UserEntity

class LoginLocalDataSource(private val userDao: UserDao) {
    suspend fun insertUser(userEntity: UserEntity) = userDao.insertUser(userEntity)

    suspend fun deleteUser() = userDao.deleteUser()
}