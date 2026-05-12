package com.example.nammashaaleinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.nammashaaleinventory.data.local.entities.AssetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {
    @Query("SELECT * FROM assets ORDER BY createdAt DESC")
    fun getAllAssets(): Flow<List<AssetEntity>>

    @Query("SELECT * FROM assets WHERE id = :id")
    suspend fun getAssetById(id: String): AssetEntity?

    @Query("SELECT * FROM assets WHERE category = :category")
    fun getAssetsByCategory(category: String): Flow<List<AssetEntity>>

    @Query("SELECT * FROM assets WHERE name LIKE '%' || :searchQuery || '%' OR serialNumber LIKE '%' || :searchQuery || '%'")
    fun searchAssets(searchQuery: String): Flow<List<AssetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: AssetEntity): Long

    @Update
    suspend fun updateAsset(asset: AssetEntity): Int

    @Delete
    suspend fun deleteAsset(asset: AssetEntity): Int

    @Query("SELECT COUNT(*) FROM assets")
    fun getAssetCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM assets WHERE status = :status")
    fun getAssetCountByStatus(status: String): Flow<Int>
}
