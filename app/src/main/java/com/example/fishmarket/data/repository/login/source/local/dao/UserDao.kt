package com.example.fishmarket.data.repository.login.source.local.dao

import androidx.room.*
import com.example.fishmarket.data.repository.login.source.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun deleteUser()
}