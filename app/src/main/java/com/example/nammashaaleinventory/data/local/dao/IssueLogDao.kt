package com.example.nammashaaleinventory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.nammashaaleinventory.data.local.entities.IssueLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IssueLogDao {
    @Query("SELECT * FROM issue_logs ORDER BY date DESC")
    fun getAllIssues(): Flow<List<IssueLogEntity>>

    @Query("SELECT * FROM issue_logs WHERE assetId = :assetId ORDER BY date DESC")
    fun getIssuesForAsset(assetId: String): Flow<List<IssueLogEntity>>

    @Query("SELECT * FROM issue_logs WHERE issueType = :type ORDER BY date DESC")
    fun getIssuesByType(type: String): Flow<List<IssueLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssue(issue: IssueLogEntity): Long

    @Update
    suspend fun updateIssue(issue: IssueLogEntity): Int

    @Query("SELECT COUNT(*) FROM issue_logs WHERE date >= :since")
    fun getIssueCountSince(since: Long): Flow<Int>
}
