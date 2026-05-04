package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.model.UserProgressDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = :id LIMIT 1")
    fun observe(id: Int = UserProgressDbModel.SINGLE_ROW_ID): Flow<UserProgressDbModel?>

    @Query("SELECT * FROM user_progress WHERE id = :id LIMIT 1")
    suspend fun get(id: Int = UserProgressDbModel.SINGLE_ROW_ID): UserProgressDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(progress: UserProgressDbModel)
}
