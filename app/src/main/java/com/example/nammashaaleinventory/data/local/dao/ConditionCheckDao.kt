package com.example.nammashaaleinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nammashaaleinventory.data.local.entities.ConditionCheckEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConditionCheckDao {
    @Query("SELECT * FROM condition_checks WHERE assetId = :assetId ORDER BY timestamp DESC")
    fun getChecksForAsset(assetId: String): Flow<List<ConditionCheckEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheck(check: ConditionCheckEntity): Long

    @Query("SELECT * FROM condition_checks ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastCheck(): ConditionCheckEntity?
}
