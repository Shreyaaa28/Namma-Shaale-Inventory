package com.example.nammashaaleinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.nammashaaleinventory.data.local.entities.RepairFlagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepairFlagDao {
    @Query("SELECT * FROM repair_flags WHERE isResolved = 0 ORDER BY priority DESC, requestDate ASC")
    fun getPendingRepairs(): Flow<List<RepairFlagEntity>>

    @Query("SELECT COUNT(*) FROM repair_flags WHERE isResolved = 0")
    fun getPendingRepairCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepairFlag(repairFlag: RepairFlagEntity): Long

    @Update
    suspend fun updateRepairFlag(repairFlag: RepairFlagEntity): Int

    @Query("SELECT * FROM repair_flags WHERE assetId = :assetId ORDER BY requestDate DESC")
    fun getRepairsForAsset(assetId: String): Flow<List<RepairFlagEntity>>
}
